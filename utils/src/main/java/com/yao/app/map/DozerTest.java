package com.yao.app.map;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

public class DozerTest {

    public static void main(String[] args) {
        Student stu = new Student();
        stu.setId(196907);
        stu.setName("李白");

        // 默认配置文件dozerBeanMapping.xml
        // Mapper beanMapper = DozerBeanMapperSingletonWrapper.getInstance();
        
        List<String> mappingFiles = new ArrayList<String>();
        mappingFiles.add("dozer/StudentMapping.xml");
        Mapper beanMapper = new DozerBeanMapper(mappingFiles);
        
        StudentDTO stuDto = beanMapper.map(stu, StudentDTO.class);
        
        System.out.println(stuDto.getId());
        System.out.println(stuDto.getStuName());
        
        // -------------------------------
        
        Student stu2 = new Student();
        stu2.setId(196907);
        
        StudentDTO stuDto2 = new StudentDTO();
        stuDto2.setStuName("李白");
        beanMapper.map(stu2, stuDto2);
        
        System.out.println(stuDto2.getId());
        System.out.println(stuDto2.getStuName());
        
        // -----------
        Student2 t2 = beanMapper.map(stu, Student2.class);
        System.out.println(t2.getId());
        System.out.println(t2.getName());
    }

}
