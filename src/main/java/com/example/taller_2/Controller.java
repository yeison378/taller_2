package com.example.taller_2;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

// * cualquier cosa :)
// ! hola
// ? hellow


public class Controller implements Initializable {

    private ArrayList<Jugador> player;
    public ObservableList<Jugador> jugP;
    private ObservableList<Jugador>jug;

    @FXML
    private TableView<Jugador> clasificacion;

    @FXML
    private TableColumn<?,?> startingNumber;

    @FXML
    private TableColumn<?,?> name;

    @FXML
    private TableColumn<?,?> location;

    @FXML
    private TableColumn<?,?> pointsObtained;

    @FXML
    private TableColumn<?,?> numberMatches;

    //Se le colocan los datos a la tablas
    public void tablaJugadores(){
        for (Jugador j: player){
            Jugador p = j;
            jugP.add(p);
            clasificacion.setItems(jugP);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player = new ArrayList<>();
        ServiceJugador serviceJ = new ServiceJugador("","jugadores.csv");
        try {
            serviceJ.loadDate();
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        player = serviceJ.getJugador();
        try {
            serviceJ.dumpData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jugP = FXCollections.observableArrayList();
        startingNumber.setCellValueFactory(new PropertyValueFactory("startingNumber"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        location.setCellValueFactory(new PropertyValueFactory("location"));
        pointsObtained.setCellValueFactory(new PropertyValueFactory("pointsObtained"));
        numberMatches.setCellValueFactory(new PropertyValueFactory("numberMatches"));
        tablaJugadores();
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
        Jugador j = new Jugador(1,"nombre","ubicacion",1,1);
       jug.add(j);
       clasificacion.setItems(jug);
    }

    @FXML
    void mov(ActionEvent event) throws InterruptedException {

        Platform.runLater(a());

    }

    private Runnable a(){
        return ()->{
            for(int i = 0; i<192;i++){


                if(jugador.getX()<(Double)960.0){
                    Platform.runLater(()->jugador.setX((int)(Math.random()*40+1)+(jugador.getX())));

                }
                if(jugador1.getX()<(Double)960.0){
                    Platform.runLater(()->jugador1.setX((int)(Math.random()*40+1)+(jugador1.getX())));

                }
                if(jugador2.getX()<(Double)960.0){
                    Platform.runLater(()->jugador2.setX((int)(Math.random()*40+1)+(jugador2.getX())));

                }
                if(jugador3.getX()<(Double)960.0){
                    Platform.runLater(()->jugador3.setX((int)(Math.random()*40+1)+(jugador3.getX())));

                }
                if(jugador4.getX()<(Double)960.0){
                    Platform.runLater(()->jugador4.setX((int)(Math.random()*40+1)+(jugador4.getX())));

                }
                System.out.println("-> "+i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}