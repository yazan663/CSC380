package gui.controllers;

import dao.CustomerDAO;
import gui.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Customer;

public class CustomerController {

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;
    @FXML private TextField txtAddress;

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> colId;
    @FXML private TableColumn<Customer, String>  colName;
    @FXML private TableColumn<Customer, String>  colEmail;
    @FXML private TableColumn<Customer, String>  colPhone;
    @FXML private TableColumn<Customer, String>  colAddress;

    @FXML private TextArea txtMessages;

    private final CustomerDAO dao = new CustomerDAO();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getCustomerId()).asObject());
        colName.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        colEmail.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEmail()));
        colPhone.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPhone()));
        colAddress.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getAddress()));
    }

    @FXML
    private void backToMain() {
        App.setRoot("main_menu");
    }

    @FXML
    private void addCustomer() {
        Integer id = parseInt(txtId.getText(), "ID");
        if (id == null) return;

        Customer c = new Customer(id,
                txtName.getText(),
                txtEmail.getText(),
                txtPhone.getText(),
                txtAddress.getText());

        boolean ok = dao.insertCustomer(c);
        if (ok) {
            customerTable.setItems(FXCollections.observableArrayList(c));
            show("Customer added!");
        } else show("Error adding customer.");
    }

    @FXML
    private void findCustomer() {
        Integer id = parseInt(txtId.getText(), "ID");
        if (id == null) return;

        Customer c = dao.getCustomerById(id);
        if (c == null) {
            show("Customer not found.");
            return;
        }

        txtName.setText(c.getName());
        txtEmail.setText(c.getEmail());
        txtPhone.setText(c.getPhone());
        txtAddress.setText(c.getAddress());

        customerTable.setItems(FXCollections.observableArrayList(c));
        show("Customer loaded.");
    }

    @FXML
    private void updateCustomerEmail() {
        Integer id = parseInt(txtId.getText(), "ID");
        if (id == null) return;

        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            show("Email cannot be empty");
            return;
        }

        if (dao.updateCustomerEmail(id, email)) {
            show("Email updated.");
            findCustomer();
        } else show("Error updating email.");
    }

    @FXML
    private void deleteCustomer() {
        Integer id = parseInt(txtId.getText(), "ID");
        if (id == null) return;

        if (dao.deleteCustomer(id)) {
            show("Customer deleted.");
            clearForm();
            customerTable.setItems(FXCollections.observableArrayList());
        } else show("Error deleting customer.");
    }

    @FXML
    private void clearForm() {
        txtId.clear();
        txtName.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtAddress.clear();
        txtMessages.clear();
    }

    private Integer parseInt(String text, String field) {
        try {
            return Integer.parseInt(text.trim());
        } catch (Exception e) {
            show(field + " must be a number.");
            return null;
        }
    }

    private void show(String msg) {
        txtMessages.setText(msg);
    }
}