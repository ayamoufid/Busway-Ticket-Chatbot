/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.dao;



import java.util.ArrayList;
import java.util.List;
import ma.fstm.ilisi.projectbusway.metier.bo.Bus;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;

/**
 *
 * @author Aya
 */
public class BusDAO 
{
    private static final String URI = "bolt://localhost:7687"; 
    private static final String USERNAME = "neo4j"; 
    private static final String PASSWORD = "12345678"; 
    private Driver driver = null;
    
    public BusDAO() 
    {
        driver = GraphDatabase.driver(URI, AuthTokens.basic(USERNAME, PASSWORD));
    }
    
    
    public void create(Bus bus) 
    {
        try (Session session = driver.session()) 
        {
            String query = "CREATE (b:Bus { numeroBus: $numero, nombrePlacesLimite: $placesLimite})";
            session.run(query, Values.parameters(
                    "numero", bus.getNumeroBus(),
                    "placesLimite", bus.getNumeroPlacesLimite()
            ));
        }
    }

    public void update(Bus bus) 
    {
        try (Session session = driver.session()) 
        {
            String query = "MATCH (b:Bus {numeroBus: $numero}) SET b.nombrePlacesLimite = $placesLimite";
            session.run(query, Values.parameters(
                    "numero", bus.getNumeroBus(),
                    "placesLimite", bus.getNumeroPlacesLimite()
            ));
        }
    }

    public void delete(Bus bus) 
    {
        try (Session session = driver.session()) 
        {
            String query = "MATCH (b:Bus {numeroBus: $numero}) DELETE b";
            session.run(query, Values.parameters("numeroBus", bus.getNumeroBus()));
        }
    } 
    public List<Bus> retreive()
    {       
        List<Bus> listeBus = new ArrayList<>();
        Session session = driver.session();
        String requeteCypher = "MATCH (b:Bus) RETURN b";
        Result result = session.run(requeteCypher);
        while (result.hasNext()) 
        {
        	
            org.neo4j.driver.Record record = result.next();
            int numeroBus = record.get("b").get("numeroBus").asInt();
            int numeroPlacesLimite = record.get("b").get("nombrePlacesLimite").asInt();
            Bus bus = new Bus( numeroBus, numeroPlacesLimite);
            listeBus.add(bus);
            System.out.println("how many bus");
             bus.setNumeroPlacesLibres(bus.getNumeroPlacesLibres()  -  new ReservationDAO().getNombreReservationsForBus(numeroBus));
        }
        return listeBus;
    }
    
//    
//    public static void main(String[] args) 
//    {
//        BusDAO dao = new BusDAO();
//        Bus newBus = new Bus(11, 123, 50);
//        dao.create(newBus);
//        System.out.println("objet cree");
//        Bus existingBus = new Bus(1, 2, 60);
//        dao.update(existingBus);
//        System.out.println("objet modifie");
//        dao.delete(newBus);
//        System.out.println("objet supprime");
//        dao.driver.close();
//    }
}
