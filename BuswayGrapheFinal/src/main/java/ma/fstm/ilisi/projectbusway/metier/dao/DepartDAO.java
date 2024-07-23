/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.dao;

import java.util.ArrayList;
import java.util.List;
import ma.fstm.ilisi.projectbusway.metier.bo.*;
import org.neo4j.driver.*;

/**
 *
 * @author Aya
 */
public class DepartDAO 
{
    private static final String URI = "bolt://localhost:7687"; 
    private static final String USERNAME = "neo4j"; 
    private static final String PASSWORD = "12345678";
    private final Driver driver;

    public DepartDAO() {
        driver = GraphDatabase.driver(URI, AuthTokens.basic(USERNAME, PASSWORD));
    }

    public void create(Bus bus, Station station) 
    {
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (bus:Bus {numeroBus: $busNum}), (station:Station {idStation: $stationId}) "
                    + "         CREATE (bus)-[:A_DEPART]->(station)";
            session.run(cypherQuery, 
                        Values.parameters("busNum", bus.getNumeroBus(), 
                                          "stationId", station.getIdStation()));
        }
    }

    public List<Voyage> retreive(Station station) 
    {
        List<Voyage> departs = new ArrayList<>();
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (bus:Bus)-[:A_DEPART]->(station:Station {idStation: $stationId}) RETURN bus";
            Result result = session.run(cypherQuery, Values.parameters("stationId", station.getIdStation()));
            while (result.hasNext()) 
            {
                org.neo4j.driver.Record record = result.next();
                Voyage voyage = new Voyage();
                voyage.setBus(new Bus(record.get("bus").get("numeroBus").asInt()));
                departs.add(voyage);
            }
        }
        return departs;
    }

    
    public void update(Bus bus, Station oldStation, Station newStation) 
    {
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (bus:Bus {numeroBus: $busNum})-[r:A_DEPART]->(station:Station {idStation: $oldStationId}) DELETE r CREATE (bus)-[:A_DEPART]->(station:Station {idStation: $newStationId})";
            session.run(cypherQuery, 
                        Values.parameters("busNum", bus.getNumeroBus(), 
                                          "oldStationId", oldStation.getIdStation(),
                                          "newStationId", newStation.getIdStation()));
        }
    }

    
    public void deleteDepart(Bus bus, Station station) 
    {
        try (Session session = driver.session()) 
        {
            String cypherQuery = "MATCH (bus:Bus {numeroBus: $busNum})-[r:A_DEPART]->(station:Station {idStation: $stationId}) DELETE r";
            session.run(cypherQuery, 
                        Values.parameters("busNum", bus.getNumeroBus(), 
                                          "stationId", station.getIdStation()));
        }
    }

    public void close() {
        driver.close();
    }
}
