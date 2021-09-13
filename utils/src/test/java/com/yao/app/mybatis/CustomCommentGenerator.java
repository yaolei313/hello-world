package com.yao.app.mybatis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomCommentGenerator extends DefaultCommentGenerator {
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        // 生成方法注释
        method.addJavaDocLine("/**");
        String method_name = method.getName();

        if ("deleteByPrimaryKey".equals(method_name)) {
            method.addJavaDocLine(" * 根据主键删除数据库的记录");
        } else if ("insert".equals(method_name)) {
            method.addJavaDocLine(" * 插入数据库记录");
        } else if ("selectByPrimaryKey".equals(method_name)) {
            method.addJavaDocLine(" * 根据主键获取一条数据库记录");
        } else if ("updateByPrimaryKey".equals(method_name)) {
            method.addJavaDocLine(" * 根据主键来更新数据库记录");
        } else if ("selectAll".equals(method_name)) {
            method.addJavaDocLine(" * 获取全部数据库记录");
        }
        method.addJavaDocLine(" *");
        List<Parameter> parameterList = method.getParameters();
        String paramName;
        for (Parameter parameter : parameterList) {
            paramName = parameter.getName();
            method.addJavaDocLine(" * @param " + paramName);
        }
        method.addJavaDocLine(" */");
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String author = System.getProperty("user.name");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * 数据库表：" + introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(" * 数据库注释: " + introspectedTable.getRemarks());
        topLevelClass.addJavaDocLine(" * ");
        topLevelClass.addJavaDocLine(" * @author " + author);
        topLevelClass.addJavaDocLine(" * @date " + dateFormat.format(new Date()));
        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // 添加字段注释
        StringBuffer sb = new StringBuffer();
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * <pre>");
        if (introspectedColumn.getRemarks() != null)
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
        sb.append(" * 表字段 : ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" * </pre>");
        field.addJavaDocLine(" */");
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // get方法注释不需要
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // set方法注释不需要
    }

    @Override
    public void addComment(XmlElement xmlElement) {
        // 不需要
    }
}
