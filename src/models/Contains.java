package models;

public class Contains {

    private int orderId;
    private int productId;
    private String numOfProducts;

    public Contains(int orderId, int productId, String numOfProducts) {
        this.orderId = orderId;
        this.productId = productId;
        this.numOfProducts = numOfProducts;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getNumOfProducts() {
        return numOfProducts;
    }

    public void setNumOfProducts(String numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    @Override
    public String toString() {
        return "Contains{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", numOfProducts='" + numOfProducts + '\'' +
                '}';
    }
}
