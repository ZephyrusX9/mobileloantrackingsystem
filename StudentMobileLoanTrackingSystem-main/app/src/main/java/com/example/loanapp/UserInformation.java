package com.example.loanapp;

public class UserInformation {
    String email, id_no, password, username;

    public UserInformation(String email, String id_no, String password, String username) {
        this.email = email;
        this.id_no = id_no;
        this.password = password;
        this.username = username;
    }

    public UserInformation(){

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getId_no() {
        return id_no;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
