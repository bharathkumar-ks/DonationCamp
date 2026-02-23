package com.kce.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.kce.entity.Donor;
import com.kce.util.HibernateUtil;

public class DonorDAO {

    // 1. Save Donor
    public void saveDonor(Donor donor) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.persist(donor);

            tx.commit();
            System.out.println("Donor saved successfully!");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // 2. Get all donors
    public List<Donor> getAllDonors() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Donor> query = session.createQuery("from Donor", Donor.class);
            return query.list();
        }
    }

    // 3. Get donor by ID
    public Donor getDonorById(String donorId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Donor.class, donorId);
        }
    }

    // 4. Update donor
    public void updateDonor(Donor donor) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            session.merge(donor);

            tx.commit();
            System.out.println("Donor updated successfully!");

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // 5. Delete donor
    public void deleteDonor(String donorId) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Donor donor = session.get(Donor.class, donorId);

            if (donor != null) {
                session.remove(donor);
                System.out.println("Donor deleted successfully!");
            } else {
                System.out.println("Donor not found!");
            }

            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
