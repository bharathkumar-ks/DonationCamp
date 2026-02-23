package com.blood.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.blood.bean.DonationAppointment;
import com.blood.util.DBUtil;

public class DonationAppointmentDAO {
    public int generateAppointmentID() {
        String sql = "SELECT APPOINTMENT_SEQ.NEXTVAL FROM DUAL";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public boolean insertAppointment(DonationAppointment appt) {
        String sql = "INSERT INTO DONATION_APPT_TBL VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1,appt.getAppointmentID());
            ps.setString(2,appt.getDonorID());
            ps.setString(3,appt.getCampName());
            ps.setString(4,appt.getCampLocation());
            ps.setDate(5,appt.getAppointmentDate());
            ps.setString(6,appt.getTimeSlot());
            ps.setDate(7,appt.getRegistrationDate());
            ps.setInt(8,appt.getUnitsPlanned());
            ps.setInt(9,appt.getUnitsCollected());
            ps.setString(10,appt.getAppointmentStatus());
            ps.setString(11,appt.getRemarks());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public DonationAppointment findAppointment(int appointmentID) {
        String sql = "SELECT * FROM DONATION_APPT_TBL WHERE APPOINTMENT_ID=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, appointmentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractAppointment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<DonationAppointment> findAppointmentsByDonor(String donorID) {
        List<DonationAppointment> list = new ArrayList<>();
        String sql = "SELECT * FROM DONATION_APPT_TBL WHERE DONOR_ID=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, donorID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<DonationAppointment> findAppointmentsByCampAndDate(String campName,Date appointmentDate) {
        List<DonationAppointment> list = new ArrayList<>();
        String sql = "SELECT * FROM DONATION_APPT_TBL WHERE CAMP_NAME=? AND APPOINTMENT_DATE=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, campName);
            ps.setDate(2, appointmentDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public DonationAppointment findConflictingAppointment(String donorID, Date appointmentDate, String timeSlot) {
        String sql = "SELECT * FROM DONATION_APPT_TBL WHERE DONOR_ID=? AND APPOINTMENT_DATE=? AND TIME_SLOT=? AND APPOINTMENT_STATUS='SCHEDULED'";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, donorID);
            ps.setDate(2, appointmentDate);
            ps.setString(3, timeSlot);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractAppointment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DonationAppointment> findFutureScheduledAppointmentsForDonor(
            String donorID, Date referenceDate) {
        List<DonationAppointment> list = new ArrayList<>();
        String sql = "SELECT * FROM DONATION_APPT_TBL WHERE DONOR_ID=? AND APPOINTMENT_STATUS='SCHEDULED' AND APPOINTMENT_DATE >= ?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, donorID);
            ps.setDate(2, referenceDate);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractAppointment(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateAppointmentStatusAndUnits(int appointmentID, String newStatus, int unitsCollected, String remarks) {
        String sql = "UPDATE DONATION_APPT_TBL SET APPOINTMENT_STATUS=?, UNITS_COLLECTED=?, REMARKS=? WHERE APPOINTMENT_ID=?";
        try (Connection con = DBUtil.getDBConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, unitsCollected);
            ps.setString(3, remarks);
            ps.setInt(4, appointmentID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private DonationAppointment extractAppointment(ResultSet rs) throws SQLException {
    	DonationAppointment a = new DonationAppointment();
        
    	a.setAppointmentID(rs.getInt("APPOINTMENT_ID"));
        a.setDonorID(rs.getString("DONOR_ID"));
        a.setCampName(rs.getString("CAMP_NAME"));
        a.setCampLocation(rs.getString("CAMP_LOCATION"));
        a.setAppointmentDate(rs.getDate("APPOINTMENT_DATE"));
        a.setTimeSlot(rs.getString("TIME_SLOT"));
        a.setRegistrationDate(rs.getDate("REGISTRATION_DATE"));
        a.setUnitsPlanned(rs.getInt("UNITS_PLANNED"));
        a.setUnitsCollected(rs.getInt("UNITS_COLLECTED"));
        a.setAppointmentStatus(rs.getString("APPOINTMENT_STATUS"));
        a.setRemarks(rs.getString("REMARKS"));
        return a;
    }
}
