package com.example.taller_2;

public class Jugador {
    private int numero;
    private String nombre;
    private String ubicacion;
    private int puntos;
    private int partidas;

    public Jugador(int numero, String nombre, String ubicacion, int puntos, int partidas) {
        this.numero = numero;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.puntos = puntos;
        this.partidas = partidas;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPartidas() {
        return partidas;
    }

    public void setPartidas(int partidas) {
        this.partidas = partidas;
    }
}
