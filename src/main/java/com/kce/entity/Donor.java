package com.kce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "DONOR_TBL")
public class Donor {

    @Id
    @Column(name = "DONOR_ID")
    private String donorId;

    @Column(name = "FULL_NAME")
    private String donorName;

    @Column(name = "AGE")
    private int age;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "BLOOD_GROUP")
    private String bloodGroup;

    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;

    // Default constructor
    public Donor() {
    }

    // Parameterized constructor
    public Donor(String donorId, String donorName, int age,
                 String gender, String bloodGroup, String contactNumber) {
        this.donorId = donorId;
        this.donorName = donorName;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.contactNumber = contactNumber;
    }

    // Getters and setters

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
