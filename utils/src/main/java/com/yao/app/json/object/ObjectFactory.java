package com.yao.app.json.object;


import java.time.LocalDate;

public class ObjectFactory {
    public static User getDefaultUser(){
        User user = new User();
        
        user.setId("y00196907");
        user.setName("yaolei");

        LocalDate birthDay = LocalDate.of(1987,3,13);
        //Instant instant = birthDay.atStartOfDay(ZoneId.systemDefault()).toInstant();
        //user.setBirthDay(Date.from(instant));
        user.setBirthDay(birthDay);
        
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
