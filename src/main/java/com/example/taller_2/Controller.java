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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static com.example.taller_2.Juego.playing;

import java.io.IOException;
import java.net.URL;
import java.util.*;

// * cualquier cosa :)
// ! hola
// ? hellow


public class Controller implements Initializable, Comparator<Jugador> {

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

    // * se asiganron los puntaje obtendios a los jugadores de acuerdo a su puesto
    public void asignarPuntajes(){
        Thread th = new Thread(() -> {
            while (j.getPosicion().size()!= 5){
                System.out.println("->");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            j.getPosicion().get(0).setPuntoCarrera(50);
            j.getPosicion().get(1).setPuntoCarrera(40);
            j.getPosicion().get(2).setPuntoCarrera(30);
            j.getPosicion().get(3).setPuntoCarrera(20);
            j.getPosicion().get(4).setPuntoCarrera(10);
            System.out.println("Orden bine: ");
            for(Jugador a: j.getPosicion()){
                setPoints(a.getName(),a.getPuntoCarrera());
                System.out.println(a.getName()+"_>"+a.getPuntoCarrera());
            }
            // ! aquie va el metodo ordenar ...................................................
            ordenarJugaodres();
            resetearTabla();
            borrrarPersistencia();
            agregraPersistencia();
        });
        th.start();
    }

    // * Este metodo lo que hace es ordenar todos los jugadores de acuerdo a su puntaje
    public void ordenarJugaodres(){
        player.sort((o1, o2) -> o1.compareTo(o2));
        for (Jugador a: player){
            System.out.println(a.getName()+"->"+a.getPointsObtained());
        }
        for (int i = 0; i < player.size(); i++) {
            player.get(i).setStartingNumber(i+1);
        }
    }

    // * lo que hace este metodo es sumarle los puntos nuevos al jugador
    public void setPoints(String namePlayer, int points){
        for (int i = 0; i < player.size(); i++) {
            if (player.get(i).getName().equalsIgnoreCase(namePlayer)){
                player.get(i).setPointsObtained(points+player.get(i).getPointsObtained());
            }
        }
    }
    public void borrrarPersistencia(){
        ServiceJugador serviceJ = new ServiceJugador("", "jugadores.csv");
        try {
            serviceJ.loadDate();
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        try {
            serviceJ.removePlayer();
            serviceJ.dumpData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregraPersistencia(){
        ServiceJugador serviceJ = new ServiceJugador("", "jugadores.csv");
        for (int i = 0; i < player.size(); i++) {
            System.out.println(player.get(i).getName()+"<->"+player.get(i).getPointsObtained());
            serviceJ.addPlayer(player.get(i));
        }
        try {
            serviceJ.loadDate();
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        try {
            serviceJ.dumpData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        meta.setVisible(false);
        Collections.addAll(imagenes = new ArrayList<>(), jugador, jugador1, jugador2, jugador3, jugador4);
        posJug = 0;
        j = null;
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
        seleccionJugador.setText(posJug + "");
    }

    public void resetearTabla(){
       clasificacion.getItems().clear();
        jugP = FXCollections.observableArrayList();
        startingNumber.setCellValueFactory(new PropertyValueFactory("startingNumber"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        location.setCellValueFactory(new PropertyValueFactory("location"));
        pointsObtained.setCellValueFactory(new PropertyValueFactory("pointsObtained"));
        numberMatches.setCellValueFactory(new PropertyValueFactory("numberMatches"));
        tablaJugadores();
        seleccionJugador.setText(posJug + "");
    }

    @FXML
    private Button cerrar;

    @FXML
    void closev(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private ImageView meta;

    // * LO que hace es iniciar a mover los carritos-----------------------------------------------------------------------------------
    @FXML
    void iniciarJuego(ActionEvent event) {
        meta.setVisible(true);
        meta.setX(Integer.parseInt(seleccionJugador.getText()));
        j = new Juego(5, Integer.parseInt(seleccionJugador.getText()));
        for (int i = 0; i < player.size(); i++) {
            Thread t = new Thread(moverImg(i));
            t.start();
            verificarHilo(t);
        }
        j.runn(0, (int) (Math.random() * 10 + 1));
        j.runn(1, (int) (Math.random() * 10 + 1));
        j.runn(2, (int) (Math.random() * 10 + 1));
        j.runn(3, (int) (Math.random() * 10 + 1));
        j.runn(4, (int) (Math.random() * 10 + 1));
        j.hilo();
        asignarPuntajes();
        //resetearTabla();
    }

    // *  para mirar cuando el hilo termine su ejecucion
    @FXML
    private Label ps1, ps2, ps3, ps4, ps5;

    public void verificarHilo(Thread t) {
        Thread th = new Thread(() -> {
            int i = 0;
            do {
                i++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (playing);
            Platform.runLater(() -> {
//             if(j.getPosicion().size()==0){
//                 ps1.setText(j.getPosicion().get(0).getName());
//                 ps1.setTextFill(Color.RED);
//             }else if(j.getPosicion().size()==1){
                ps1.setText(j.getPosicion().get(0).getName());
                ps1.setTextFill(Color.RED);

                ps2.setText(j.getPosicion().get(1).getName());
                ps2.setTextFill(Color.BLUE);

                ps3.setText(j.getPosicion().get(2).getName());
                ps3.setTextFill(Color.BLUE);

                ps4.setText(j.getPosicion().get(3).getName());
                ps4.setTextFill(Color.BLUE);

                ps5.setText(j.getPosicion().get(4).getName());
                ps5.setTextFill(Color.BLUE);
//             }

            });
        });
        th.start();
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

            if (posJug < 960) posJug += 20;
            seleccionJugador.setText(posJug + "");

        } else {

            if (posJug > 0) {
                posJug -= 20;
                seleccionJugador.setText(posJug + "");
            }

        }
    }

    public Runnable moverImg(int j) {
        return () -> {
            while (imagenes.get(j).getX() < Integer.parseInt(seleccionJugador.getText())) {
                //System.out.println("Entro");
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

    public int equivalencia(int i) {
        /*
         * 100 -> 960
         * 1 -> x
         * */
        if (i < 100) {
            int a = (i * 960) / 100;
//        a-=960;
            int b = 960 - a;

            return a + b;
        } else {

            return (i * 960) / 100;
        }
    }


    @Override
    public int compare(Jugador o1, Jugador o2) {
        return 0;
    }
}