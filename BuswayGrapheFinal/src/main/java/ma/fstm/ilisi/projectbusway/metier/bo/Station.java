/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.bo;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Aya
 */
public class Station 
{
    private int idStation;
    private String nomStation;
    private List<Voyage> voyagesDep = new ArrayList();
    private List<Voyage> voyagesArr = new ArrayList();
    private List<Reservation> reservationFrom = new ArrayList();
    private List<Reservation> reservationTo = new ArrayList();
    private double lat; // Latitude of the station
    private double lon; // Longitude of the station

    private static List<Station> toutesLesStations = new ArrayList();
    public Station() {
        toutesLesStations.add(this);
    }

    public Station(int idStation, String nomStation) {
        this.idStation = idStation;
        this.nomStation = nomStation;
        toutesLesStations.add(this);
    }

    
    
    public Station(int idStation, String nomStation, double lat, double lon) {
        this.idStation = idStation;
        this.nomStation = nomStation;
        this.lat = lat;
        this.lon = lon;
        toutesLesStations.add(this);
    }
    public Station(int idStation, String nomStation, List<Voyage> oyagesDep, List<Voyage> voyagesArr, List<Reservation> reservationFrom, List<Reservation> reservationTo) {
        this.idStation = idStation;
        this.nomStation = nomStation;
        this.voyagesDep = oyagesDep;
        this.voyagesArr = voyagesArr;
        this.reservationFrom = reservationFrom;
        this.reservationTo = reservationTo;
        toutesLesStations.add(this);
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
    
    public int getIdStation() {
        return idStation;
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }

    public String getNomStation() {
        return nomStation;
    }

    public void setNomStation(String nomStation) {
        this.nomStation = nomStation;
    }

    public List<Voyage> getOyagesDep() {
        return voyagesDep;
    }

    public void setOyagesDep(List<Voyage> oyagesDep) {
        this.voyagesDep = oyagesDep;
    }

    public List<Voyage> getVoyagesArr() {
        return voyagesArr;
    }

    public void setVoyagesArr(List<Voyage> voyagesArr) {
        this.voyagesArr = voyagesArr;
    }

    public List<Reservation> getReservationFrom() {
        return reservationFrom;
    }

    public void setReservationFrom(List<Reservation> reservationFrom) {
        this.reservationFrom = reservationFrom;
    }

    public List<Reservation> getReservationTo() {
        return reservationTo;
    }

    public void setReservationTo(List<Reservation> reservationTo) {
        this.reservationTo = reservationTo;
    }

    @Override
    public String toString() {
        return "Station{" + "idStation=" + idStation + ", nomStation=" + nomStation + ", voyagesDep=" + voyagesDep + ", voyagesArr=" + voyagesArr + ", reservationFrom=" + reservationFrom + ", reservationTo=" + reservationTo + ", lat=" + lat + ", lon=" + lon + '}';
    }
    
    public static Station getStation(int idS) 
    {
        for (Station station : toutesLesStations) 
            if (station.getIdStation() == idS) 
                return station;
        return null; 
    }


    
    public static final double VITESSE_MOYENNE_BUS_URBAIN_KM_PAR_HEURE = 20.0;
    public double calculateDistance(Station otherStation) {
        double latDifference = this.lat - otherStation.lat;
        double lonDifference = this.lon - otherStation.lon;
        
        // Calculate the Euclidean distance
        double distance = Math.sqrt(latDifference * latDifference + lonDifference * lonDifference);
        
        return distance;
    }
 // Convertir la distance de degrés en mètres
    public static double distanceDegreesToKilometers(double distanceDegrees) {
        double earthRadiusKm = 6371.0; // Rayon moyen de la Terre en kilomètres
        double distanceRadians = Math.toRadians(distanceDegrees);
        return earthRadiusKm * distanceRadians;
    }

//    public static void main(String[] args) {
//        // Créez une liste de stations
//        List<Station> stations = new ArrayList<>();
//        
//        // Ajoutez des stations à la liste
//        stations.add(new Station(1,"a" ,40.7128, -74.0060)); // Exemple de New York
//        stations.add(new Station(2,"b" ,34.0522, -118.2437)); // Exemple de Los Angeles
//        stations.add(new Station(3,"c" ,51.5074, -0.1278)); // Exemple de Londres
//        
//        // Parcourez la liste de stations à partir de la deuxième station
//        for (int i = 1; i < stations.size(); i++) {
//            Station currentStation = stations.get(i);
//            Station previousStation = stations.get(i - 1);
//            
//            // Calculez la distance entre la station actuelle et la station précédente
//            double distanceDegrees = currentStation.calculateDistance(previousStation);
//            
//            // Convertir la distance de degrés en mètres
//            double distanceMeters = distanceDegreesToKilometers(distanceDegrees);
//            
//            // Calculez le temps estimé pour voyager de la station précédente à la station actuelle
//            double tempsEstimeHours = distanceMeters / (VITESSE_MOYENNE_BUS_URBAIN_KM_PAR_HEURE * 1000); // Convertir en heures
//            double tempsEstimeMinutes = tempsEstimeHours * 60; // Convertir en minutes
//            
//            // Affichez la distance et le temps estimé
//            System.out.println("La distance entre la station " + currentStation.getIdStation() + " et la station " +
//                    previousStation.getIdStation() + " est : " + distanceMeters + " mètres.");
//            System.out.println("Le temps estimé pour voyager entre ces deux stations est : " + tempsEstimeMinutes + " minutes.");
//        }
//    }

   
}