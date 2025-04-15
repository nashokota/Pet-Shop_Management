module org.example.petshopmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires fontawesomefx;
    requires com.google.gson;
    requires java.sql;


    opens org.example.petshopmanagement to javafx.fxml;
    exports org.example.petshopmanagement;
}