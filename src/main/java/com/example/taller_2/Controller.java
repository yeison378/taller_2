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
import java.util.*;

// * cualquier cosa :)
// ! hola
// ? hellow


public class Controller implements Initializable {

    private ArrayList<Jugador> player;
    public ObservableList<Jugador> jugP;
    private ObservableList<Jugador> jug;
    private Juego j;
    private List<ImageView> imagenes;

    @FXML
    private TableView<Jugador> clasificacion;

    @FXML
    private TableColumn<?, ?> startingNumber;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> location;

    @FXML
    private TableColumn<?, ?> pointsObtained;

    @FXML
    private TableColumn<?, ?> numberMatches;

    //Se le colocan los datos a la tablas
    public void tablaJugadores() {
        for (Jugador j : player) {
            Jugador p = j;
            jugP.add(p);
            clasificacion.setItems(jugP);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Collections.addAll(imagenes = new ArrayList<>(), jugador, jugador1, jugador2, jugador3,jugador4);
        posJug = 0;
        j = new Juego(5, 960);
        player = new ArrayList<>();
        ServiceJugador serviceJ = new ServiceJugador("", "jugadores.csv");
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
        seleccionJugador.setText(player.get(posJug).getName());
    }

    @FXML
    private Button cerrar;

    @FXML
    void closev(ActionEvent event) {
        System.exit(0);
    }

    // * LO que hace es iniciar a mover los carritos
    @FXML
    void iniciarJuego(ActionEvent event) {
        for (int i = 0; i < player.size(); i++) {
            Thread t = new Thread(moverImg(i));
            t.start();
        }
        j.runn(0, (int) (Math.random() * 500 + 100));
        j.runn(1, (int) (Math.random() * 500 + 100));
        j.runn(2, (int) (Math.random() * 500 + 100));
        j.runn(3, (int) (Math.random() * 500 + 100));
        j.runn(4, (int) (Math.random() * 500 + 100));
        j.hilo();
    }

    @FXML
    private Button minimizar;

    @FXML
    void minimizarV(ActionEvent event) {
        Stage stage = (Stage) minimizar.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private ImageView jugador, jugador1, jugador2, jugador3, jugador4;

    @FXML
    private Button mover, aleatorio;

    @FXML
    private TextField texto;

    @FXML
    void moverEnX(ActionEvent event) {
        Jugador j = new Jugador(1, "nombre", "ubicacion", 1, 1);
        jug.add(j);
        clasificacion.setItems(jug);
    }

    @FXML
    void mov(ActionEvent event) throws InterruptedException {

//        Platform.runLater(a());

    }

 /*   private Runnable a(){
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
    }*/

    //    * seleccion de jugador ------------------------------------------------------------
    @FXML
    private Button adelanteJUgador;

    @FXML
    private Button atrasJugador;

    @FXML
    private TextField seleccionJugador;
    private int posJug;

    @FXML
    void movJugador(ActionEvent event) {
        Object obj = event.getSource();
        if (obj.equals(adelanteJUgador)) {

            if (posJug < player.size() - 1) posJug++;
            else posJug = 0;
            seleccionJugador.setText(player.get(posJug).getName());
        } else {

            if (posJug > 0) posJug--;
            else posJug = player.size() - 1;
            seleccionJugador.setText(player.get(posJug).getName());
        }
    }

    public Runnable moverImg(int j) {
        return () -> {
            while ( imagenes.get(j).getX()< 960) {
                Platform.runLater(() -> {
                    imagenes.get(j).setX(this.j.getJugador().get(j).getKm());
                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Runnable moverImg1() {
        return () -> {
            while (jugador1.getX() < 100) {
                Platform.runLater(() -> jugador1.setX(j.getJugador().get(1).getKm()));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Runnable moverImg2() {
        return () -> {
            while (jugador2.getX() < 960) {
                Platform.runLater(() -> jugador2.setX(j.getJugador().get(2).getKm()));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public Runnable moverImg3() {
        return () -> {
            while (jugador3.getX() < 960) {
                Platform.runLater(() -> jugador3.setX(j.getJugador().get(3).getKm()));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}