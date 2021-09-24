package com.example.taller_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Button cerrar;

    @FXML
    void closev(ActionEvent event) {
    System.exit(0);
    }

    @FXML
    private Button minimizar;

    @FXML
    void minimizarV(ActionEvent event) {
    Stage stage = (Stage) minimizar.getScene().getWindow();
    stage.setIconified(true);

    }
}