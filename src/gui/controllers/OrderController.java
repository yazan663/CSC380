package gui.controllers;

import dao.OrderDAO;
import gui.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Order;

import java.util.ArrayList;
import java.util.List;

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
        Integer id = parseInt(txtId.getText(), "Order ID");
        Integer customerId = parseInt(txtCustomerId.getText(), "Customer ID");
        if (id == null || customerId == null) return;

        Order o = new Order(id,
                txtDate.getText(),
                txtStatus.getText(),
                txtPrice.getText(),
                customerId);

        if (dao.insertOrder(o)) {
            show("Order added successfully.");
            orderTable.setItems(FXCollections.observableArrayList(o));
        } else {
            show("Error adding order.");
        }
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
}
