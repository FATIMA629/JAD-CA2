package com.myshop.userws.dbaccess;

public class User {

    // Unique identifier for a user
    private String userid;
    
    // Age of the user
    private int age;
    
    // Gender of the user
    private String gender;

    // Getter for User ID
    public String getUserid() {
        return userid;
    }

    // Setter for User ID
    public void setUserid(String userid) {
        this.userid = userid;
    }

    // Getter for Age
    public int getAge() {
        return age;
    }

    // Setter for Age
    public void setAge(int age) {
        this.age = age;
    }

    // Getter for Gender
    public String getGender() {
        return gender;
    }

    // Setter for Gender
    public void setGender(String gender) {
        this.gender = gender;
    }

}
