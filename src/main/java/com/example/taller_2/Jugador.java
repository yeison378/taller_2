package com.example.taller_2;

public class Jugador {
    //startingNumber: es el puesto en todo el juego
    //pointsObtained: son los puntos obtenidos en todo el juego
    //numberMatches: numero de partidos en todo el juego
    private int startingNumber;
    private String name;
    private String location;
    private int pointsObtained;
    private int numberMatches;
    private int km;
    private int puntoCarrera;

    public Jugador(int startingNumber, String name, String location, int pointsObtained, int numberMatches) {
        this.name = name;
        this.location = location;
        this.startingNumber = startingNumber;
        this.pointsObtained = pointsObtained;
        this.numberMatches = numberMatches;
        km=0;
        puntoCarrera =0;
    }

    public int compareTo(Jugador o2) {
        if(getPointsObtained()>o2.getPointsObtained()){
            return -1;
        }else if(getPointsObtained()>o2.getPointsObtained()){
            return 0;
        }else{
            return 1;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
// *
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStartingNumber() {
        return startingNumber;
    }

    public void setStartingNumber(int startingNumber) {
        this.startingNumber = startingNumber;
    }

    public int getPointsObtained() {
        return pointsObtained;
    }

    public void setPointsObtained(int pointsObtained) {
        this.pointsObtained = pointsObtained;
    }

    public int getNumberMatches() {
        return numberMatches;
    }

    public void setNumberMatches(int numberMatches) {this.numberMatches = numberMatches;}

    public void setNumberMatches() {
        this.numberMatches ++;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getPuntoCarrera() {
        return puntoCarrera;
    }

    public void setPuntoCarrera(int puntoCarrera) {
        this.puntoCarrera = puntoCarrera;
    }

    public void ver(){
        System.out.println("Nombre "+name+ " KM "+km);
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "startingNumber=" + startingNumber +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", pointsObtained=" + pointsObtained +
                ", numberMatches=" + numberMatches +
                ", km=" + km +
                '}';
    }
}
