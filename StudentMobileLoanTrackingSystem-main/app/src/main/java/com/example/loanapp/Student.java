package com.example.loanapp;

public class Student {

    String name, loan;

    public void Student(){

    }

    public Student(String name, String loan) {
        this.name = name;
        this.loan = loan;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }
}
