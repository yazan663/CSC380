package dao;

import db.DBConnection;
import models.Contains;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContainsDAO {

    public boolean insertContains(Contains c) {
        String sql = "INSERT INTO Contains (Order_Order_ID, Product_Product_ID, Num_Of_Products) " +
                     "VALUES (?, ?, ?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, c.getOrderId());
            ps.setInt(2, c.getProductId());
            ps.setString(3, c.getQuantity());


            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Contains> getContainsByOrderId(int orderId) {
        String sql = "SELECT * FROM Contains WHERE Order_Order_ID = ?";
        List<Contains> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int oId = rs.getInt("Order_Order_ID");
                int pId = rs.getInt("Product_Product_ID");
                String num = rs.getString("Num_Of_Products");

                list.add(new Contains(oId, pId, num));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean deleteContainsForOrder(int orderId) {
        String sql = "DELETE FROM Contains WHERE Order_Order_ID = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, orderId);

            int rows = ps.executeUpdate();
            return rows >= 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Contains> getProductsInOrder(int orderId) {
        List<Contains> list = new ArrayList<>();
        String sql = "SELECT * FROM Contains WHERE Order_Order_ID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Contains c = new Contains(
                        rs.getInt("Order_Order_ID"),
                        rs.getInt("Product_Product_ID"),
                        rs.getString("Num_Of_Products")
                );
                list.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
