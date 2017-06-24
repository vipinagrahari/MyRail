package com.android.firebasedemo.myrail.model;

public class Complain {

    public String userId;
    public String category;
    public String pnr;
    public String complain;

    public Complain() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }
}