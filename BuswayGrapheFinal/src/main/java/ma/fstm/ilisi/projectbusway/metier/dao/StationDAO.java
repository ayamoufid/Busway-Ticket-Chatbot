

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.dao;

import java.util.ArrayList;
import java.util.List;
import ma.fstm.ilisi.projectbusway.metier.bo.Station;
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
public class StationDAO 
{
    private static final String URI = "bolt://localhost:7687"; 
    private static final String USERNAME = "neo4j"; 
    private static final String PASSWORD = "12345678";
    private Driver driver = null;
    
    public StationDAO() 
    {
        driver = GraphDatabase.driver(URI, AuthTokens.basic(USERNAME, PASSWORD));
    }
    
    public void create(Station station)
    {
        try (Session session = driver.session()) 
        {
            String query = "CREATE (s:Station {idStation: $id, nomStation: $nom, latitude: $lat, longitude: $lon})";
            session.run(query, Values.parameters(
                    "id", station.getIdStation(),
                    "nom", station.getNomStation(),
                    "lat", station.getLat(),
                    "lon", station.getLon()
            ));
        }
    }
    
    public void update(Station station)
    {
        try (Session session = driver.session()) {
            String query = "MATCH (s:Station {idStation: $id}) SET s.nomStation = $nom, s.latitude = $lat, s.longitude = $lon";
            session.run(query, Values.parameters(
                    "id", station.getIdStation(),
                    "nom", station.getNomStation(),
                    "lat", station.getLat(),
                    "lon", station.getLon()
            ));
        }
    }
    
    public void delete(Station station)
    {
        try (Session session = driver.session()) 
        {
            String query = "MATCH (s:Station {idStation: $id}) DELETE s";
            session.run(query, Values.parameters("id", station.getIdStation()));
        }
    }
    
    public List<Station> retreive()
    {
        List<Station> stations = new ArrayList<>();
        try (Session session = driver.session()) 
        {
            String query = "MATCH (s:Station) RETURN s.idStation, s.nomStation, s.latitude, s.longitude";
            Result result = session.run(query);
            while (result.hasNext()) {
                org.neo4j.driver.Record record = result.next();
                int idStation = record.get("s.idStation").asInt();
                String nomStation = record.get("s.nomStation").asString();
                double lat = record.get("s.latitude").asDouble();
                double lon = record.get("s.longitude").asDouble();
                stations.add(new Station(idStation, nomStation, lat, lon));
            }
        }
        return stations;
    }
    
    public static void main(String[] args) 
    {
        
        StationDAO dao = new StationDAO();
        Station newStation = new Station(11, "New Station", 33.574, -7.657);
        dao.create(newStation);
        System.out.println("objet cree");
        /*Station existingStation = new Station(1, "AL laymoune", 33.575, -7.658);
        dao.update(existingStation);
        System.out.println("objet modif");
        dao.delete(newStation);
        System.out.println("objet supprime");*/
        dao.driver.close(); 
    }
}
