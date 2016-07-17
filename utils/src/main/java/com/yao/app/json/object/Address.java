package com.yao.app.json.object;

import java.io.Serializable;

public class Address implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String country;

    private String province;

    private String city;

    private String detail;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

/*    @JsonIgnore
    @Transient
    @JSONField(serialize = false)
    @Override
    public long getUsedHeapSpace() {
        // TODO Auto-generated method stub
        return 0;
    }

    @JsonIgnore
    @Transient
    @JSONField(serialize = false)
    @Override
    public long getUsedStackSpace() {
        // TODO Auto-generated method stub
        return 0;
    }*/
}
