module com.example.taller_2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.taller_2 to javafx.fxml;
    exports com.example.taller_2;
}