package models;

public class Shipment {

    private int shipmentId;
    private String shipmentDate;
    private String trackNum;
    private String shipStatus;
    private int orderId;

    public Shipment(int shipmentId, String shipmentDate, String trackNum, String shipStatus, int orderId) {
        this.shipmentId = shipmentId;
        this.shipmentDate = shipmentDate;
        this.trackNum = trackNum;
        this.shipStatus = shipStatus;
        this.orderId = orderId;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getTrackNum() {
        return trackNum;
    }

    public void setTrackNum(String trackNum) {
        this.trackNum = trackNum;
    }

    public String getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(String shipStatus) {
        this.shipStatus = shipStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "shipmentId=" + shipmentId +
                ", shipmentDate='" + shipmentDate + '\'' +
                ", trackNum='" + trackNum + '\'' +
                ", shipStatus='" + shipStatus + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
