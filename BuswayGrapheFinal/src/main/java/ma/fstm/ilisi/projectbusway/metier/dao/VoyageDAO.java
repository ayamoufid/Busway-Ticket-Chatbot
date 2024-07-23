/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.dao;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import ma.fstm.ilisi.projectbusway.metier.bo.*;
import org.neo4j.driver.*;

/**
 *
 * @author Aya
 */
public class VoyageDAO {
    private static final String URI = "bolt://localhost:7687";
    private static final String USERNAME = "neo4j";
    private static final String PASSWORD = "12345678";
    private final Driver driver;

    public VoyageDAO() {
        driver = GraphDatabase.driver(URI, AuthTokens.basic(USERNAME, PASSWORD));
    }
    
    public Time getHeureArret(int numeroBus, int idStation) 
    {
    	
    try (Session session = driver.session()) 
    {
        String query = "MATCH (b:Bus {numeroBus: $numeroBus})-[arreter:Arreter]->(s:Station {idStation: $idStation}) " +
                       "RETURN arreter.heureArret AS heureArret";
        Result result = session.run(query, Values.parameters("numeroBus", numeroBus, "idStation", idStation));
        if (result.hasNext()) 
        {
            org.neo4j.driver.Record record = result.next();
            String heureArretString = record.get("heureArret").asString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            
            try 
            {
                java.util.Date parsedDate = dateFormat.parse(heureArretString);
                Time t =  new Time(parsedDate.getTime());
                System.out.println("tempss"+t );
                return t;
            } 
            catch (ParseException e) 
            {
            }
        }
    }
    // Si aucune heure d'arrêt n'est trouvée, retourner null ou une valeur par défaut
    return null;
}
    
    public List<Voyage> retrieve() 
    {
        List<Voyage> voyages = new ArrayList<>();
        List<Voyage> voyages1;
        Session session = driver.session();
        String requeteCypher = "MATCH (b:Bus) RETURN b.numeroBus AS numero, b.nombrePlacesLimite AS places";
        Result result = session.run(requeteCypher);
        while (result.hasNext()) 
        {
            org.neo4j.driver.Record record = result.next();
            int numeroBus = record.get("numero").asInt();
            Bus b = Bus.ChercherBusparNumero(numeroBus);
            voyages1 = retrieveParBus(b);
            voyages.addAll(voyages1);
        }
        return voyages;
}
       
   
    public List<Voyage> retrieveParBus(Bus bus) 
    {
        String req = "MATCH (s1:Station )-[:SUIT*]->(s2:Station {idStation : $ids}) RETURN s1";
        String query = "MATCH (b:Bus)-[a:Arreter]->(s:Station), (p:Passager)-[r:A_RESERVE]->(b:Bus) RETURN b, s, p, r, a, s.endStation AS endStation";
        Session session = driver.session();
        Result result = session.run(query);
        List<Voyage> voyages = new ArrayList<>();
        Passager passager;
        Reservation reservation;
        List<Reservation> lesReservations = new ArrayList();
        Station dep = null,arr=null;
        Time heureDepart = null,heureArrivee=null;
        int test = 0;
        int busv= 0;
        while (result.hasNext()) 
        {
            org.neo4j.driver.Record record = result.next();

            String CIN = record.get("p").get("CIN").asString();
            String NomPassager = record.get("p").get("NomPassager").asString();
            System.out.println("Hi: "+NomPassager);
            passager = Passager.PassagerexisteDeja(CIN);
            if(passager == null) passager = new Passager(CIN, NomPassager);

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm"); 
            Time heureReservation = null;
            try 
            {
                java.util.Date parsedDate = dateFormat.parse(record.get("r").get("heureReservation").asString());
                heureReservation = new Time(parsedDate.getTime());
            } 
            
            catch (ParseException e) {}   
            reservation = Reservation.getReservationgr(CIN,heureReservation);
            
            
            System.out.print("bus :" +bus.getIdBus() +"nombre de place libre: "  + bus.getNumeroPlacesLibres()+"nombre de place limite"+bus.getNumeroPlacesLimite());
            System.out.println("********le problemee***********"+reservation);
            if(reservation == null)
            	reservation = new Reservation(heureReservation, passager, null); 
            else if(test == 0)
            {
                lesReservations.add(reservation);
                
                test=1;
            }

            
            int idStation = record.get("s").get("idStation").asInt();
            Result result1 = session.run(req,Values.parameters("ids", idStation));
            String nomStation = record.get("s").get("nomStation").asString();
            int endStation = record.get("endStation").asInt();
            if (endStation == 0 && !result1.hasNext()) 
            {
                dep = Station.getStation(idStation);
                if(dep == null) dep = new Station(idStation, nomStation);
                heureDepart = Time.valueOf("00:00:00");
            }
            
            
            
            
            else if(endStation == 1)
            {
                arr = Station.getStation(idStation);
                if(arr == null) arr = new Station(idStation, nomStation);
                heureArrivee = Time.valueOf("00:00:00");
            } 
            Voyage voyage = null;
            if(arr!= null && dep != null && heureArrivee!= null && heureDepart != null)
            {
                voyage = new Voyage(bus, lesReservations, heureDepart,heureArrivee,dep, arr); 
                //reservation.setVoyage(voyage);
                System.out.println("----vooyage fiiinalll-- "+voyage);
                System.out.println(busv+ " et "+bus.getIdBus() );
                if(busv!=bus.getIdBus())
                {
                voyages.add(voyage);
                //bus.setNumeroPlacesLibres(bus.getNumeroPlacesLibres() - 1);
                
                busv =bus.getIdBus();
                System.out.println("je suis voyage de bud " +bus.getIdBus());
                }
                
                
                                      
                arr = null;
                heureArrivee = null;
                test = 0;
                lesReservations.clear();
            }
           
        }
        
        return voyages;
    }
    

  
    
    public void close() {
        driver.close();
    }
}



