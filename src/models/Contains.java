package models;

public class Contains {

    private int orderId;
    private int productId;
    private String numOfProducts;

    // Extra fields loaded from JOIN with Product
    

    public Contains(int orderId, int productId, String numOfProducts    ) {

        this.orderId = orderId;
        this.productId = productId;
        this.numOfProducts = numOfProducts;
       ;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public String getNumOfProducts() {
        return numOfProducts;
    }

    public int getQuantityInt() {
        try {
            return Integer.parseInt(numOfProducts);
        } catch (Exception e) {
            return 0;
        }
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    // Subtotal = price * quantity
    public String getSubtotal() {
        try {
            int price = Integer.parseInt(productPrice);
            int qty = Integer.parseInt(numOfProducts);
            return String.valueOf(price * qty);
        } catch (Exception e) {
            return "0";
        }
    }

    @Override
    public String toString() {
        return "Contains{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", numOfProducts='" + numOfProducts + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                '}';
    }
}
