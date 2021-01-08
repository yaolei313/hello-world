package com.yao.app.nosql;


import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.not;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-16
 */
public class MongoPojoStudy {

    public static void main(String[] args) {
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        // 全局的codec registry
        MongoClientSettings settings = MongoClientSettings.builder()
            .applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress("10.4.44.199", 27017))))
            .codecRegistry(pojoCodecRegistry)
            .build();
        MongoClient mongoClient = MongoClients.create(settings);

        // 单个db的codec registry
        MongoDatabase database = mongoClient.getDatabase("study");
        //database = database.withCodecRegistry(pojoCodecRegistry);

        // 单个collection的codec registry
        /*
        MongoCollection<Document> tmpCollection = database.getCollection("people");
        tmpCollection = tmpCollection.withCodecRegistry(pojoCodecRegistry);
         */
        MongoCollection<Person> collection = database.getCollection("people", Person.class);
        Person somebody = collection.find(eq("address.city", "Wimborne")).first();
        System.out.println(somebody);

        test1(database);
    }

    public static void test1(MongoDatabase database){

        MongoCollection<Person> collection = database.getCollection("people", Person.class);
        collection.drop();

        Person ada = new Person("Ada Byron", 20, new Address("St James Square", "London", "W1"));
        collection.insertOne(ada);

        System.out.println(ada.getId());

        List<Person> peoples = Arrays.asList(
            new Person("Charles Babbage", 45, new Address("5 Devonshire Street", "London", "W11")),
            new Person("Alan Turing", 28, new Address("Bletchley Hall", "Bletchley Park", "MK12")),
            new Person("Timothy Berners-Lee", 61, new Address("Colehill", "Wimborne", null))
        );
        collection.insertMany(peoples);

        Consumer<Person> consumer = (item) -> System.out.println(item);
        collection.find().forEach(consumer);

        Person somebody = collection.find(eq("address.city", "Wimborne")).first();
        System.out.println(somebody);

        UpdateResult updateResult = collection.updateOne(eq("name", "Ada Byron"), combine(set("age", 23), set("name", "Ada Byron 2")));
        System.out.println(updateResult);

        updateResult = collection.updateMany(not(eq("zip", null)), set("zip", null));
        System.out.println(updateResult.getModifiedCount());
    }
}
