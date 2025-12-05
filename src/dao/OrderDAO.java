package dao;

import db.DBConnection;
import models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {

    public boolean insertOrder(Order o) {
        String sql = "INSERT INTO `Order` (Order_ID, Order_Date, Status, Order_Price, Customer_Cutomer_ID) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, o.getOrderId());
            ps.setString(2, o.getOrderDate());
            ps.setString(3, o.getStatus());
            ps.setString(4, o.getOrderPrice());
            ps.setInt(5, o.getCustomerId());

            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Order getOrderById(int id) {
        String sql = "SELECT * FROM `Order` WHERE Order_ID = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int orderId = rs.getInt("Order_ID");
                String orderDate = rs.getString("Order_Date");
                String status = rs.getString("Status");
                String orderPrice = rs.getString("Order_Price");
                int customerId = rs.getInt("Customer_Cutomer_ID");

                return new Order(orderId, orderDate, status, orderPrice, customerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateOrderStatus(int id, String newStatus) {
        String sql = "UPDATE `Order` SET Status = ? WHERE Order_ID = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, newStatus);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrder(int id) {
        String sql = "DELETE FROM `Order` WHERE Order_ID = ?";
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
