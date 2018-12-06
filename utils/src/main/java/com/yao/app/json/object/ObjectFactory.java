package com.yao.app.json.object;



import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class ObjectFactory {
    public static User getDefaultUser(){
        User user = new User();
        
        user.setId("y00196907");
        user.setName("yaolei");
        Instant instant = LocalDate.of(1987,3,13).atStartOfDay(ZoneId.systemDefault()).toInstant();
        user.setBirthDay(Date.from(instant));
        
        Address address = new Address();
        address.setCountry("China");
        address.setProvince("Shandong");
        address.setCity("Zibo");
        address.setDetail("road a");
        user.setAddress(address);
        
        Contact contact = new Contact();
        contact.setMobileNumber("18600735741");
        contact.setOfficeNumber("010-12345678");
        user.setContact(contact);
        
        return user;
    }
    
    public static Group getDefaultGroup(){
        return new Group();
    }
}
