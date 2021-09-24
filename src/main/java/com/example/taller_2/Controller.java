package com.example.taller_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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

    @FXML
    private ImageView jugador,jugador1,jugador2,jugador3,jugador4;

    @FXML
    private Button mover,aleatorio;

    @FXML
    private TextField texto;

    @FXML
    void moverEnX(ActionEvent event) {
        jugador.setX(Double.parseDouble(texto.getText()));


        jugador1.setX((int)(Math.random()*100+1)+(jugador1.getX()));

        System.out.println(jugador.getX());
    }

    @FXML
    void mov(ActionEvent event) {


        if(jugador.getX()<(Double)960.0)jugador.setX((int)(Math.random()*40+1)+(jugador.getX()));
        if(jugador1.getX()<(Double)960.0)jugador1.setX((int)(Math.random()*40+1)+(jugador1.getX()));
        if(jugador2.getX()<(Double)960.0)jugador2.setX((int)(Math.random()*40+1)+(jugador2.getX()));
        if(jugador3.getX()<(Double)960.0)jugador3.setX((int)(Math.random()*40+1)+(jugador3.getX()));
        if(jugador4.getX()<(Double)960.0)jugador4.setX((int)(Math.random()*40+1)+(jugador4.getX()));

//        jugador.setX(960);
//        jugador1.setX(960);


//        System.out.println(jugador.getX());
//        System.out.println(jugador1.getX());
    }
}