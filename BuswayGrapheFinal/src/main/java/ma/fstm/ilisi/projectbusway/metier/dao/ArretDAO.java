/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.dao;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import ma.fstm.ilisi.projectbusway.metier.bo.Arret;
import ma.fstm.ilisi.projectbusway.metier.bo.Bus;
import ma.fstm.ilisi.projectbusway.metier.bo.Station;
import ma.fstm.ilisi.projectbusway.metier.bo.Voyage;

/**
 *
 * @author Aya
 */
public class ArretDAO 
{
    private static final String URI = "bolt://localhost:7687";
    private static final String USERNAME = "neo4j";
    private static final String PASSWORD = "12345678";
    private final Driver driver;

    public ArretDAO() 
    {
        driver = GraphDatabase.driver(URI, AuthTokens.basic(USERNAME, PASSWORD));
    }

    public void create(Arret arret) 
    {
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (bus:Bus {numeroBus: $num}), (station:Station {idStation: $stationId}) CREATE (bus)-[:ARRETER {heureArret: $heureArret}]->(station)";
            session.run(cypherQuery, 
                          Values.parameters("num", arret.getVoyage().getBus().getNumeroBus(),
                                            "stationId", arret.getStation().getIdStation(),
                                            "heureArret", arret.getHeureArret().toString()));
        }
}
    
    
//    public List<Arret> retrieveparStation(Station station) 
//    {
//        List<Arret> arrets = new ArrayList<>();
//        try (Session session = driver.session()) 
//        {
//            String cypherQuery = "MATCH (bus:Bus)-[arret:Arreter]->(station:Station {idStation: $stationId}) RETURN arret, bus";
//            Result result = session.run(cypherQuery, Values.parameters("stationId", station.getIdStation()));
//            while (result.hasNext()) 
//            {
//                org.neo4j.driver.Record record = result.next();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm"); 
//                Time heureArret = null;
//                try 
//                {
//                    java.util.Date parsedDate = dateFormat.parse(record.get("arret").get("heureArret").asString());
//                    heureArret = new Time(parsedDate.getTime());
//                } 
//                catch (ParseException e) {}
//                
//                int numBus = record.get("bus").get("numeroBus").asInt();
//                Bus busart = Bus.ChercherBusparNumero(numBus);
//                
//                Arret arret = new Arret(heureArret , busart.getLesVoyages().get(0) , station);
//                System.out.println("Arret************ "+arret);
//                arrets.add(arret);
//            }
//        }
//        return arrets;
//    }
    
    
    public int departStation(int numeroBus) {
        try (Session session = driver.session()) {
            Result result = session.run(
                "MATCH (bus:Bus {numeroBus: $numeroBus})-[:Arreter]->(stationDepart:Station)\n" +
                "WHERE NOT EXISTS {\n" +
                "    MATCH (bus)-[:Arreter]->(autreStation:Station)-[:SUIT]->(stationDepart)\n" +
                "    RETURN autreStation\n" +
                "}\n" +
                "RETURN stationDepart.idStation AS idStationDepart\n" +
                "LIMIT 1",
                Values.parameters("numeroBus", numeroBus));

            if (result.hasNext()) {
                Record record = result.next();
                System.out.print("depart station "+record.get("idStationDepart").asInt());
                return record.get("idStationDepart").asInt();
            } else {
                // Aucun résultat trouvé
                return -1; // ou tout autre code d'erreur approprié
            }
        }
    }
    
    
    //donner ordre de station
    public int OrdreStation(int numeroBus, int idStation) {
        try (Session session = driver.session()) {
            Result result = session.run(
                "MATCH (bus:Bus {numeroBus: $numeroBus})-[:Arreter]->(station1:Station {idStation: $idStation})\n" +
                "MATCH (bus)-[:Arreter]->(station2:Station)\n" +
                "WHERE station1 <> station2\n" +
                "MATCH path = (station1)-[:SUIT*]->(station2)\n" +
                "RETURN COUNT(path) AS nombreRelationsSUIT",
                Values.parameters("numeroBus", numeroBus, "idStation", idStation));

            if (result.hasNext()) {
                Record record = result.next();
                return record.get("nombreRelationsSUIT").asInt() ;
            } else {
                // Aucun résultat trouvé
                return 0;
            }
        }
    }
    
    
    
    
  //this function what i need  
    public List<Arret> retrieve() 
    {
    	
        List<Arret> arrets = new ArrayList<>();
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (bus:Bus)-[arret:Arreter]->(station:Station ) RETURN arret,station,bus";
            Result result = session.run(cypherQuery);
            while (result.hasNext()) 
            {
                org.neo4j.driver.Record record = result.next();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm"); 
                Time heureArret = null;
                try 
                {
                    java.util.Date parsedDate = dateFormat.parse(record.get("arret").get("heureArret").asString());
                    heureArret = new Time(parsedDate.getTime());
                } 
                catch (ParseException e) {}
                
                
                int idStation = record.get("station").get("idStation").asInt();
                Station arrt = Station.getStation(idStation);
                
                int numBus = record.get("bus").get("numeroBus").asInt();
                System.out.println("------------------------numeroBus " + numBus);
                int nombre = record.get("bus").get("nombrePlacesLimite").asInt();
                Bus busart = Bus.ChercherBusparNumero(numBus);
                
                int num1 = record.get("arret").get("nombreAscendents").asInt();
                int num2 = record.get("arret").get("nombreDescendents").asInt();
                int num = busart.getNumeroPlacesLibres() - num1 + num2;
               // busart.setNumeroPlacesLibres(num);
                System.out.println("------------------------numeroplaceslibre " + num);
                num = 0;
                
                Arret arret = new Arret(heureArret , busart.getLesVoyages().get(0) , arrt);
                int ordre = Math.abs( OrdreStation(numBus,arret.getStation().getIdStation()) -OrdreStation(numBus,departStation(numBus))) +1;
                arret.setOrdre(ordre);
                System.out.println("here : arret heure : " +heureArret + "arret heure : "+"numbus "+numBus+" id station"+arret.getStation().getIdStation()+"station dep "+departStation(numBus)+" ordre  "+ordre);
                System.out.println("Arret************ "+arret);
                arrets.add(arret);
            }
        }
        
        Collections.sort(arrets, new ArretComparator());
//        for(Arret a:arrets)
//        {
//        	System.out.println("num bus: " +a.getHeureArret());
//        }
        System.out.println("Affichage start:");
        afficherStationsAvecPrecedent(arrets);
        System.out.println("apres trie heure:");
//        for(Arret a:arrets)
        
//        {
//        	System.out.println("id station " +a.getStation().getIdStation()+" heure "+a.getHeureArret());
//        }
        return arrets;
        
    }

    
    public void afficherStationsAvecPrecedent(List<Arret> arrets) {
        // Map pour stocker le station précédent pour chaque bus
        Map<Integer, Integer> precedents = new HashMap<>();

        // Parcours des arrêts
        Time heurarret = null;
        for (Arret arret : arrets) {
            int numeroBus = arret.getVoyage().getBus().getIdBus();
            int idStation = arret.getStation().getIdStation();
            int ordre = arret.getOrdre();

            // Récupération du station précédent pour ce bus
            Integer precedent = precedents.get(numeroBus);

            // Affichage du arrêt actuel
            System.out.println("num bus: " + numeroBus + " id station: " + idStation + " ordre: " + ordre);

            // Si on a un station précédent, l'afficher
            if (precedent != null) {
                System.out.println("precedent : " + precedent);
                
                
              Station currentStation =getStationInfo(idStation);
              Station previousStation =getStationInfo(precedent);
              double distanceDegrees = currentStation.calculateDistance(previousStation);
              double distanceMeters = Station.distanceDegreesToKilometers(distanceDegrees);
              double tempsEstimeHours = distanceMeters / (Station.VITESSE_MOYENNE_BUS_URBAIN_KM_PAR_HEURE * 10); // Convertir en heures
              double tempsEstimeMinutes = tempsEstimeHours * 60; // Convertir en minutes
              System.out.println("Le temps : " + tempsEstimeMinutes + " minutes.");
              heurarret = Arret.addMinutes(tempsEstimeMinutes,heurarret);
              System.out.println("heur arret est "+heurarret);  
              arret.setHeureArret(heurarret);
              arret.getVoyage().setDateArrivee(heurarret);
             // arret.setStation(getStationById(idStation));
              arret.getVoyage().setlArrivee((getStationById(idStation)));
              
              
            }
            else {
            	  Random random = new Random();
                  int nombreAleatoire = random.nextInt(21) + 10;
                  Time dep = Voyage.genrerHeure1(nombreAleatoire);
                  
                  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                  arret.setHeureArret(dep);
                  //arret.setStation();
                  arret.getVoyage().setDateDepart(dep);
                 arret.getVoyage().setLeDepart(getStationById(idStation));
                  heurarret=dep;
                  System.out.println("date depart :"+dep+"station dep "+idStation);
            }
            
            // Mettre à jour le station précédent pour ce bus
            precedents.put(numeroBus, idStation);
        }
        
        
    }

    

    public Station getStationById(int id) {
        try (Session session = driver.session()) {
            Result result = session.run(
                "MATCH (s:Station {idStation: $id}) " +
                "RETURN s.latitude AS latitude, s.longitude AS longitude, s.nomStation AS nomStation",
                Values.parameters("id", id)
            );
            if (result.hasNext()) {
                Record record = result.next();
                double latitude = record.get("latitude").asDouble();
                double longitude = record.get("longitude").asDouble();
                String nomStation = record.get("nomStation").asString();
                
                // Créer et retourner un objet Station avec les informations récupérées
                return new Station(id, nomStation,latitude, longitude);
            } else {
                return null; // Aucune station trouvée avec cet ID
            }
        }
    }

    public void update(Arret oldArret, Arret newArret) 
    {
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (bus:Bus {numeroBus: $num})-[r:ARRETER]->(oldStation:Station {idStation: $oldStationId}) DELETE r CREATE (bus)-[:ARRETER {heureArret: $newHeureArret}]->(newStation:Station {idStation: $newStationId})";
            session.run(cypherQuery,
                          Values.parameters("num", oldArret.getVoyage().getBus().getNumeroBus(),
                                            "oldStationId", oldArret.getStation().getIdStation(),
                                            "newHeureArret", newArret.getHeureArret().toString(),
                                            "newStationId", newArret.getStation().getIdStation()));
        }
    }

    
    
    public void deleteArret(Arret arret) 
    {
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (bus:Bus {numeroBus: $num})-[r:ARRETER]->(station:Station {idStation: $stationId}) DELETE r";
            session.run(cypherQuery,
                          Values.parameters("num", arret.getVoyage().getBus().getNumeroBus(),
                                            "stationId", arret.getStation().getIdStation()));
        }
    }
    
    
//    public void modifierHeureArret(int numeroBus, int idStation, String nouvelleHeureArret) {
//        try (Session session = driver.session()) {
//            String cypherQuery = "MATCH (bus:Bus {numeroBus: $numeroBus})-[r:Arreter]->(station:Station {idStation: $idStation}) " +
//                                 "SET r.heureArret = $nouvelleHeureArret";
//            session.run(cypherQuery, Values.parameters(
//                    "numeroBus", numeroBus,
//                    "idStation", idStation,
//                    "nouvelleHeureArret", nouvelleHeureArret));
//        }
//    }
 
    
    // Fonction pour récupérer les informations de la station à partir de Neo4j
    public Station getStationInfo(int idStation) {
        try (Session session = driver.session()) {
            // Exécution de la requête Cypher pour récupérer les informations de la station
            Result result = session.run("MATCH (s:Station {idStation: $id}) RETURN s.latitude AS latitude, s.longitude AS longitude, s.nomStation AS nomStation",
                                        Values.parameters("id", idStation));

            // Traitement du résultat de la requête
            if (result.hasNext()) {
                Record record = result.next();

                // Récupération des données du résultat
                double latitude = record.get("latitude").asDouble();
                double longitude = record.get("longitude").asDouble();
                String nomStation = record.get("nomStation").asString();

                // Création et retour de l'instance de Station avec les données récupérées
                return new Station(idStation, nomStation,latitude, longitude);
            } else {
                // Aucune station trouvée avec l'idStation donné
                return null;
            }
        }
    }
    
   
    public void retrieveStationIdsForBus(int numeroBus) {
        try (Session session = driver.session()) {
            String cypherQuery = "MATCH (bus:Bus {numeroBus: $numeroBus})-[:Arreter]->(startStation)-[:SUIT*]->(nextStation)\n" +
                                 "RETURN startStation.idStation AS startId, COLLECT(nextStation.idStation) AS stationIds\n" +
                                 "LIMIT 1";
            Value parameters = Values.parameters("numeroBus", numeroBus);
            Result result = session.run(cypherQuery, parameters);
        }
    }

    public void close() 
    {
        driver.close();
    }
}


class ArretComparator implements Comparator<Arret> {
    @Override
    public int compare(Arret a1, Arret a2) {
        // Compare les numéros de bus
        int compareNumBus = Integer.compare(a1.getVoyage().getBus().getIdBus(), a2.getVoyage().getBus().getIdBus());
        
        // Si les numéros de bus sont égaux, compare les ordres
        if (compareNumBus == 0) {
            return Integer.compare(a1.getOrdre(), a2.getOrdre());
        }
        
        return compareNumBus;
    }
}

