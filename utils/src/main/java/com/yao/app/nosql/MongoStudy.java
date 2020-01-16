package com.yao.app.nosql;

import static com.mongodb.client.model.Filters.eq;

import com.google.common.collect.Lists;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.ValidationOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.yao.app.java.date.DateExtUtils;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.bson.Document;

/**
 * 描述: http://mongodb.github.io/mongo-java-driver/3.11/driver/getting-started/quick-start-pojo/
 *
 * @author allen@xiaohongshu.com 2019-12-02
 */
public class MongoStudy {

    public static void main(String[] args) {
        MongoClientSettings settings = MongoClientSettings.builder()
            .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress("10.4.44.199", 27017))))
            //.credential(MongoCredential.createCredential("userName", "study", "password".toCharArray()))
            //.compressorList(Arrays.asList(MongoCompressor.createSnappyCompressor(), MongoCompressor.createZlibCompressor(), MongoCompressor.createZstdCompressor()))
            .build();
        // MongoClientSettings默认的codecRegistry为MongoClientSettings.getDefaultCodecRegistry()

        MongoClient mongoClient = MongoClients.create(settings);

        // mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database.collection][?options]]
        // ConnectionString connectionString = new ConnectionString("mongodb://10.4.44.199:27017/study?compressors=snappy,zlib,zstd");
        // MongoClient mongoClient = MongoClients.create(connectionString);

        MongoDatabase database = mongoClient.getDatabase("study");

        MongoCollection<Document> collection = database.getCollection("user");
        collection.drop();

        ValidationOptions collOptions = new ValidationOptions().validator(Filters.and(Filters.exists("username"), Filters.exists("email")));
        database.createCollection("user", new CreateCollectionOptions().validationOptions(collOptions));

        System.out.println(LocalDateTime.now());

        collection.drop();
        System.out.println("drop collection");

        Document index1 = new Document("username", 1);
        Document index2 = new Document("email", 1);

        IndexOptions indexOptions = new IndexOptions();
        indexOptions.unique(true);

        List<IndexModel> indexes = Lists.newArrayList(new IndexModel(index1, indexOptions), new IndexModel(index2, indexOptions));

        collection.createIndexes(indexes);

        // collection.deleteMany();

        Document doc = new Document("username", "y00196907")
            .append("email", "yaolei313@gmail.com")
            .append("nickname", "李白")
            .append("sex", "M")
            .append("register_time", DateExtUtils.asDate(LocalDateTime.of(2014, 8, 8, 0, 0)));

        // If no top-level _id field is specified in the document, MongoDB automatically adds the _id field to the inserted document.
        collection.insertOne(doc);
        // MongoDB reserves field names that start with "_" and "$" for internal use.
        System.out.println(doc.get("_id"));

        Document doc2 = new Document("username", "l00196906")
            .append("email", "liwu@gmail.com")
            .append("nickname", "李五")
            .append("sex", "M")
            .append("register_time", DateExtUtils.asDate(LocalDateTime.of(2014, 8, 7, 0, 0)));
        Document doc3 = new Document("username", "w00194905")
            .append("email", "wangsi@gmail.com")
            .append("nickname", "王四")
            .append("sex", "M")
            .append("register_time", DateExtUtils.asDate(LocalDateTime.of(2014, 8, 6, 0, 0)));
        Document doc4 = new Document("username", "z00192605")
            .append("email", "zhangsan@gmail.com")
            .append("nickname", "张三")
            .append("sex", "F")
            .append("register_time", DateExtUtils.asDate(LocalDateTime.of(2014, 8, 5, 0, 0)));

        // 若存在违法唯一性约束的记录，则其及之后的记录会插入失败
        collection.insertMany(Lists.newArrayList(doc2, doc3, doc4));
        System.out.println(doc2.get("_id"));
        System.out.println(doc3.get("_id"));
        System.out.println(doc4.get("_id"));

        System.out.println(collection.countDocuments());

        Document firstDoc = collection.find().first();
        System.out.println(firstDoc);

        //collection.find().batchSize(20).cursorType(CursorType.NonTailable)

        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

        System.out.println("-----2-----");

        Document queryResult = collection.find(eq("username", "y00196907")).first();
        System.out.println(queryResult);

        System.out.println("-----3-----");
        Consumer<Document> consumer = item -> System.out.println(item);
        collection.find(Filters.eq("username", "y00196907")).forEach(consumer);

        // update是更新field，replace是替换整个document
        // UpdateResult updateResult = collection.updateOne(Filters.eq("username", "y00196907"), new Document("$set", new Document("nickname", "李白2")));
        UpdateResult updateResult = collection.updateOne(Filters.eq("username", "y00196907"), Updates.set("nickname", "李白3"));
        System.out.println(updateResult);

        System.out.println("-----4-----");

        Document tmp = new Document("username", "z00121605")
            .append("email", "zhaoyi@gmail.com")
            .append("nickname", "赵一")
            .append("sex", "F")
            .append("register_time", DateExtUtils.asDate(LocalDateTime.of(2012, 8, 5, 0, 0)));
        collection.insertOne(tmp);
        System.out.println(tmp.get("_id"));

        DeleteResult deleteResult = collection.deleteMany(Filters.eq("email", "zhaoyi@gmail.com"));
        System.out.println(deleteResult);

        System.out.println("-----5-----");

        doc.append("nickname", "李白replace2");
        // replace one等同删除，再insert。id会发生变化
        updateResult = collection.replaceOne(Filters.eq("username", "y00196907"), doc);
        System.out.println(updateResult);

    }
}
