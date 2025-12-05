package Main;
import java.sql.Connection;
import db.DBConnection;
import dao.*;
import models.*;

public class TestSQL {
    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();
            System.out.println("Connection = " + con);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Test Customer
        CustomerDAO customerDAO = new CustomerDAO();
        CustomerDAO customerDAO1 = new CustomerDAO();

        boolean cDeleted = customerDAO.deleteCustomer(1);
        System.out.println("Customer deleted? " + cDeleted);
       
        Customer c1 = new Customer(1, "Azooz", "azooz@example.com",
                                   "0500000000", "Riyadh");
        boolean inserted = customerDAO.insertCustomer(c1);
        System.out.println("Customer inserted? " + inserted);

        Customer found = customerDAO.getCustomerById(1);
        System.out.println("Customer found = " + found);

       /* boolean updated = customerDAO.updateCustomerEmail(1, "newmail@example.com");
        System.out.println("Customer updated? " + updated);

        boolean deleted = customerDAO.deleteCustomer(1);
        System.out.println("Customer deleted? " + deleted);*/


        // Test Product
        ProductDAO productDAO = new ProductDAO();

        Product p1 = new Product(100, "Keyboard", "150", 10);
        boolean pInserted = productDAO.insertProduct(p1);
        System.out.println("Product inserted? " + pInserted);

        Product foundP = productDAO.getProductById(100);
        System.out.println("Product found = " + foundP);

        boolean stockUpdated = productDAO.updateProductStock(100, 5);
        System.out.println("Product stock updated? " + stockUpdated);

        
        
        // test order
        OrderDAO orderDAO = new OrderDAO();
        Order o1 = new Order(500, "2025-12-05", "Pending", "150", 1);
        boolean oInserted = orderDAO.insertOrder(o1);
        System.out.println("Order inserted? " + oInserted);

        Order foundOrder = orderDAO.getOrderById(500);
        System.out.println("Order found = " + foundOrder);

        boolean statusUpdated = orderDAO.updateOrderStatus(500, "Shipped");
        System.out.println("Order status updated? " + statusUpdated);

        

       
        ShipmentDAO shipmentDAO = new ShipmentDAO();
        Shipment s1 = new Shipment(1000, "2025-12-06", "TRK123", "In Transit", 500);
        boolean sInserted = shipmentDAO.insertShipment(s1);
        System.out.println("Shipment inserted? " + sInserted);

        Shipment foundS = shipmentDAO.getShipmentById(1000);
        System.out.println("Shipment found = " + foundS);

        boolean sUpdated = shipmentDAO.updateShipmentStatus(1000, "Delivered");
        System.out.println("Shipment status updated? " + sUpdated);

       
        
        PaymentDAO paymentDAO = new PaymentDAO();

        Payment pay1 = new Payment(2000, "2025-12-06", "150", "Credit Card", 500);
        boolean pInserted2 = paymentDAO.insertPayment(pay1);
        System.out.println("Payment inserted? " + pInserted2);

        Payment foundPay = paymentDAO.getPaymentById(2000);
        System.out.println("Payment found = " + foundPay);

        boolean methodUpdated = paymentDAO.updatePaymentMethod(2000, "Cash");
        System.out.println("Payment method updated? " + methodUpdated);

        
        
        
        
        
        
        
        
        ContainsDAO containsDAO = new ContainsDAO();

        Contains cRel = new Contains(500, 100, "3");
        boolean relInserted = containsDAO.insertContains(cRel);
        System.out.println("Contains inserted? " + relInserted);

        System.out.println("Contains rows for order 500:");
        for (Contains c : containsDAO.getContainsByOrderId(500)) {
            System.out.println(c);
        }
        
        
        
        

        boolean relDeleted = containsDAO.deleteContainsForOrder(500);
        System.out.println("Contains deleted for order? " + relDeleted);

        boolean payDeleted = paymentDAO.deletePayment(2000);
        System.out.println("Payment deleted? " + payDeleted);
        
        
        
   
        boolean sDeleted = shipmentDAO.deleteShipment(1000);
        System.out.println("Shipment deleted? " + sDeleted);

        boolean oDeleted = orderDAO.deleteOrder(500);
        System.out.println("Order deleted? " + oDeleted);
        boolean pDeleted = productDAO.deleteProduct(100);
        System.out.println("Product deleted? " + pDeleted);
        
        boolean cDeleted1 = customerDAO1.deleteCustomer(1);
        System.out.println("Customer deleted? " + cDeleted1);

    }

    }

