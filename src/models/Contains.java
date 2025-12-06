package models;

public class Contains {

    private int orderId;
    private int productId;
    private String quantity;

    public Contains(int orderId, int productId, String quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public String getQuantity() {
        return quantity;
    }

    // Setters (if needed)
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Contains: orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity;
    }
}
