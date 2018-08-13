package com.BugzTests;

import java.io.Serializable;

public class Guardian extends Person implements Serializable {


    // override the toString method to return a string description of the object Guardian
    @Override
    public String toString() {

        return  "Name: "+name +", ID: "+IdNo +", Phone: "+ phoneNumber+ "}" ;
    }




}
