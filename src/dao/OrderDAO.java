package dao;

import db.DBConnection;
import models.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

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
    public List<Order> searchByDate(String date) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM `Order` WHERE Order_Date LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + date + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order o = new Order(
                    rs.getInt("Order_ID"),
                    rs.getString("Order_Date"),
                    rs.getString("Status"),
                    rs.getString("Order_Price"),
                    rs.getInt("Customer_ID")
                );
                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<Order> searchByStatus(String status) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM `Order` WHERE Status LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + status + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order o = new Order(
                    rs.getInt("Order_ID"),
                    rs.getString("Order_Date"),
                    rs.getString("Status"),
                    rs.getString("Order_Price"),
                    rs.getInt("Customer_ID")
                );
                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<Order> searchByPrice(String price) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM `Order` WHERE Order_Price = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, price);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order o = new Order(
                    rs.getInt("Order_ID"),
                    rs.getString("Order_Date"),
                    rs.getString("Status"),
                    rs.getString("Order_Price"),
                    rs.getInt("Customer_ID")
                );
                list.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<Order> searchByCustomerId(int customerId) {
        List<Order> list = new ArrayList<>();

        String sql = "SELECT * FROM `Order` WHERE Customer_Cutomer_ID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order(
                        rs.getInt("Order_ID"),
                        rs.getString("Order_Date"),
                        rs.getString("Status"),
                        rs.getString("Order_Price"),
                        rs.getInt("Customer_Cutomer_ID")
                );
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    
    public int getNextOrderId() {
        String sql = "SELECT MAX(Order_ID) AS max_id FROM `Order`";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt("max_id") + 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
    
    public boolean updateOrderPrice(int id, double newPrice) {
        String sql = "UPDATE `Order` SET Order_Price = ? WHERE Order_ID = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, String.valueOf(newPrice));
            ps.setInt(2, id);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM `Order` ORDER BY Order_ID";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Order(
                    rs.getInt("Order_ID"),
                    rs.getString("Order_Date"),
                    rs.getString("Status"),
                    rs.getString("Order_Price"),
                    rs.getInt("Customer_Cutomer_ID")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }




}
