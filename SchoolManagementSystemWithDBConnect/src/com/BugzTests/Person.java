package com.BugzTests;

import java.io.Serializable;

public class Person implements Serializable {
    protected String IdNo;
    protected String name;
    protected String phoneNumber;


    //setters and getters for the class Person
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdNo() {

        return IdNo;
    }

    public void setIdNo(String idNo) {
        IdNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
