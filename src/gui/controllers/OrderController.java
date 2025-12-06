package gui.controllers;

import dao.OrderDAO;
import dao.ProductDAO;
import dao.ContainsDAO;
import gui.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Order;
import models.Product;
import models.Contains;
import dao.ProductDAO;
import dao.ContainsDAO;
import models.Product;
import models.Contains;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class OrderController {

    @FXML private TextField txtId;
    @FXML private TextField txtDate;
    @FXML private TextField txtStatus;
    @FXML private TextField txtPrice;
    @FXML private TextField txtCustomerId;

    @FXML private ComboBox<String> searchField;
    @FXML private TextField searchValue;

    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, Integer> colId;
    @FXML private TableColumn<Order, String> colDate;
    @FXML private TableColumn<Order, String> colStatus;
    @FXML private TableColumn<Order, String> colPrice;
    @FXML private TableColumn<Order, Integer> colCustomer;

    @FXML private TextArea txtMessages;

    private final OrderDAO dao = new OrderDAO();
    private final ProductDAO productDao = new ProductDAO();
    private final ContainsDAO containsDao = new ContainsDAO();


    @FXML
    private void initialize() {

        colId.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getOrderId()).asObject());
        colDate.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getOrderDate()));
        colStatus.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));
        colPrice.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getOrderPrice()));
        colCustomer.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getCustomerId()).asObject());

        searchField.setItems(FXCollections.observableArrayList(
                "ID", "Date", "Status", "Price", "CustomerID"
        ));
    }

    @FXML
    private void backToMain() {
        App.setRoot("main_menu");
    }

    @FXML
    private void addOrder() {

        // 1) Ask for Customer ID
        TextInputDialog cidDialog = new TextInputDialog();
        cidDialog.setTitle("Customer ID");
        cidDialog.setHeaderText("Create new order");
        cidDialog.setContentText("Enter customer ID:");

        Optional<String> cidRes = cidDialog.showAndWait();
        if (!cidRes.isPresent() || cidRes.get().trim().isEmpty()) {
            show("Canceled.");
            return;
        }

        Integer customerId = parseInt(cidRes.get(), "Customer ID");
        if (customerId == null) return;

        // 2) Generate order ID = max + 1
        int orderId = dao.getNextOrderId();

        Order o = new Order(orderId, "2025-01-01", "Pending", "0", customerId);
        if (!dao.insertOrder(o)) {
            show("Error creating order.");
            return;
        }

        int totalItems = 0;
        double totalPrice = 0;

        // 3) Loop: add products
        boolean keepGoing = true;

        while (keepGoing) {

            TextInputDialog pDialog = new TextInputDialog();
            pDialog.setTitle("Add Product");
            pDialog.setHeaderText("Order ID: " + orderId);
            pDialog.setContentText("Enter product ID (leave empty to finish):");

            Optional<String> pRes = pDialog.showAndWait();
            if (!pRes.isPresent() || pRes.get().trim().isEmpty()) break;

            Integer pid = parseInt(pRes.get(), "Product ID");
            if (pid == null) continue;

            Product p = productDao.getProductById(pid);
            if (p == null) {
                show("Product not found.");
                continue;
            }

            TextInputDialog qDialog = new TextInputDialog("1");
            qDialog.setTitle("Quantity");
            qDialog.setHeaderText("Product: " + p.getName() + " | Stock: " + p.getStock());
            qDialog.setContentText("Quantity:");

            Optional<String> qRes = qDialog.showAndWait();
            if (!qRes.isPresent()) continue;

            Integer qty = parseInt(qRes.get(), "Quantity");
            if (qty == null || qty <= 0) {
                show("Invalid quantity.");
                continue;
            }

            if (qty > p.getStock()) {
                show("Not enough stock.");
                continue;
            }

            // Add to Contains table
            Contains c = new Contains(orderId, pid, qty.toString());
            containsDao.insertContains(c);

            // Update product stock
            productDao.updateProductStock(pid, p.getStock() - qty);

            totalItems += qty;
            totalPrice += (qty * Double.parseDouble(p.getPrice()));

            Alert more = new Alert(Alert.AlertType.CONFIRMATION, "Add another product?");
            Optional<ButtonType> moreRes = more.showAndWait();

            if (!moreRes.isPresent() || moreRes.get() != ButtonType.OK)
                keepGoing = false;
        }

        // Update order price
        dao.updateOrderPrice(orderId, totalPrice);

        show(
            "Order Completed!\n" +
            "Order ID: " + orderId + "\n" +
            "Number of items: " + totalItems + "\n" +
            "Total Price: " + totalPrice
        );
    }



    @FXML
    private void findOrder() {
        String field = searchField.getValue();
        String value = searchValue.getText().trim();

        if (field == null || value.isEmpty()) {
            show("Select a field and enter a value.");
            return;
        }

        List<Order> results = new ArrayList<>();

        switch (field) {
            case "ID":
                Integer id = parseInt(value, "Order ID");
                if (id != null) {
                    Order o = dao.getOrderById(id);
                    if (o != null) results.add(o);
                }
                break;

            case "Date":
                results = dao.searchByDate(value);
                break;

            case "Status":
                results = dao.searchByStatus(value);
                break;

            case "Price":
                results = dao.searchByPrice(value);
                break;

            case "CustomerID":
                Integer cid = parseInt(value, "Customer ID");
                if (cid != null) results = dao.searchByCustomerId(cid);
                break;
        }

        if (results.isEmpty()) {
            show("No orders found.");
        } else {
            show(results.size() + " orders found.");
            orderTable.setItems(FXCollections.observableArrayList(results));
        }
    }

    @FXML
    private void updateOrderStatus() {
        Integer id = parseInt(txtId.getText(), "Order ID");
        if (id == null) return;

        String status = txtStatus.getText().trim();
        if (status.isEmpty()) {
            show("Status cannot be empty.");
            return;
        }

        if (dao.updateOrderStatus(id, status)) {
            show("Order status updated.");
            findOrder();
        } else {
            show("Error updating order.");
        }
    }

    @FXML
    private void deleteOrder() {
        Integer id = parseInt(txtId.getText(), "Order ID");
        if (id == null) return;

        if (dao.deleteOrder(id)) {
            show("Order deleted.");
            clearForm();
            orderTable.setItems(FXCollections.observableArrayList());
        } else {
            show("Could not delete order.");
        }
    }

    @FXML
    private void clearForm() {
        txtId.clear();
        txtDate.clear();
        txtStatus.clear();
        txtPrice.clear();
        txtCustomerId.clear();
        searchValue.clear();
        txtMessages.clear();
    }

    private Integer parseInt(String txt, String field) {
        try {
            return Integer.parseInt(txt.trim());
        } catch (Exception e) {
            show(field + " must be a number.");
            return null;
        }
    }

    private void show(String msg) {
        txtMessages.setText(msg);
    }
    
    @FXML
    private void showAllOrders() {
        orderTable.setItems(FXCollections.observableArrayList(dao.getAllOrders()));
        show("Loaded all orders.");
    }
    @FXML
    private void showOrderProducts() {
        Integer id = parseInt(txtId.getText(), "Order ID");
        if (id == null) return;

        List<Contains> list = containsDao.getProductsInOrder(id);

        if (list.isEmpty()) {
            show("No products in this order.");
            return;
        }

        StringBuilder sb = new StringBuilder("Products in order " + id + ":\n");

        for (Contains c : list) {
            sb.append("Product ID: ").append(c.getProductId())
              .append(" | Quantity: ").append(c.getQuantity())
              .append("\n");
        }

        txtMessages.setText(sb.toString());
    }


}
