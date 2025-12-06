package gui.controllers;

import dao.ProductDAO;
import gui.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductController {

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtPrice;
    @FXML private TextField txtStock;

    @FXML private ComboBox<String> searchField;
    @FXML private TextField searchValue;

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> colId;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, String> colPrice;
    @FXML private TableColumn<Product, Integer> colStock;

    @FXML private TextArea txtMessages;

    private final ProductDAO dao = new ProductDAO();

    @FXML
    private void initialize() {
        // Table mapping
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getProductId()).asObject());
        colName.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        colPrice.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getPrice()));
        colStock.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getStock()).asObject());

        // Search dropdown
        searchField.setItems(FXCollections.observableArrayList(
                "ID", "Name", "Price", "Stock"
        ));
    }

    @FXML
    private void backToMain() {
        App.setRoot("main_menu");
    }

    @FXML
    private void addProduct() {
        Integer id = parseInt(txtId.getText(), "Product ID");
        if (id == null) return;

        String name  = txtName.getText().trim();
        String price = txtPrice.getText().trim();
        String stockText = txtStock.getText().trim();

        if (name.isEmpty() || price.isEmpty() || stockText.isEmpty()) {
            show("Please fill ID, Name, Price and Stock.");
            return;
        }

        Integer stock = parseInt(stockText, "Stock");
        if (stock == null) return;

        Product p = new Product(id, name, price, stock);

        if (dao.insertProduct(p)) {
            show("Product added.");
            productTable.setItems(
                    FXCollections.observableArrayList(dao.getAllProducts())
            );
        } else {
            show("Error adding product.");
        }
    }


    @FXML
    private void findProduct() {
        String field = searchField.getValue();
        String value = searchValue.getText().trim();

        if (field == null || value.isEmpty()) {
            show("Select a field and enter a value.");
            return;
        }

        List<Product> results = new ArrayList<>();

        switch (field) {
            case "ID":
                Integer id = parseInt(value, "ID");
                if (id != null) {
                    Product p = dao.getProductById(id);
                    if (p != null) results.add(p);
                }
                break;

            case "Name":
                results = dao.searchByName(value);
                break;

            case "Price":
                results = dao.searchByPrice(value);
                break;

            case "Stock":
                Integer stock = parseInt(value, "Stock");
                if (stock != null) results = dao.searchByStock(stock);
                break;
        }

        if (results.isEmpty()) show("No products found.");
        else show(results.size() + " results found.");

        productTable.setItems(FXCollections.observableArrayList(results));
    }

    @FXML
    private void updateProductPrice() {
        Integer id = parseInt(txtId.getText(), "Product ID");
        if (id == null) return;

        String newPrice = txtPrice.getText().trim();
        if (newPrice.isEmpty()) {
            show("Price cannot be empty.");
            return;
        }

        if (dao.updateProductPrice(id, newPrice)) {
            show("Price updated.");
            findProduct();
        } else {
            show("Error updating price.");
        }
    }

    @FXML
    private void deleteProduct() {
        Integer id = parseInt(txtId.getText(), "Product ID");
        if (id == null) return;

        if (dao.deleteProduct(id)) {
            show("Product deleted.");
            productTable.setItems(
                    FXCollections.observableArrayList(dao.getAllProducts())
            );
        } else {
            show("Error deleting product.");
        }
    }
    @FXML
    private void showAllProducts() {
        List<Product> list = dao.getAllProducts();
        productTable.setItems(FXCollections.observableArrayList(list));
        show("Loaded all products.");
    }


    @FXML
    private void clearForm() {
        txtId.clear();
        txtName.clear();
        txtPrice.clear();
        txtStock.clear();
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
