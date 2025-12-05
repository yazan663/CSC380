package dao;

import db.DBConnection;
import models.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class PaymentDAO {

    public boolean insertPayment(Payment p) {
        String sql = "INSERT INTO Payment (Payment_ID, Payment_Date, Amount, Method, Order_Order_ID) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, p.getPaymentId());
            ps.setString(2, p.getPaymentDate());
            ps.setString(3, p.getAmount());
            ps.setString(4, p.getMethod());
            ps.setInt(5, p.getOrderId());

            int rows = ps.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Payment getPaymentById(int id) {
        String sql = "SELECT * FROM Payment WHERE Payment_ID = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int paymentId = rs.getInt("Payment_ID");
                String paymentDate = rs.getString("Payment_Date");
                String amount = rs.getString("Amount");
                String method = rs.getString("Method");
                int orderId = rs.getInt("Order_Order_ID");

                return new Payment(paymentId, paymentDate, amount, method, orderId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePaymentMethod(int id, String newMethod) {
        String sql = "UPDATE Payment SET Method = ? WHERE Payment_ID = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, newMethod);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            return rows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePayment(int id) {
        String sql = "DELETE FROM Payment WHERE Payment_ID = ?";
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
    public List<Payment> searchByDate(String date) {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM Payment WHERE Payment_Date LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + date + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Payment_ID");
                String pDate = rs.getString("Payment_Date");
                String amount = rs.getString("Amount");
                String method = rs.getString("Method");
                int orderId = rs.getInt("Order_Order_ID");

                list.add(new Payment(id, pDate, amount, method, orderId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<Payment> searchByAmount(String amountValue) {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM Payment WHERE Amount = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, amountValue);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Payment_ID");
                String pDate = rs.getString("Payment_Date");
                String amount = rs.getString("Amount");
                String method = rs.getString("Method");
                int orderId = rs.getInt("Order_Order_ID");

                list.add(new Payment(id, pDate, amount, method, orderId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<Payment> searchByMethod(String methodValue) {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM Payment WHERE Method LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + methodValue + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Payment_ID");
                String pDate = rs.getString("Payment_Date");
                String amount = rs.getString("Amount");
                String method = rs.getString("Method");
                int orderId = rs.getInt("Order_Order_ID");

                list.add(new Payment(id, pDate, amount, method, orderId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<Payment> searchByOrderId(int orderIdValue) {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM Payment WHERE Order_Order_ID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderIdValue);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Payment_ID");
                String pDate = rs.getString("Payment_Date");
                String amount = rs.getString("Amount");
                String method = rs.getString("Method");
                int orderId = rs.getInt("Order_Order_ID");

                list.add(new Payment(id, pDate, amount, method, orderId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
