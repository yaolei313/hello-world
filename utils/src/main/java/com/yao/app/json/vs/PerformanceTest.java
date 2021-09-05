package com.yao.app.json.vs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yao.app.json.object.Group;
import com.yao.app.json.object.ObjectFactory;
import com.yao.app.json.object.User;
import java.util.TimeZone;

/**
 * 10W数据，5W数据时都做了对比，序列化jasckson>fastjson>gson
 * 反序列化jackson>=fastjson>gson
 * 
 * @author summer
 *
 */
public class PerformanceTest {

    public static void main(String[] args) {
        Group group = ObjectFactory.getDefaultGroup();
        for (int i = 0; i < 50000; i++) {
            User user = ObjectFactory.getDefaultUser();

            group.getUsers().put("index" + (i + 1), user);
        }

        try {
            // gson test
            Gson gson = new Gson();

            long t1 = System.currentTimeMillis();
            String json = gson.toJson(group);
            long t2 = System.currentTimeMillis();

            System.out.println("gson serializing time:" + (t2 - t1));

            long t3 = System.currentTimeMillis();
            @SuppressWarnings("unused")
            Group g = gson.fromJson(json, Group.class);
            long t4 = System.currentTimeMillis();

            System.out.println("gson deserializing time:" + (t4 - t3));

            // jackson test
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            //mapper.registerModule(new JSR310Module());
            
            // bug:http://stackoverflow.com/questions/20222376/default-timezone-for-datetime-deserialization-with-jackson-joda-time-module
            mapper.setTimeZone(TimeZone.getDefault());

            t1 = System.currentTimeMillis();
            json = mapper.writeValueAsString(group);
            t2 = System.currentTimeMillis();

            System.out.println("jackson serializing time:" + (t2 - t1));

            t3 = System.currentTimeMillis();
            g = mapper.readValue(json, Group.class);
            t4 = System.currentTimeMillis();

            System.out.println("jackson deserializing time:" + (t4 - t3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
