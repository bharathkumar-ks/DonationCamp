package com.kce.service;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kce.entity.Donor;
import com.kce.exception.AppointmentConflictException;
import com.kce.exception.ValidationException;
import com.kce.entity.DonationAppointment;
import com.kce.util.HibernateUtil;

public class BloodService {

    /* =============================
       DONOR REGISTRATION
    ============================== */

    public boolean registerNewDonor(Donor donor) throws ValidationException {

        // ✅ Basic validation
        if (donor.getDonorId() == null || donor.getDonorId().isEmpty()) {
            throw new ValidationException("Donor ID required");
        }

        if (donor.getDonorName() == null || donor.getDonorName().isEmpty()) {
            throw new ValidationException("Name required");
        }

        if (donor.getAge() < 18 || donor.getAge() > 60) {
            throw new ValidationException("Age must be between 18 and 60");
        }

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            // ✅ Check donor already exists
            Donor existing = session.get(Donor.class, donor.getDonorId());
            if (existing != null) {
                throw new ValidationException("Donor already exists");
            }

            session.persist(donor);

            tx.commit();
            return true;

        } catch (ValidationException e) {
            if (tx != null) tx.rollback();
            throw e;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }

        return false;
    }


    /* =============================
       SCHEDULE APPOINTMENT
    ============================== */

    public boolean scheduleAppointment(
            String donorId,
            String campName,
            String location,
            Date appointmentDate,
            String timeSlot,
            Date bookedDate,
            int maxDonations)
            throws ValidationException, AppointmentConflictException {

        if (donorId == null || donorId.isEmpty()) {
            throw new ValidationException("Donor ID required");
        }

        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            // ✅ Check donor exists
            Donor donor = session.get(Donor.class, donorId);
            if (donor == null) {
                throw new ValidationException("Donor not found");
            }

            // ✅ Conflict check
            String hql = "FROM DonationAppointment " +
                    "WHERE donorId = :donorId " +
                    "AND appointmentDate = :date";


            List<DonationAppointment> list =
                    session.createQuery(hql, DonationAppointment.class)
                           .setParameter("donorId", donorId)
                           .setParameter("date", appointmentDate)
                           .list();

            if (!list.isEmpty()) {
                throw new AppointmentConflictException("Appointment already booked on this date");
            }

            // ✅ Save appointment
            DonationAppointment appt = new DonationAppointment();
            appt.setDonorId(donorId);
            appt.setCampName(campName);
            appt.setLocation(location);
            appt.setAppointmentDate(appointmentDate);
            appt.setTimeSlot(timeSlot);
            appt.setBookedDate(bookedDate);
            appt.setStatus("BOOKED");

            session.persist(appt);

            tx.commit();
            return true;

        } catch (ValidationException | AppointmentConflictException e) {
            if (tx != null) tx.rollback();
            throw e;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();

        } finally {
            if (session != null) session.close();
        }

        return false;
    }


    /* =============================
       VIEW DONORS
    ============================== */

    public List<Donor> getAllDonors() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery("FROM Donor", Donor.class).list();
        }
    }


    /* =============================
       VIEW APPOINTMENTS
    ============================== */

    public List<DonationAppointment> getAllAppointments() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery("FROM DonationAppointment", DonationAppointment.class).list();
        }
    }
}
