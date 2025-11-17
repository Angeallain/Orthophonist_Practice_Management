module com.example.demo1 {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.club.Controller to javafx.fxml;
    exports com.club.Controller;
    exports com.club.Model;
}