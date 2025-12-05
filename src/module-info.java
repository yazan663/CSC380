/**
 * 
 */
/**
 * 
 */
module CSC380_Project2 {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens gui to javafx.fxml;
    opens gui.controllers to javafx.fxml;

    exports gui;
    exports models;
}
