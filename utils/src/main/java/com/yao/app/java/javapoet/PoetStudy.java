package com.yao.app.java.javapoet;

import com.google.common.collect.Lists;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * 一个生成java文件的工具,注意生成的不是byte code
 * 通过生成代码，避免写模板文件，还能保证元数据单一这一真理
 * 场景比如数据库表，各种协议等
 * https://github.com/square/javapoet
 * <p>
 * Created by yaolei02 on 2018/11/23.
 */
public class PoetStudy {
    public static void main(String[] args) throws Exception {

        FieldSpec id = FieldSpec.builder(TypeName.LONG, "id", Modifier.PRIVATE).addJavadoc("数据库自增id").build();

        FieldSpec orderId = FieldSpec.builder(TypeName.LONG, "orderId", Modifier.PRIVATE).addJavadoc("业务订单号").build();

        List<FieldSpec> fields = Lists.newArrayList(id, orderId);

        MethodSpec getOrderId = genGetterMethod(orderId);
        MethodSpec setOrderId = genSetterMethod(orderId);
        MethodSpec constructor = genConstructor(id, orderId);


        //ParameterSpec parameter = ParameterSpec.builder()
        String code = "int total=0;\n" + "for (int i=0;i<10;i++){\n" + "   total += i;\n" + "}\n";
        // $L 代表 literal value，等同直接替换
        // $S 代表 string literal value，会处理转义
        // $T 代表 表示java type
        // $N 代表 引用其他的Spec定义
        String code2 = "int total=$L;\n" + "for (int i=0;i<$L;i++){\n" + "   total += i;\n" + "}\n";
        MethodSpec main =
                MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC).addParameter(String[].class, "args").addCode(code).build();

        ParameterizedTypeName testParam = ParameterizedTypeName.get(List.class, String.class);
        MethodSpec test = MethodSpec.methodBuilder("test").addModifiers(Modifier.PUBLIC).addParameter(testParam, "list").addCode(code2, 0, 20).build();
        MethodSpec test2 =
                MethodSpec.methodBuilder("test").addModifiers(Modifier.PUBLIC).returns(LocalDate.class).addStatement("return new $T", LocalDate.class).build();

        List<MethodSpec> methods = Lists.newArrayList(getOrderId, setOrderId, main, test, test2);


        TypeSpec orderType = TypeSpec.classBuilder("Order").addModifiers(Modifier.PUBLIC).addFields(fields).addMethods(methods).build();

        JavaFile orderFile =
                JavaFile.builder("com.yao.app.java.javapoet.generate", orderType).addFileComment("static import在这里写").addStaticImport(Collections.class, "sort")
                        .build();
        orderFile.writeTo(System.out);

    }

    public static MethodSpec genGetterMethod(FieldSpec fieldSpec) {
        String name = fieldSpec.name;
        String getterName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        MethodSpec getMethod =
                MethodSpec.methodBuilder(getterName).addModifiers(Modifier.PUBLIC).returns(fieldSpec.type).addStatement("return this.$N", fieldSpec).build();

        return getMethod;
    }

    public static MethodSpec genSetterMethod(FieldSpec fieldSpec) {
        String name = fieldSpec.name;
        String setterName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        ParameterSpec parameter = ParameterSpec.builder(fieldSpec.type, fieldSpec.name).build();
        MethodSpec setMethod = MethodSpec.methodBuilder(setterName).addModifiers(Modifier.PUBLIC).addParameter(parameter).returns(TypeName.VOID)
                .addStatement("this.$N = $N", fieldSpec, parameter).build();

        return setMethod;
    }

    public static MethodSpec genConstructor(FieldSpec... properties){

    }
}
