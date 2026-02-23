package com.kce.main;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.kce.entity.Donor;
import com.kce.entity.DonationAppointment;
import com.kce.service.BloodService;
import com.kce.exception.AppointmentConflictException;
import com.kce.exception.ValidationException;

public class Main {

    private static BloodService service = new BloodService();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while (true) {

            System.out.println("\n==== BLOOD DONATION SYSTEM ====");
            System.out.println("1. Register Donor");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. View All Donors");
            System.out.println("4. View All Appointments");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {

            /* ==========================
               REGISTER DONOR
            ========================== */
            case 1:
                try {
                    Donor d = new Donor();

                    System.out.print("Enter Donor ID: ");
                    d.setDonorId(sc.nextLine());

                    System.out.print("Enter Full Name: ");
                    d.setDonorName(sc.nextLine());

                    System.out.print("Enter Gender: ");
                    d.setGender(sc.nextLine());

                    System.out.print("Enter Age: ");
                    d.setAge(sc.nextInt());
                    sc.nextLine();

                    System.out.print("Enter Blood Group: ");
                    d.setBloodGroup(sc.nextLine());

                    System.out.print("Enter Contact Number: ");
                    d.setContactNumber(sc.nextLine());

                    boolean ok = service.registerNewDonor(d);

                    System.out.println(ok ? "DONOR REGISTERED" : "REGISTRATION FAILED");

                } catch (ValidationException e) {
                    System.out.println("Validation Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("System Error: " + e.getMessage());
                }
                break;

            /* ==========================
               SCHEDULE APPOINTMENT
            ========================== */
            case 2:
                try {

                    System.out.print("Enter Donor ID: ");
                    String donorId = sc.nextLine();

                    System.out.print("Enter Camp Name: ");
                    String camp = sc.nextLine();

                    System.out.print("Enter Location: ");
                    String location = sc.nextLine();

                    System.out.print("Enter Appointment Date (yyyy-mm-dd): ");
                    Date apptDate = Date.valueOf(sc.nextLine());

                    System.out.print("Enter Time Slot: ");
                    String timeSlot = sc.nextLine();

                    Date bookedDate = new Date(System.currentTimeMillis());

                    boolean ok = service.scheduleAppointment(
                            donorId,
                            camp,
                            location,
                            apptDate,
                            timeSlot,
                            bookedDate,
                            1);

                    System.out.println(ok ? "APPOINTMENT SCHEDULED" : "FAILED");

                } catch (AppointmentConflictException e) {
                    System.out.println("Conflict: " + e.getMessage());
                } catch (ValidationException e) {
                    System.out.println("Validation Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("System Error: " + e.getMessage());
                }
                break;

            /* ==========================
               VIEW DONORS
            ========================== */
            case 3:
                List<Donor> donors = service.getAllDonors();

                for (Donor d : donors) {
                    System.out.println(
                            d.getDonorId() + " | "
                            + d.getDonorName() + " | "
                            + d.getBloodGroup());
                }
                break;

            /* ==========================
               VIEW APPOINTMENTS
            ========================== */
            case 4:
                List<DonationAppointment> list = service.getAllAppointments();

                for (DonationAppointment a : list) {
                    System.out.println(
                            a.getDonorId() + " | "
                            + a.getCampName() + " | "
                            + a.getAppointmentDate());
                }
                break;

            case 5:
                System.out.println("Thank you!");
                sc.close();
                System.exit(0);

            default:
                System.out.println("Invalid choice");
            }
        }
    }
}
