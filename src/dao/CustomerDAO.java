package dao;

import db.DBConnection;
import models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    // INSERT
    public boolean insertCustomer(Customer c) {

        String sql = "INSERT INTO Customer (Customer_ID, Name, Email, Phone, Address) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, c.getCustomerId());
            ps.setString(2, c.getName());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getAddress());

            int rows = ps.executeUpdate();
            return rows == 1;
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
}
