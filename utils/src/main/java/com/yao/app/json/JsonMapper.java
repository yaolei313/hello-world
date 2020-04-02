package com.yao.app.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.common.base.Throwables;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonMapper {

    // 若使用这个，就不要通过getMapper来修改内部ObjectMapper
    public static final JsonMapper NON_NULL_MAPPER = JsonMapper.nonNullMapper();

    public static final JsonMapper NON_EMPTY_MAPPER = JsonMapper.nonEmptyMapper();

    private static final Logger LOG = LoggerFactory.getLogger(JsonMapper.class);

    private static JsonMapper nonNullMapper() {
        return new JsonMapper(Include.NON_NULL);
    }

    private static JsonMapper nonEmptyMapper() {
        return new JsonMapper(Include.NON_EMPTY);
    }

    private ObjectMapper mapper;

    private JsonMapper(Include include) {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        if (include != null) {
            mapper.setSerializationInclusion(include);
        }

        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 修复joda serialize deserialize lost timezone bug
        mapper.setTimeZone(TimeZone.getDefault());

        // 以下为扩展模块
        mapper.registerModule(new GuavaModule());
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());

        // 支持使用Jaxb的Annotation，使得POJO上的annotation不用与Jackson耦合。
        // mapper.registerModule(new JaxbAnnotationModule());

        // 设置是否使用Enum的toString函数来读写Enum,
        // 为False时使用Enum的name()函数来读写Enum, 默认为False
        // 注意本函数一定要在Mapper创建后, 所有的读写操作之前调用
        // mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        // mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);

    }

    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            LOG.warn("to json error:{}", object, e);
            return null;
        }
    }

    /**
     * 反序列化POJO或简单Collection如List<String>.
     * <p/>
     * 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合.
     * <p/>
     * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
     *
     * @see #fromJson(String, JavaType)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            LOG.warn("parse json error:{}", jsonString, e);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, TypeReference valueTypeRef) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, valueTypeRef);
        } catch (IOException e) {
            LOG.warn("parse json error:{}", jsonString, e);
            return null;
        }
    }

    public <T> T fromJsonWithException(String jsonString, TypeReference valueTypeRef) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, valueTypeRef);
        } catch (IOException e) {
            LOG.warn("parse json error:{}", jsonString, e);
            throw Throwables.propagate(e);
        }
    }

    /**
     * 反序列化复杂Collection如List<Bean>, 先使用constructCollectionType()或constructMapType()构造类型, 然后调用本函数.
     *
     * @see #constructCollectionType(Class, Class)
     */
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            LOG.warn("parse json error:{}", jsonString, e);
            return null;
        }
    }

    /**
     * 构造Collection类型
     */
    public JavaType constructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 构造Map类型
     */
    public JavaType constructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    /**
     * 当JSON里只含有Bean的部分属性时，更新一个已存在的bean,只覆盖部分属性
     */
    public void update(String jsonString, Object object) {
        try {
            mapper.readerForUpdating(object).readValue(jsonString);
        } catch (IOException e) {
            LOG.warn("update  object:{} error. json :{}", object, jsonString, e);
        }
    }

    /**
     * 输出JSONP格式数据.
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

}
