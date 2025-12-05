package gui.controllers;

import dao.ShipmentDAO;
import gui.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Shipment;

import java.util.ArrayList;
import java.util.List;

public class ShipmentController {

    @FXML private TextField txtId;
    @FXML private TextField txtDate;
    @FXML private TextField txtTrackNum;
    @FXML private TextField txtStatus;
    @FXML private TextField txtOrderId;

    @FXML private ComboBox<String> searchField;
    @FXML private TextField searchValue;

    @FXML private TableView<Shipment> shipmentTable;
    @FXML private TableColumn<Shipment, Integer> colId;
    @FXML private TableColumn<Shipment, String> colDate;
    @FXML private TableColumn<Shipment, String> colTrackNum;
    @FXML private TableColumn<Shipment, String> colStatus;
    @FXML private TableColumn<Shipment, Integer> colOrderId;

    @FXML private TextArea txtMessages;

    private final ShipmentDAO dao = new ShipmentDAO();

    @FXML
    private void initialize() {

        colId.setCellValueFactory(c ->
            new javafx.beans.property.SimpleIntegerProperty(c.getValue().getShipmentId()).asObject());
        colDate.setCellValueFactory(c ->
            new javafx.beans.property.SimpleStringProperty(c.getValue().getShipmentDate()));
        colTrackNum.setCellValueFactory(c ->
            new javafx.beans.property.SimpleStringProperty(c.getValue().getTrackNum()));
        colStatus.setCellValueFactory(c ->
            new javafx.beans.property.SimpleStringProperty(c.getValue().getShipStatus()));
        colOrderId.setCellValueFactory(c ->
            new javafx.beans.property.SimpleIntegerProperty(c.getValue().getOrderId()).asObject());

        searchField.setItems(FXCollections.observableArrayList(
                "ID", "Date", "TrackingNumber", "Status", "OrderID"
        ));
    }

    @FXML
    private void backToMain() {
        App.setRoot("shipments");
        App.setRoot("main_menu");
    }

    @FXML
    private void addShipment() {
        Integer id = parseInt(txtId.getText(), "Shipment ID");
        Integer orderId = parseInt(txtOrderId.getText(), "Order ID");
        if (id == null || orderId == null) return;

        Shipment s = new Shipment(
            id,
            txtDate.getText(),
            txtTrackNum.getText(),
            txtStatus.getText(),
            orderId
        );

        if (dao.insertShipment(s)) {
            show("Shipment added.");
            shipmentTable.setItems(FXCollections.observableArrayList(s));
        } else {
            show("Error adding shipment.");
        }
    }

    @FXML
    private void findShipment() {
        String field = searchField.getValue();
        String value = searchValue.getText().trim();

        if (field == null || value.isEmpty()) {
            show("Select a field and enter a value.");
            return;
        }

        List<Shipment> results = new ArrayList<>();

        switch (field) {
            case "ID":
                Integer id = parseInt(value, "Shipment ID");
                if (id != null) {
                    Shipment s = dao.getShipmentById(id);
                    if (s != null) results.add(s);
                }
                break;

            case "Date":
                results = dao.searchByDate(value);
                break;

            case "TrackingNumber":
                results = dao.searchByTrackNum(value);
                break;

            case "Status":
                results = dao.searchByStatus(value);
                break;

            case "OrderID":
                Integer oid = parseInt(value, "Order ID");
                if (oid != null) results = dao.searchByOrderId(oid);
                break;
        }

        if (results.isEmpty()) show("No shipments found.");
        else show(results.size() + " shipment(s) found.");

        shipmentTable.setItems(FXCollections.observableArrayList(results));
    }

    @FXML
    private void updateShipmentStatus() {
        Integer id = parseInt(txtId.getText(), "Shipment ID");
        if (id == null) return;

        String status = txtStatus.getText().trim();
        if (status.isEmpty()) {
            show("Status cannot be empty.");
            return;
        }

        if (dao.updateShipmentStatus(id, status)) {
            show("Status updated.");
            Shipment s = dao.getShipmentById(id);
            if (s != null)
                shipmentTable.setItems(FXCollections.observableArrayList(s));
        } else {
            show("Error updating status.");
        }
    }

    @FXML
    private void deleteShipment() {
        Integer id = parseInt(txtId.getText(), "Shipment ID");
        if (id == null) return;

        if (dao.deleteShipment(id)) {
            show("Shipment deleted.");
            clearForm();
            shipmentTable.setItems(FXCollections.observableArrayList());
        } else {
            show("Could not delete shipment.");
        }
    }

    @FXML
    private void clearForm() {
        txtId.clear();
        txtDate.clear();
        txtTrackNum.clear();
        txtStatus.clear();
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

