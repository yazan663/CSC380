package dao;

import db.DBConnection;
import models.Shipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShipmentDAO {

    public boolean insertShipment(Shipment s) {
        String sql = "INSERT INTO Shipment (Shipment_ID, Shipment_Date, Track_num, Ship_Status, Order_Order_ID) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, s.getShipmentId());
            ps.setString(2, s.getShipmentDate());
            ps.setString(3, s.getTrackNum());
            ps.setString(4, s.getShipStatus());
            ps.setInt(5, s.getOrderId());

            int rows = ps.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Shipment getShipmentById(int id) {
        String sql = "SELECT * FROM Shipment WHERE Shipment_ID = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int shipmentId = rs.getInt("Shipment_ID");
                String shipmentDate = rs.getString("Shipment_Date");
                String trackNum = rs.getString("Track_num");
                String shipStatus = rs.getString("Ship_Status");
                int orderId = rs.getInt("Order_Order_ID");

                return new Shipment(shipmentId, shipmentDate, trackNum, shipStatus, orderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateShipmentStatus(int id, String newStatus) {
        String sql = "UPDATE Shipment SET Ship_Status = ? WHERE Shipment_ID = ?";
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

    public boolean deleteShipment(int id) {
        String sql = "DELETE FROM Shipment WHERE Shipment_ID = ?";
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
