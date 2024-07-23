/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.dao;

import java.util.ArrayList;
import java.util.List;
import ma.fstm.ilisi.projectbusway.metier.bo.Passager;
import org.neo4j.driver.*;

/**
 *
 * @author Aya
 */
public class PassagerDAO 
{
    private static final String URI = "bolt://localhost:7687"; 
    private static final String USERNAME = "neo4j"; 
    private static final String PASSWORD = "12345678"; 
    private Driver driver = null;
    
    public PassagerDAO() 
    {
        driver = GraphDatabase.driver(URI, AuthTokens.basic(USERNAME, PASSWORD));
    }
    
    public void create(Passager passager) 
    {
        try (Session session = driver.session()) 
        {
            String query = "CREATE (p:Passager {CIN: $cin, NomPassager: $nom})";
            session.run(query, Values.parameters(
                    "cin", passager.getCIN(),
                    "nom", passager.getNomPassager()
            ));
        }
    }

    public void update(Passager passager) 
    {
        try (Session session = driver.session()) 
        {
            String query = "MATCH (p:Passager {CIN: $cin}) SET p.NomPassager = $nom";
            session.run(query, Values.parameters(
                    "cin", passager.getCIN(),
                    "nom", passager.getNomPassager()
            ));
        }
    }
    

    public void delete(Passager passager) 
    {
        try (Session session = driver.session()) 
        {
            String query = "MATCH (p:Passager {CIN: $cin}) DELETE p";
            session.run(query, Values.parameters("cin", passager.getCIN()));
        }
    }
    
    public List<Passager> retreive()
    {
        List<Passager> passagers = new ArrayList();
        try (Session session = driver.session()) 
        {
            String query = "MATCH (s:Passager) RETURN s.CIN, s.NomPassager";
            Result result = session.run(query);
            while (result.hasNext()) 
            {
                org.neo4j.driver.Record record = result.next();
                String CIN = record.get("s.CIN").asString();
                String nomPassager = record.get("s.NomPassager").asString();         
                passagers.add(new Passager(CIN, nomPassager));
            }
        }
        return passagers;
    }
    
    
    public static void main(String[] args) 
    {
        PassagerDAO dao = new PassagerDAO();
        Passager newPassager = new Passager("123456", "test");
        dao.create(newPassager);
        System.out.println("objet cree");
        Passager existingPassager = new Passager("ABC123", "Aya update");
        dao.update(existingPassager);
        System.out.println("objet modif");
        dao.delete(newPassager);
        System.out.println("objet supprime");
        dao.driver.close();
    }
}
