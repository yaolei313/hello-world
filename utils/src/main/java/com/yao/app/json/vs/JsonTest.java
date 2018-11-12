package com.yao.app.json.vs;

import com.yao.app.json.JsonMapper;
import com.yao.app.json.object.CodeEnum;

/**
 * Created by yaolei02 on 2018/9/5.
 */
public class JsonTest {
    public static void main(String[] args) {
        CodeEnum t1 = CodeEnum.TIPS;

        String json1 = JsonMapper.NON_NULL_MAPPER.toJson(t1);
        System.out.println(json1);

        CodeEnum t2 = JsonMapper.NON_NULL_MAPPER.fromJson(json1, CodeEnum.class);
        System.out.println(t1 == t2);
    }
}
