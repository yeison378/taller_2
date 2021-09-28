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
    public void asignarPuntajes() {
        Thread th = new Thread(() -> {
            while (j.getPosicion().size() != 5) {
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
            for (Jugador a : j.getPosicion()) {
                setPoints(a.getName(), a.getPuntoCarrera());
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
    public void ordenarJugaodres() {
        player.sort((o1, o2) -> o1.compareTo(o2));
        for (int i = 0; i < player.size(); i++) {
            player.get(i).setStartingNumber(i + 1);
        }
    }

    // * lo que hace este metodo es sumarle los puntos nuevos al jugador
    public void setPoints(String namePlayer, int points) {
        for (int i = 0; i < player.size(); i++) {
            if (player.get(i).getName().equalsIgnoreCase(namePlayer)) {
                player.get(i).setPointsObtained(points + player.get(i).getPointsObtained());
            }
        }
    }

    public void borrrarPersistencia() {
        ServiceJugador serviceJ = new ServiceJugador("", "jugadores.csv");
        try {
            serviceJ.loadDate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serviceJ.removePlayer();
            serviceJ.dumpData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void agregraPersistencia() {
        ServiceJugador serviceJ = new ServiceJugador("", "jugadores.csv");
        for (int i = 0; i < player.size(); i++) {
            serviceJ.addPlayer(player.get(i));
        }
        try {
            serviceJ.loadDate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            serviceJ.dumpData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private ImageView fiesta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fiesta.setVisible(false);
        reiniciarbtn.setVisible(false);
        meta.setVisible(false);
        Collections.addAll(imagenes = new ArrayList<>(), jugador, jugador1, jugador2, jugador3, jugador4);
        posJug = 50;
        j = null;
        player = new ArrayList<>();
        ServiceJugador serviceJ = new ServiceJugador("", "jugadores.csv");
        try {
            serviceJ.loadDate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = serviceJ.getJugador();
        try {
            serviceJ.dumpData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sort((o1, o2) -> o1.compareTo(o2));
        postInicial();
        jugP = FXCollections.observableArrayList();
        startingNumber.setCellValueFactory(new PropertyValueFactory("startingNumber"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        location.setCellValueFactory(new PropertyValueFactory("location"));
        pointsObtained.setCellValueFactory(new PropertyValueFactory("pointsObtained"));
        numberMatches.setCellValueFactory(new PropertyValueFactory("numberMatches"));
        tablaJugadores();
        // ! habilitar metodo al terminar -------------------------------------------------------------
        seleccionJugador.setText(posJug + "");
        horas();
    }

    @FXML
    private Label horaCol,horaArg,horaFran,horaVen;

    public void horas() {
        Thread hr = new Thread(() -> {
            do {
                Platform.runLater(() -> {
                    horaCol.setText(horaHilo(0));
                    horaArg.setText(horaHilo(2));
                    horaFran.setText(horaHilo(7));
                    horaVen.setText(horaHilo(1));
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);

        });
        hr.start();
    }

    public String horaHilo(int hGtr) {
        String hora = "", minutos = "", segundos = "";
        Calendar calendar = new GregorianCalendar();
        Date fechaHora = new Date();
        calendar.setTime(fechaHora);
        String amPm = calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";

        if (amPm.equalsIgnoreCase("PM")) {
            int h = calendar.get(Calendar.HOUR_OF_DAY) ;
            hora = h > 9 ? "" + h : "0" + h;
        } else {
            hora = calendar.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendar.get(Calendar.HOUR_OF_DAY) : "0" + calendar.get(Calendar.HOUR_OF_DAY);
        }
        minutos = calendar.get(Calendar.MINUTE) > 9 ? "" + calendar.get(Calendar.MINUTE) : "0" + calendar.get(Calendar.MINUTE);
        segundos = calendar.get(Calendar.SECOND) > 9 ? "" + calendar.get(Calendar.SECOND) : "0" + calendar.get(Calendar.SECOND);

        return formato(Integer.parseInt(hora),hGtr) + " : " + minutos + " : " + segundos + " " ;

    }

    public String formato(int actual, int aumento) {

        if ((actual + aumento) > 24 ) return (actual + aumento) - 24 + "";
        else return (actual + aumento)+"";
    }


    public void postInicial() {
        for (int i = 0; i < player.size(); i++) {
            player.get(i).setStartingNumber(i + 1);
        }
    }

    public void partidas() {
        for (Jugador a : player) {
            a.setNumberMatches();
        }
    }

    public void resetearTabla() {
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
    private Button jugarbtn, reiniciarbtn;

    @FXML
    void iniciarJuego(ActionEvent event) {
        partidas();
        seleccionJugador.setDisable(true);
        atrasJugador.setDisable(true);
        adelanteJUgador.setDisable(true);

        meta.setVisible(true);
        meta.setX(Integer.parseInt(seleccionJugador.getText()));
        j = new Juego(5, Integer.parseInt(seleccionJugador.getText()));
        for (int i = 0; i < player.size(); i++) {
            Thread t = new Thread(moverImg(i));
            t.start();
//            verificarHilo(t);
            tablaPuntuacion();
        }
        int rand =(int) (Math.random() * 100 + 50);
//        j.runn(0, (int) (Math.random() * 50 + 10));
//        j.runn(1, (int) (Math.random() * 50 + 10));
//        j.runn(2, (int) (Math.random() * 50 + 10));
//        j.runn(3, (int) (Math.random() * 50 + 10));
//        j.runn(4, (int) (Math.random() * 50 + 10));

        j.runn(0, rand);
        j.runn(1, rand);
        j.runn(2, rand);
        j.runn(3, rand);
        j.runn(4, rand);
        j.hilo();
        asignarPuntajes();
        jugarbtn.setDisable(true);
        jugarbtn.setVisible(false);
        reiniciarbtn.setVisible(true);
        reiniciarbtn.setDisable(true);
        Thread im = new Thread(marcasImg());
        im.start();

    }


    @FXML
    void reiniciarJuego(ActionEvent event) {
        fiesta.setVisible(false);
        seleccionJugador.setText("50");
        posJug = 50;
        ps1.setText("-----------");
        ps2.setText("-----------");
        ps3.setText("-----------");
        ps4.setText("-----------");
        ps5.setText("-----------");

        for (ImageView a : imagenes) {
            a.setX(14);
        }

        reiniciarbtn.setVisible(false);
        jugarbtn.setVisible(true);
        jugarbtn.setDisable(false);
        meta.setVisible(false);

        seleccionJugador.setDisable(false);
        atrasJugador.setDisable(false);
        adelanteJUgador.setDisable(false);
        resetPositionLabel();


    }

    // *  para mirar cuando el hilo termine su ejecucion
    @FXML
    private Label ps1, ps2, ps3, ps4, ps5;

    public void resetPositionLabel() {
        kmActual.setText("0");
        kmActual1.setText("0");
        kmActual2.setText("0");
        kmActual3.setText("0");
        kmActual4.setText("0");

        kmActual.setLayoutX(25);
        kmActual1.setLayoutX(25);
        kmActual2.setLayoutX(25);
        kmActual3.setLayoutX(25);
        kmActual4.setLayoutX(25);
    }
// * se añden los marcadores a la tabla de posiciones
    public void tablaPuntuacion() {

        Thread th = new Thread(() -> {

            do {
                Platform.runLater(() -> {
                    if (j.getPosicion().size() == 1) {

                        ps1.setText(j.getPosicion().get(0).getName() + " PTS. 50+");
                        ps1.setTextFill(Color.YELLOW);

                        fiesta.setVisible(true);
                        fiesta.setX(Integer.parseInt(seleccionJugador.getText()));  fiesta.setVisible(true);


                    } else if (j.getPosicion().size() == 2) {

                        fiesta.setVisible(true);
                        fiesta.setX(Integer.parseInt(seleccionJugador.getText()));  fiesta.setVisible(true);

                        ps1.setText(j.getPosicion().get(0).getName() + " PTS. 50+");
                        ps1.setTextFill(Color.YELLOW);

                        ps2.setText(j.getPosicion().get(1).getName() + " PTS. 40+");
                        ps2.setTextFill(Color.SILVER);

                    } else if (j.getPosicion().size() == 3) {

                        fiesta.setVisible(true);
                        fiesta.setX(Integer.parseInt(seleccionJugador.getText()));  fiesta.setVisible(true);

                        ps1.setText(j.getPosicion().get(0).getName() + " PTS. 50+");
                        ps1.setTextFill(Color.YELLOW);

                        ps2.setText(j.getPosicion().get(1).getName() + " PTS. 40+");
                        ps2.setTextFill(Color.SILVER);

                        ps3.setText(j.getPosicion().get(2).getName() + " PTS. 30+");
                        ps3.setTextFill(Color.GOLDENROD);

                    } else if (j.getPosicion().size() == 4) {
                        fiesta.setVisible(true);
                        fiesta.setX(Integer.parseInt(seleccionJugador.getText()));  fiesta.setVisible(true);

                        ps1.setText(j.getPosicion().get(0).getName() + " PTS. 50+");
                        ps1.setTextFill(Color.YELLOW);

                        ps2.setText(j.getPosicion().get(1).getName() + " PTS. 40+");
                        ps2.setTextFill(Color.SILVER);

                        ps3.setText(j.getPosicion().get(2).getName() + " PTS. 30+");
                        ps3.setTextFill(Color.GOLDENROD);

                        ps4.setText(j.getPosicion().get(3).getName() + " PTS. 20+");
                        ps4.setTextFill(Color.ALICEBLUE);


                    } else if (j.getPosicion().size() == 5) {
                        ps1.setText(j.getPosicion().get(0).getName() + " PTS. 50+");
                        ps1.setTextFill(Color.YELLOW);

                        ps2.setText(j.getPosicion().get(1).getName() + " PTS. 40+");
                        ps2.setTextFill(Color.SILVER);

                        ps3.setText(j.getPosicion().get(2).getName() + " PTS. 30+");
                        ps3.setTextFill(Color.GOLDENROD);

                        ps4.setText(j.getPosicion().get(3).getName() + " PTS. 20+");
                        ps4.setTextFill(Color.ALICEBLUE);

                        ps5.setText(j.getPosicion().get(4).getName() + " PTS. 10+");
                        ps5.setTextFill(Color.ALICEBLUE);
                    }
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (j.getPosicion().size() < 5 || ps5.getText().equalsIgnoreCase("-----------"));
            reiniciarbtn.setDisable(false);
            Platform.runLater(() -> {
//             if(j.getPosicion().size()==0){
//                 ps1.setText(j.getPosicion().get(0).getName());
//                 ps1.setTextFill(Color.RED);
//             }else if(j.getPosicion().size()==1){


            /*    ps2.setText(j.getPosicion().get(1).getName());
                ps2.setTextFill(Color.BLUE);

                ps3.setText(j.getPosicion().get(2).getName());
                ps3.setTextFill(Color.BLUE);

                ps4.setText(j.getPosicion().get(3).getName());
                ps4.setTextFill(Color.BLUE);

                ps5.setText(j.getPosicion().get(4).getName());
                ps5.setTextFill(Color.BLUE);*/
//             }

            });
        });
        th.start();
    }

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

            if (posJug < 1000) {
                posJug += 50;
                seleccionJugador.setText(posJug + "");
            } else {
                seleccionJugador.setText("50");
                posJug = 50;
            }


        } else {

            if (posJug > 0) {
                posJug -= 50;
                seleccionJugador.setText(posJug + "");
                if (posJug == 0) {
                    seleccionJugador.setText("1000");
                    posJug = 1000;
                }
            } else {
                seleccionJugador.setText("1000");
                posJug = 1000;
            }

        }
    }

    public Runnable moverImg(int j) {
        return () -> {
            while (imagenes.get(j).getX() < Integer.parseInt(seleccionJugador.getText())) {
                Platform.runLater(() -> {
                    imagenes.get(j).setX(this.j.getJugador().get(j).getKm());
//                    kmActual.setLayoutX(jugador.getX());
//                    kmActual.setText(jugador.getLayoutX()+"");
                });
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @FXML
    private Label kmActual, kmActual1, kmActual2, kmActual3, kmActual4;

    // * mover kilometraje
    public Runnable marcasImg() {
        return () -> {
            int i = 0;
            while (jugador.getX() < j.getLevel_km() || jugador1.getX() < j.getLevel_km() || jugador2.getX() < j.getLevel_km() || jugador3.getX() < j.getLevel_km() || jugador4.getX() < j.getLevel_km()) {
                Platform.runLater(() -> {

                    kmActual.setLayoutX(jugador.getX());
                    String aa = (int) jugador.getX() + "";
                    kmActual.setText(aa);

                    kmActual1.setLayoutX(jugador1.getX());
                    String aa1 = (int) jugador1.getX() + "";
                    kmActual1.setText(aa1);

                    kmActual2.setLayoutX(jugador2.getX());
                    String aa2 = (int) jugador2.getX() + "";
                    kmActual2.setText(aa2);

                    kmActual3.setLayoutX(jugador3.getX());
                    String aa3 = (int) jugador3.getX() + "";
                    kmActual3.setText(aa3);

                    kmActual4.setLayoutX(jugador4.getX());
                    String aa4 = (int) jugador4.getX() + "";
                    kmActual4.setText(aa4);


                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> {

                kmActual.setLayoutX(jugador.getX());
                String aa = (int) jugador.getX() + "";
                kmActual.setText(aa);

                kmActual1.setLayoutX(jugador1.getX());
                String aa1 = (int) jugador1.getX() + "";
                kmActual1.setText(aa1);

                kmActual2.setLayoutX(jugador2.getX());
                String aa2 = (int) jugador2.getX() + "";
                kmActual2.setText(aa2);

                kmActual3.setLayoutX(jugador3.getX());
                String aa3 = (int) jugador3.getX() + "";
                kmActual3.setText(aa3);

                kmActual4.setLayoutX(jugador4.getX());
                String aa4 = (int) jugador4.getX() + "";
                kmActual4.setText(aa4);


            });
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