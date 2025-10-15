package com.example.loanvalidation;

public class Beneficiary {
    public String name;
    public String phone;

    public Beneficiary() { } // required for Firestore deserialization

    public Beneficiary(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
