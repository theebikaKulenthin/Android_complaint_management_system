package com.example.onlinecomplaintmanagement.model;

public class UserModel {
    String email, password, id, phoneNumber, userName, address;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserModel() {
    }

    public UserModel(String email, String password, String id, String phoneNumber, String userName, String address) {
        this.email = email;
        this.password = password;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.address = address;
    }
}
