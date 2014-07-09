package com.yao.app.json.object;

import java.io.Serializable;

public class Contact implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String mobileNumber;

    private String officeNumber;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

}
