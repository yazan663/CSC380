package dao;

import db.DBConnection;
import models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {

    public boolean insertProduct(Product p) {
        String sql = "INSERT INTO Product (Product_ID, Name, Price, Stock) VALUES (?, ?, ?, ?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, p.getProductId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getPrice());
            ps.setInt(4, p.getStock());

            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM Product WHERE Product_ID = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int productId = rs.getInt("Product_ID");
                String name = rs.getString("Name");
                String price = rs.getString("Price");
                int stock = rs.getInt("Stock");

                return new Product(productId, name, price, stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProductStock(int id, int newStock) {
        String sql = "UPDATE Product SET Stock = ? WHERE Product_ID = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, newStock);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM Product WHERE Product_ID = ?";
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
