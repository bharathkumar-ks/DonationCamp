package com.kce.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.kce.entity.DonationAppointment;
import com.kce.util.HibernateUtil;

public class DonationAppointmentDAO {

    // Save appointment
    public void saveAppointment(DonationAppointment appt) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.persist(appt);

            tx.commit();
            System.out.println("Appointment saved successfully!");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // View all appointments
    public List<DonationAppointment> getAllAppointments() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DonationAppointment> query =
                    session.createQuery("from DonationAppointment", DonationAppointment.class);

            return query.list();
        }
    }

    // Get appointment by ID
    public DonationAppointment getAppointmentById(int id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(DonationAppointment.class, id);
        }
    }

    // Update appointment
    public void updateAppointment(DonationAppointment appt) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.merge(appt);

            tx.commit();
            System.out.println("Appointment updated successfully!");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // Delete appointment
    public void deleteAppointment(int id) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            DonationAppointment appt =
                    session.get(DonationAppointment.class, id);

            if (appt != null) {
                session.remove(appt);
                System.out.println("Appointment deleted!");
            } else {
                System.out.println("Appointment not found!");
            }

            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
