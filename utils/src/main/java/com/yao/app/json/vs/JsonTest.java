package com.yao.app.json.vs;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.yao.app.json.JsonMapper;
import com.yao.app.json.object.CodeEnum;
import com.yao.app.json.object.ObjectFactory;
import com.yao.app.json.object.User;
import java.util.TimeZone;

/**
 * Created by yaolei02 on 2018/9/5.
 */
public class JsonTest {

    public static void main(String[] args) throws Exception {
        CodeEnum t1 = CodeEnum.TIPS;

        String json1 = JsonMapper.NON_NULL_MAPPER.toJson(t1);
        System.out.println(json1);

        CodeEnum t2 = JsonMapper.NON_NULL_MAPPER.fromJson(json1, CodeEnum.class);
        System.out.println(t1 == t2);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.setTimeZone(TimeZone.getDefault());
        mapper.registerModule(new GuavaModule());
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());

        mapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);

        User user = ObjectFactory.getDefaultUser();
        String json = mapper.writeValueAsString(user);
        System.out.println(json);

        User user2 = (User)mapper.readValue(json, Object.class);
        System.out.println(user2);
    }
}
