package com.example.loanapp;


public class ApplicationFormHelper {

    public String mname, id_no, loan, contact, email, fblink;


    public ApplicationFormHelper(){

    }

    public ApplicationFormHelper(String mname, String id_no, String loan, String contact, String email, String fblink) {
        this.mname = mname;
        this.id_no = id_no;
        this.loan = loan;
        this.contact = contact;
        this.email = email;
        this.fblink = fblink;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFblink() {
        return fblink;
    }

    public void setFblink(String fblink) {
        this.fblink = fblink;
    }
}
