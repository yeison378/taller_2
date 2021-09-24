package com.example.taller_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    jug = FXCollections.observableArrayList();
    numero.setCellValueFactory(new PropertyValueFactory("numero"));
    nombre.setCellValueFactory(new PropertyValueFactory("nombre"));
    ubicacion.setCellValueFactory(new PropertyValueFactory("ubicacion"));
    puntos.setCellValueFactory(new PropertyValueFactory("puntos"));
    partida.setCellValueFactory(new PropertyValueFactory("partidas"));
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
       Jugador a = new Jugador(1,"nombre","ubicacion",1,1);
       jug.add(a);
       clasificacion.setItems(jug);
    }

    @FXML
    void mov(ActionEvent event) {


        if(jugador.getX()<(Double)960.0)jugador.setX((int)(Math.random()*40+1)+(jugador.getX()));
        if(jugador1.getX()<(Double)960.0)jugador1.setX((int)(Math.random()*40+1)+(jugador1.getX()));
        if(jugador2.getX()<(Double)960.0)jugador2.setX((int)(Math.random()*40+1)+(jugador2.getX()));
        if(jugador3.getX()<(Double)960.0)jugador3.setX((int)(Math.random()*40+1)+(jugador3.getX()));
        if(jugador4.getX()<(Double)960.0)jugador4.setX((int)(Math.random()*40+1)+(jugador4.getX()));

    }
private ObservableList<Jugador>jug;

    @FXML
    private TableView<Jugador> clasificacion;

    @FXML
    private TableColumn<?, ?> numero;

    @FXML
    private TableColumn<?, ?> nombre;

    @FXML
    private TableColumn<?, ?> ubicacion;

    @FXML
    private TableColumn<?, ?> puntos;

    @FXML
    private TableColumn<?, ?> partida;
}