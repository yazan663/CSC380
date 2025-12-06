package dao;

import db.DBConnection;
import models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ProductDAO {

	public List<Product> searchByName(String name) {
	    List<Product> list = new ArrayList<>();
	    String sql = "SELECT * FROM Product WHERE Name LIKE ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, "%" + name + "%");
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Product p = new Product(
	                rs.getInt("Product_ID"),
	                rs.getString("Name"),
	                rs.getString("Price"),
	                rs.getInt("Stock")
	            );
	            list.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	public List<Product> searchByPrice(String price) {
	    List<Product> list = new ArrayList<>();
	    String sql = "SELECT * FROM Product WHERE Price = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, price);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Product p = new Product(
	                rs.getInt("Product_ID"),
	                rs.getString("Name"),
	                rs.getString("Price"),
	                rs.getInt("Stock")
	            );
	            list.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	public List<Product> searchByStock(int stock) {
	    List<Product> list = new ArrayList<>();
	    String sql = "SELECT * FROM Product WHERE Stock = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, stock);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Product p = new Product(
	                rs.getInt("Product_ID"),
	                rs.getString("Name"),
	                rs.getString("Price"),
	                rs.getInt("Stock")
	            );
	            list.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	public boolean updateProductPrice(int id, String newPrice) {
	    String sql = "UPDATE Product SET Price = ? WHERE Product_ID = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, newPrice);
	        stmt.setInt(2, id);

	        int rows = stmt.executeUpdate();
	        return rows > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}


		
	// 1) إضافة منتج
	public boolean insertProduct(Product p) {

	    String sql = "INSERT INTO Product (Product_ID, Name, Price, Stock) " +
	                 "VALUES (?, ?, ?, ?)";

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

 // 2) حذف منتج بالـ ID
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
    
 // 3) إرجاع كل المنتجات لعرضها في الجدول
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM Product ORDER BY Product_ID";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("Product_ID"),
                        rs.getString("Name"),
                        rs.getString("Price"),
                        rs.getInt("Stock")
                );
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


}