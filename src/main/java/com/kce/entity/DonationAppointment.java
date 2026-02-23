package com.kce.entity;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "DONATION_APPT_TBL")
public class DonationAppointment {

    @Id
    @Column(name = "APPOINTMENT_ID")
    private int appointmentId;

    @Column(name = "DONOR_ID")
    private String donorId;

    @Column(name = "CAMP_NAME")
    private String campName;

    @Column(name = "APPOINTMENT_DATE")
    private Date appointmentDate;

    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "LOCATION")
    private String location;

    @Column(name = "TIME_SLOT")
    private String timeSlot;

    @Column(name = "BOOKED_DATE")
    private Date bookedDate;


    // Default constructor
    public DonationAppointment() {
    }

    // Parameterized constructor
    public DonationAppointment(int appointmentId, String donorId,
                               String campName, Date appointmentDate,
                               String status,String location,String timeSlot,Date bookedDate) {
        this.appointmentId = appointmentId;
        this.donorId = donorId;
        this.campName = campName;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.location=location;
        this.timeSlot=timeSlot;
        this.bookedDate=bookedDate;
    }

    // Getters and setters

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
    public Date getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(Date bookedDate) {
        this.bookedDate = bookedDate;
    }
}
