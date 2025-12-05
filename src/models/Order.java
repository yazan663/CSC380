package models;

public class Order {

    private int orderId;
    private String orderDate;
    private String status;
    private String orderPrice;
    private int customerId;

    public Order(int orderId, String orderDate, String status, String orderPrice, int customerId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.status = status;
        this.orderPrice = orderPrice;
        this.customerId = customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate='" + orderDate + '\'' +
                ", status='" + status + '\'' +
                ", orderPrice='" + orderPrice + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
