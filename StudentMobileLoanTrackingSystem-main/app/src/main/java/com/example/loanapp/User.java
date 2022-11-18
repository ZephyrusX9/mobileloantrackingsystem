package com.example.loanapp;

public class User {

    public String id_no, username, email, password;

    public User(){

    }

    public User(String id_no, String username, String email, String password) {
        this.id_no = id_no;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
