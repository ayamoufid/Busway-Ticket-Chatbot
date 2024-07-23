/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.dao;

import java.util.ArrayList;
import java.util.List;
import ma.fstm.ilisi.projectbusway.metier.bo.Station;
import org.neo4j.driver.*;
/**
 *
 * @author Aya
 */
public class TrajetDAO 
{
    private static final String URI = "bolt://localhost:7687"; 
    private static final String USERNAME = "neo4j"; 
    private static final String PASSWORD = "12345678";
     private final Driver driver;

    public TrajetDAO() {
        driver = GraphDatabase.driver(URI, AuthTokens.basic(USERNAME, PASSWORD));
    }

    public void create(Station station1, Station station2) 
    {
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (s1:Station {idStation: $station1Id}), (s2:Station {idStation: $station2Id}) CREATE (s1)-[:SUIT]->(s2)";
            session.run(cypherQuery, 
                        Values.parameters("station1Id", station1.getIdStation(), 
                                          "station2Id", station2.getIdStation()));
        }
    }

    
    public List<Station> getTrajet(Station stationDep) 
    {
        List<Station> trajet = new ArrayList<>();
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (s1:Station {idStation: $stationDepId})-[:SUIT*]->(s2:Station) RETURN s2";
            Result result = session.run(cypherQuery, Values.parameters("stationDepId", stationDep.getIdStation()));
            while (result.hasNext()) 
            {
                org.neo4j.driver.Record record = result.next();
                Station station = new Station();
                station.setIdStation(record.get("s2").get("idStation").asInt());
                station.setNomStation(record.get("s2").get("nomStation").asString());
                trajet.add(station);
            }
        }
        return trajet;
    }


    public void update(Station station1, Station oldStation2, Station newStation2) 
    {
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (s1:Station {idStation: $station1Id})-[r:SUIT]->(s2:Station {idStation: $oldStation2Id}) DELETE r CREATE (s1)-[:SUIT]->(s2:Station {idStation: $newStation2Id})";
            session.run(cypherQuery, 
                        Values.parameters("station1Id", station1.getIdStation(), 
                                          "oldStation2Id", oldStation2.getIdStation(),
                                          "newStation2Id", newStation2.getIdStation()));
        }
    }


    public void delete(Station station1, Station station2) 
    {
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (s1:Station {idStation: $station1Id})-[r:SUIT]->(s2:Station {idStation: $station2Id}) DELETE r";
            session.run(cypherQuery, 
                        Values.parameters("station1Id", station1.getIdStation(), 
                                          "station2Id", station2.getIdStation()));
        }
    }

    public void close() 
    {
        driver.close();
    }
}
