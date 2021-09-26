package com.example.taller_2;

import java.io.IOException;
import java.util.ArrayList;

//km de la carrera
//cantidad de jugadores
//Hasta que puntaje se debe llegar
//asignar cantidad de puntos para 1.2 .... 5 puesto
//dados aleatorios del 1-6
//A lo que termine el ultimo jugador comienza la siguiente ronda
public class Juego {
    // ? nplayer: es el numero del jugador con el que vamos a jugar
    // ? level: seria el km de la comtepencia
    private int nPlayer;
    private int level_km;
    private ArrayList<Jugador> posicion;
    private ArrayList<Jugador> jugador;

    public Juego(int nPlayer, int level) {
        this.nPlayer = nPlayer;
        this.level_km = level;
        posicion = new ArrayList<>();
        jugador = cargarJugadores();
    }

    // * En este metodo se cargan los jugadores que estan en el jugadores.csv
    public ArrayList<Jugador> cargarJugadores() {
        ServiceJugador sJugadores = new ServiceJugador("", "jugadores.csv");
        try {
            sJugadores.loadDate();
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        ArrayList<Jugador> j = sJugadores.getJugador();
        return j;
    }

    // * En este metodo el carrito corre
    public boolean correr(int posicion) {
        synchronized (this){
            int dado = (int) (Math.random() * 12 + 1);
            if (jugador.get(posicion).getKm() < level_km) {
                jugador.get(posicion).setKm(dado + jugador.get(posicion).getKm());
                return true;
            } else {
                this.posicion.add(jugador.get(posicion));
                // pruebaPosicion();
                System.out.println("-> " + this.posicion.size());
                return false;
            }
        }
    }

    public void asiganrPuntuacion() {
        //posicion.get(0);
    }

    // ! PRUEBA---------------------------------
    public void pruebaPosicion() {
        int i = 1;
        for (Jugador j : posicion) {
            System.out.println(i + " - " + j.getName());
            i++;
        }
        System.out.println("_____________________________________________");
    }

    /*public void run(int posicion) {
        runn(posicion);
    }*/

    private static int aux = 0;

    public void runn(int posicion, int tiempo) {
        Thread t = new Thread(() -> {
            //synchronized (this) {
                boolean a = false;
                do {
                    a = correr(posicion);
                    try {
                        Thread.sleep(tiempo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (a);
                aux++;
           //}
        });
        t.start();
    }

    public void hilo() {
        Thread t = new Thread(() -> {
            while (posicion.size() != 5) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pruebaPosicion();
        });
        t.start();
    }

    public int getnPlayer() {
        return nPlayer;
    }

    public void setnPlayer(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    public int getLevel_km() {
        return level_km;
    }

    public void setLevel_km(int level_km) {
        this.level_km = level_km;
    }

    public ArrayList<Jugador> getPosicion() {
        return posicion;
    }

    public void setPosicion(ArrayList<Jugador> posicion) {
        this.posicion = posicion;
    }

    public ArrayList<Jugador> getJugador() {
        return jugador;
    }

    public void setJugador(ArrayList<Jugador> jugador) {
        this.jugador = jugador;
    }


}
