package com.blood.dao;
import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.blood.bean.Donor;
import com.blood.util.DBUtil;

public class DonorDAO {
    public Donor findDonor(String donorID) {
        String sql="SELECT * FROM DONOR_TBL WHERE DONOR_ID = ?";
        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, donorID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Donor d = new Donor();
                d.setDonorID(rs.getString("DONOR_ID"));
                d.setFullName(rs.getString("FULL_NAME"));
                d.setGender(rs.getString("GENDER"));
                d.setAge(rs.getInt("AGE"));
                d.setBloodGroup(rs.getString("BLOOD_GROUP"));
                d.setLastDonationDate(rs.getDate("LAST_DONATION_DATE"));
                d.setEligibleFlag(rs.getString("ELIGIBLE_FLAG"));
                d.setMobile(rs.getString("MOBILE"));
                d.setEmail(rs.getString("EMAIL"));
                d.setCity(rs.getString("CITY"));
                d.setStatus(rs.getString("STATUS"));
                return d;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Donor> viewAllDonors() {
        List<Donor>list=new ArrayList<>();
        String sql="SELECT * FROM DONOR_TBL";
        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Donor d = new Donor();
                d.setDonorID(rs.getString("DONOR_ID"));
                d.setFullName(rs.getString("FULL_NAME"));
                d.setGender(rs.getString("GENDER"));
                d.setAge(rs.getInt("AGE"));
                d.setBloodGroup(rs.getString("BLOOD_GROUP"));
                d.setLastDonationDate(rs.getDate("LAST_DONATION_DATE"));
                d.setEligibleFlag(rs.getString("ELIGIBLE_FLAG"));
                d.setMobile(rs.getString("MOBILE"));
                d.setEmail(rs.getString("EMAIL"));
                d.setCity(rs.getString("CITY"));
                d.setStatus(rs.getString("STATUS"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertDonor(Donor donor) {
        String sql = "INSERT INTO DONOR_TBL VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, donor.getDonorID());
            ps.setString(2, donor.getFullName());
            ps.setString(3, donor.getGender());
            ps.setInt(4, donor.getAge());
            ps.setString(5, donor.getBloodGroup());
            ps.setDate(6, donor.getLastDonationDate());
            ps.setString(7, donor.getEligibleFlag());
            ps.setString(8, donor.getMobile());
            ps.setString(9, donor.getEmail());
            ps.setString(10, donor.getCity());
            ps.setString(11, donor.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateDonorStatus(String donorID, String newStatus) {
        String sql = "UPDATE DONOR_TBL SET STATUS = ? WHERE DONOR_ID = ?";
        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setString(2, donorID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateLastDonationDate(String donorID, Date lastDonationDate) {
        String sql = "UPDATE DONOR_TBL SET LAST_DONATION_DATE = ? WHERE DONOR_ID = ?";
        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, lastDonationDate);
            ps.setString(2, donorID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
