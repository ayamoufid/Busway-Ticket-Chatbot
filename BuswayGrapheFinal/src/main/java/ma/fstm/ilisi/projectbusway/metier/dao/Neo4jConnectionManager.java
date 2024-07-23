/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.dao;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
/**
 *
 * @author Aya
 */
public class Neo4jConnectionManager 
{
     private final Driver driver;

    private final String uri = "bolt://localhost:7687";
    private final String username = "busway";
    private final String password = "12345678";

    public Neo4jConnectionManager() {
        this.driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }

    /**
     * Gets a session for executing queries against the database.
     *
     * @return A session for executing queries.
     */
    public Session getSession() {
        return driver.session();
    }

    /**
     * Closes the driver and its associated resources.
     */
    public void close() {
        driver.close();
    }
    
    
    public static void main(String[] args) 
    {
        // URI examples: "neo4j://localhost", "neo4j+s://xxx.databases.neo4j.io"
        final String dbUri = "bolt://localhost:7687";
        final String dbUser = "busway";
        final String dbPassword = "12345678";

        try (var driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword))) {
            driver.verifyConnectivity();
        }
    }
}
