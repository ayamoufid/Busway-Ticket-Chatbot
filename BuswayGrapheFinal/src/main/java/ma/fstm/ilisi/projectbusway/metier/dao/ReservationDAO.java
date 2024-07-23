/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.dao;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ma.fstm.ilisi.projectbusway.metier.bo.*;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

/**
 *
 * @author Aya
 */
public class ReservationDAO 
{
    private static final String URI = "bolt://localhost:7687"; 
    private static final String USERNAME = "neo4j"; 
    private static final String PASSWORD = "12345678";
    private static Driver driver;

    public ReservationDAO() 
    {
        driver = GraphDatabase.driver(URI, AuthTokens.basic(USERNAME, PASSWORD));
    }

    public void create(Reservation reservation) 
    {
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (p:Passager {CIN: $passagerCIN}) MATCH (b:Bus {numeroBus: $busNum}) CREATE (p)-[:A_RESERVE {heureReservation: $heureRes}]->(b)";
            session.run(cypherQuery, Values.parameters
            ("passagerCIN", reservation.getPassager().getCIN(), 
            "busNum", reservation.getVoyage().getBus().getNumeroBus(),
            "heureRes",reservation.getHeureReservation().toString()));
        }
    }

    public void update(Reservation reservation) 
    {
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (p:Passager {CIN: $passagerCIN})-[r:A_RESERVE]->(b:Bus {numeroBus: $busNum}) SET r.heureReservation = $heureReservation";
            session.run(cypherQuery, 
                        Values.parameters("passagerCIN", reservation.getPassager().getCIN(), 
                                          "busNum", reservation.getVoyage().getBus().getNumeroBus(),
                                          "heureReservation", reservation.getHeureReservation().toString()));
        }
    }

    public void deleteReservation(Reservation reservation) 
    {
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (p:Passager {CIN: $passagerCIN})-[r:A_RESERVE]->(b:Bus {numeroBus: $busNum}) DELETE r";
            session.run(cypherQuery, 
                        Values.parameters("passagerCIN", reservation.getPassager().getCIN(), 
                                          "busNum", reservation.getVoyage().getBus().getNumeroBus()));
        }
    }

    
public List<Reservation> retrieveReservations() {
    List<Reservation> reservations = new ArrayList<>();
    Set<Integer> busNumbers = new HashSet<>(); // Utilisé pour stocker les numéros de bus déjà traités
    
    try (Session session = driver.session()) {
        String cypherQuery = "MATCH (p:Passager)-[r:A_RESERVE]->(b:Bus) RETURN p, r, b";
        Result result = session.run(cypherQuery);
        
        while (result.hasNext()) {
            org.neo4j.driver.Record record = result.next();
            String cin1 = record.get("p").get("CIN").asString();
            Passager passager = Passager.PassagerexisteDeja(cin1);
            if (passager == null) {
                passager = new Passager(cin1, record.get("p").get("NomPassager").asString());
            }
            int num = record.get("b").get("numeroBus").asInt();
            int numplace = record.get("b").get("nombrePlacesLimite").asInt();
            Bus bus = Bus.ChercherBusparNumero(num);
           
            
            // Vérifier si le bus a déjà été traité
            if (!busNumbers.contains(num)) {
                busNumbers.add(num); // Ajouter le numéro de bus à l'ensemble
                //bus.setNumeroPlacesLibres(bus.getNumeroPlacesLibres() - 1); // Mettre à jour le nombre de places libres pour le bus
            }
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm"); 
            Time heureReservation = null;
            try {
                java.util.Date parsedDate = dateFormat.parse(record.get("r").get("heureReservation").asString());
                heureReservation = new Time(parsedDate.getTime());
            } catch (ParseException e) {}
            
            Reservation reservation = Reservation.getReservation(cin1, heureReservation);
            if (reservation == null) {
                reservation = new Reservation(heureReservation, passager, null);
                
            }
            reservations.add(reservation);
            System.out.println("reservation *********** " + reservation);
        }
    }
    return reservations;
}

public int getNombreReservationsForBus(int numeroBus) {
    try (Session session = driver.session()) {
        Result result = session.run(
            "MATCH (:Bus {numeroBus: $numeroBus})<-[:A_RESERVE]-(passager:Passager) " +
            "RETURN COUNT(passager) AS nombreReservations",
            Values.parameters("numeroBus", numeroBus)
        );
        if (result.hasNext()) {
            Record record = result.next();
            return record.get("nombreReservations").asInt();
        } else {
            return 0; // Aucun résultat trouvé
        }
    }
}


public static  List<Reservation> getPassagersReserves(int numeroBus) {
    List<Reservation> passagersReserves = new ArrayList<>();

    try (Session session = driver.session()) {
        // Exécution de la requête avec un paramètre
        String query = "MATCH (p:Passager)-[r:A_RESERVE]->(b:Bus {numeroBus: $numeroBus}) RETURN p, r";
        Result result = session.run(query, Values.parameters("numeroBus", numeroBus));

        // Traitement des résultats
        while (result.hasNext()) {
            Record record = result.next();
            Node passagerNode = record.get("p").asNode();
            Relationship aReserveRelationship = record.get("r").asRelationship();

            // Récupération des propriétés du nœud passager
            String cin = passagerNode.get("CIN").asString();
           
            String nomPassager = passagerNode.get("NomPassager").asString();
            System.out.println("cin "+ cin +" nom " +nomPassager);
            System.out.println( Time.valueOf(aReserveRelationship.get("heureReservation").asString()));
            // Récupération des propriétés de la relation A_RESERVE
            Time heureReservation = Time.valueOf(aReserveRelationship.get("heureReservation").asString());
           // int idReservation = aReserveRelationship.get("id").asInt();
            // Création de l'objet PassagerReservation
            //
            Passager p = new Passager(cin, nomPassager);
            Reservation rs  = new Reservation(heureReservation,p);
            passagersReserves.add(rs);
            
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return passagersReserves;
}



}
