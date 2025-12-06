package dao;

import db.DBConnection;
import models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;


public class CustomerDAO {
	
    private int getNextCustomerId() {
        String sql = "SELECT MAX(Customer_ID) AS max_id FROM Customer";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int max = rs.getInt("max_id");
                return max + 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // لو الجدول فاضي أو صارت مشكلة
        return 1;
    }

 // INSERT
    public boolean insertCustomer(Customer c) {

        // SQL لجلب أكبر ID موجود
        String sqlMax = "SELECT MAX(Customer_ID) AS max_id FROM Customer";

        // SQL للإدخال
        String sqlInsert = "INSERT INTO Customer (Customer_ID, Name, Email, Phone, Address) " +
                           "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            // 1) نجيب أكبر ID + 1
            int newId = 1;
            try (PreparedStatement psMax = con.prepareStatement(sqlMax);
                 ResultSet rs = psMax.executeQuery()) {

                if (rs.next()) {
                    int max = rs.getInt("max_id");
                    // لو الجدول مو فاضي
                    if (max > 0) {
                        newId = max + 1;
                    }
                }
            }

            // نخزن الـ id الجديد في الأوبجكت
            c.setCustomerId(newId);

            // 2) نسوي INSERT
            try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {

                ps.setInt(1, newId);
                ps.setString(2, c.getName());
                ps.setString(3, c.getEmail());
                ps.setString(4, c.getPhone());
                ps.setString(5, c.getAddress());

                int rows = ps.executeUpdate();
                return rows == 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    // SELECT BY ID
    public Customer getCustomerById(int id) {

        String sql = "SELECT * FROM Customer WHERE Customer_ID = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String name    = rs.getString("Name");
                String email   = rs.getString("Email");
                String phone   = rs.getString("Phone");
                String address = rs.getString("Address");

                return new Customer(customerId, name, email, phone, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateCustomerEmail(int id, String newEmail) {

        String sql = "UPDATE Customer SET Email = ? WHERE Customer_ID = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, newEmail);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean deleteCustomer(int id) {

        String sql = "DELETE FROM Customer WHERE Customer_ID = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // ترجع كل العملاء
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();

        String sql = "SELECT * FROM Customer ORDER BY Customer_ID";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Address")
                );
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
