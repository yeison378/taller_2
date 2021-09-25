package com.example.taller_2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ServiceJugador {

    public ArrayList<Jugador> jugador;
    private final FilePlane file;

    public ServiceJugador(String path, String name) {
        jugador = new ArrayList<>();
        file = new FilePlane();
        file.setPathFile(path);
        file.setNameFile(name);
    }

    //Este metodo lo que hace es cargar los datos del archivo al arreglo
    public void loadDate() throws IOException, NumberFormatException, NoSuchElementException{
        file.openFile();
        String [] content = file.getContentFile();
        for(String record: content){
            StringTokenizer tokens = new StringTokenizer(record,",");
            while (tokens.hasMoreTokens()){
                try{
                    String name = tokens.nextToken();
                    String location = tokens.nextToken();
                    int startingNumber = Integer.parseInt(tokens.nextToken());
                    int pointsObtained = Integer.parseInt(tokens.nextToken());
                    int numberMatches = Integer.parseInt(tokens.nextToken());
                    jugador.add(new Jugador(startingNumber,name,location,pointsObtained, numberMatches));
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }catch (NoSuchElementException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //Este metodo lo que hace es cargar los datos del archivo al arreglo
    public void dateTabla() throws IOException, NumberFormatException, NoSuchElementException{
        file.openFile();
        String [] content = file.getContentFile();
        for(String record: content){
            StringTokenizer tokens = new StringTokenizer(record,",");
            while (tokens.hasMoreTokens()){
                try{
                    String name = tokens.nextToken();
                    String location = tokens.nextToken();
                    int startingNumber = Integer.parseInt(tokens.nextToken());
                    int pointsObtained = Integer.parseInt(tokens.nextToken());
                    int numberMatches = Integer.parseInt(tokens.nextToken());
                    jugador.add(new Jugador(startingNumber,name,location,pointsObtained, numberMatches));
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }catch (NoSuchElementException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //Encontrar a algun jugador
    public Jugador findPlayer(String name){
      for (Jugador p: jugador){
          if(p.getName().equalsIgnoreCase(name)){
              return p;
          }
      }
      return null;
    }

    //Encontrar un jugador por medio de su nombre
    public int findPlayerName(String number) {
        int cont = 0;
        Iterator var3;
        for (var3 = this.jugador.iterator(); var3.hasNext(); cont++) {
            Jugador j = (Jugador) var3.next();
            if (number.equals(j.getName())) {
                return cont;
            }
        }
        return -1;
    }

    //adicionar
    public boolean addPlayer(Jugador jugadores){
        if (findPlayer(jugadores.getName())==null){
            jugador.add(jugadores);
            return  true;
        }
        return false;
    }

    //Eliminra todos los juegadores
    public void removePlayer(){
        jugador.clear();
    }

    //Agregar datos al archivo plano (jugadores.csv)
    public void dumpData() throws IOException{
        file.openFile();
        String [] dump = new String[jugador.size()];
        int cont =0;
        for (Jugador j: jugador){
            String name = j.getName();
            String location = j.getLocation();
            int startingNumber = j.getStartingNumber();
            int pointsObtained = j.getPointsObtained();
            int numberMatches = j.getNumberMatches();
            dump[cont++] = name+","+location+","+startingNumber+","+pointsObtained+","+numberMatches;
        }
        file.setContentFile(dump);
    }

    //Obtener todos los jugadores
    public ArrayList<Jugador> getJugador(){
        return (ArrayList<Jugador>) jugador.clone();
    }

    //Obtender todos los jugadores
    public String getJugadores(){
        String a ="";
        for (Jugador j:jugador){
            a += j.toString()+"\n";
        }
        return a;
    }
}
