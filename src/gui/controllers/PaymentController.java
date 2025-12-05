package gui.controllers;

import dao.PaymentDAO;
import gui.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentController {

    @FXML private TextField txtId;
    @FXML private TextField txtDate;
    @FXML private TextField txtAmount;
    @FXML private TextField txtMethod;
    @FXML private TextField txtOrderId;

    @FXML private ComboBox<String> searchField;
    @FXML private TextField searchValue;

    @FXML private TableView<Payment> paymentTable;
    @FXML private TableColumn<Payment, Integer> colId;
    @FXML private TableColumn<Payment, String>  colDate;
    @FXML private TableColumn<Payment, String>  colAmount;
    @FXML private TableColumn<Payment, String>  colMethod;
    @FXML private TableColumn<Payment, Integer> colOrderId;

    @FXML private TextArea txtMessages;

    private final PaymentDAO dao = new PaymentDAO();

    @FXML
    private void initialize() {
        // map table columns
        colId.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getPaymentId()).asObject());
        colDate.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getPaymentDate()));
        colAmount.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getAmount()));
        colMethod.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getMethod()));
        colOrderId.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getOrderId()).asObject());

        // search fields
        searchField.setItems(FXCollections.observableArrayList(
                "ID", "Date", "Amount", "Method", "OrderID"
        ));
    }

    @FXML
    private void backToMain() {
        App.setRoot("main_menu");
    }

    @FXML
    private void addPayment() {
        Integer id = parseInt(txtId.getText(), "Payment ID");
        Integer orderId = parseInt(txtOrderId.getText(), "Order ID");
        if (id == null || orderId == null) return;

        String date = txtDate.getText().trim();
        String amount = txtAmount.getText().trim();
        String method = txtMethod.getText().trim();

        if (date.isEmpty() || amount.isEmpty() || method.isEmpty()) {
            show("Please fill all fields.");
            return;
        }

        Payment p = new Payment(id, date, amount, method, orderId);
        if (dao.insertPayment(p)) {
            show("Payment added.");
            paymentTable.setItems(FXCollections.observableArrayList(p));
        } else {
            show("Error adding payment.");
        }
    }

    @FXML
    private void findPayment() {
        String field = searchField.getValue();
        String value = searchValue.getText().trim();

        if (field == null || value.isEmpty()) {
            show("Select a field and enter a value.");
            return;
        }

        List<Payment> results = new ArrayList<>();

        switch (field) {
            case "ID":
                Integer id = parseInt(value, "Payment ID");
                if (id != null) {
                    Payment p = dao.getPaymentById(id);
                    if (p != null) results.add(p);
                }
                break;

            case "Date":
                results = dao.searchByDate(value);
                break;

            case "Amount":
                results = dao.searchByAmount(value);
                break;

            case "Method":
                results = dao.searchByMethod(value);
                break;

            case "OrderID":
                Integer oid = parseInt(value, "Order ID");
                if (oid != null) results = dao.searchByOrderId(oid);
                break;
        }

        if (results.isEmpty()) show("No payments found.");
        else show(results.size() + " payment(s) found.");

        paymentTable.setItems(FXCollections.observableArrayList(results));
    }

    @FXML
    private void updatePaymentMethod() {
        Integer id = parseInt(txtId.getText(), "Payment ID");
        if (id == null) return;

        String newMethod = txtMethod.getText().trim();
        if (newMethod.isEmpty()) {
            show("Method cannot be empty.");
            return;
        }

        if (dao.updatePaymentMethod(id, newMethod)) {
            show("Method updated.");
            // reload
            Payment p = dao.getPaymentById(id);
            if (p != null) {
                paymentTable.setItems(FXCollections.observableArrayList(p));
            }
        } else {
            show("Error updating method.");
        }
    }

    @FXML
    private void deletePayment() {
        Integer id = parseInt(txtId.getText(), "Payment ID");
        if (id == null) return;

        if (dao.deletePayment(id)) {
            show("Payment deleted.");
            clearForm();
            paymentTable.setItems(FXCollections.observableArrayList());
        } else {
            show("Could not delete payment.");
        }
    }

    @FXML
    private void clearForm() {
        txtId.clear();
        txtDate.clear();
        txtAmount.clear();
        txtMethod.clear();
        txtOrderId.clear();
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
