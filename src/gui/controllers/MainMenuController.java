package gui.controllers;

import gui.App;
import javafx.fxml.FXML;

public class MainMenuController {

    @FXML
    private void openCustomers() {
        App.setRoot("customers");
    }

    @FXML
    private void openProducts() {
        App.setRoot("products");
    }

    @FXML
    private void openOrders() {
        App.setRoot("orders");
    }

    @FXML
    private void openPayments() {
        App.setRoot("payments");
    }

    @FXML
    private void openShipments() {
        App.setRoot("shipments");
    }

    @FXML
    private void openContains() {
        App.setRoot("contains");
    }
}
