package com.yao.app.nosql;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2019-12-16
 */
public class Address {

    private String street;

    private String city;

    private String zipCode;

    public Address() {
    }

    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}