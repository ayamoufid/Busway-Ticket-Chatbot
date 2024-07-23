package ma.fstm.ilisi.projectbusway.metier.service;

/*import java.io.File;
import java.time.LocalDateTime;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;

public class ReservationBot {
    public Chat chatSession;

    public ReservationBot() {
    	String resourcepath = getpath();
		MagicBooleans.trace_mode = false;
		Bot b = new Bot("sup",resourcepath);
        chatSession = new Chat(b);
    }
    
    private static String getpath()
	{
		File currd=new File(".");
		//String path=currd.getAbsolutePath();
		String resourcepath="src" + File.separator +"main"+File.separator+"resources";	
		return resourcepath;
	}

    public String processInput(String input) 
    {
        String response = chatSession.multisentenceRespond(input);
        if (response.equals("Recherche des lignes disponibles...")) 
        {
        	String stationDepart = context.get("station_depart");
            if (stationDepart.equals("Al Qods")) //!listeDesStations.contains(stationDepart)
            {
                return "Désolé, la station " + stationDepart + " n'existe pas. Veuillez choisir une autre station.";
            }
            else return "Ligne 1";
        } 
        else if (response.equals("Réservation en cours...")) 
        {
            LocalDateTime heureReservation = LocalDateTime.now();
            return "heureReservation = "+heureReservation;
        }
        return response;
    }
}*/


import java.io.File;   
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;

import ma.fstm.ilisi.projectbusway.controleur.Busway;
import ma.fstm.ilisi.projectbusway.metier.bo.CatalogueStation;
import ma.fstm.ilisi.projectbusway.metier.bo.Station;
import ma.fstm.ilisi.projectbusway.metier.bo.Voyage;
import ma.fstm.ilisi.projectbusway.presentation.Reserver;

public class ReservationBot 
{
    public Chat chatSession;
    public String stationDepart = "";
    private String ligneChoisie = "";
    private String etat;
    public List <Voyage> voyagesp;
	private List<String> listeDesLignes;
	private String cin;
    private String nom;
    
    public ReservationBot() 
    {
    	String resourcepath = getpath();
		MagicBooleans.trace_mode = false;
		Bot b = new Bot("sup",resourcepath);
        chatSession = new Chat(b);
        etat = "";
    }

    private static String getpath()
	{
		File currd=new File(".");
		//String path=currd.getAbsolutePath();
		String resourcepath="src" + File.separator +"main"+File.separator+"resources";	
		return resourcepath;
	}

    public List<String> getAllNames() 
    {      
        Station dep = Busway.trouverStation(stationDepart);
        voyagesp=Busway.trouverVoyageLigne(dep);
        String textForScrollPane = Reserver.generateTextForScrollPane(voyagesp);
        int i=1;
        List<String> stationNames = new ArrayList<>();
        for(Voyage v: voyagesp)
        {
            System.out.println(v.getIdVoyage());
            stationNames.add("Ligne "+i);
            i++;
        }
        return stationNames;
}
    
    public String processInput(String input) 
    {
    	CatalogueStation cs = CatalogueStation.getCatalogueStation();
    	if (etat.equals("attente_station_depart")) 
    	{
    		String[] parts = input.split("EST");
    	    if (parts.length > 1) 
    	    {
    	        stationDepart = parts[1].trim();
    	    }
    	    etat = "";
        }
    	else if (etat.equals("attente_ligne")) 
        {
            String[] parts = input.split(" ");
            if (parts.length > 1) 
            {
                ligneChoisie = parts[parts.length - 1].trim();
                System.out.println(ligneChoisie);
            }
            etat = "";
        }
    	else if (etat.equals("attente_cin_nom")) 
        {
            String[] parts = input.split(" ");
            if (parts.length > 1) 
            {
                cin = parts[0];
                nom = parts[1];
            }
            etat = "";
        	Voyage v=voyagesp.get(Integer.parseInt(ligneChoisie)-1);
        	Station dep = Busway.trouverStation(stationDepart);
        	boolean find = Busway.reserver(v,cin,nom,dep);
            System.out.println(find);
        	LocalTime heureReservation = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return "Reservation effectue : Station de depart = " + stationDepart +" heureReservation = " + heureReservation.format(formatter);
        }
        String response = chatSession.multisentenceRespond(input);
        if (response.equals("Quelle est votre station de départ?")) 
        {
        	 etat = "attente_station_depart";
        }
        else if (response.equals("Recherche des lignes disponibles...")) 
        {
        	boolean stationExiste = false;
            for (Station station : cs.getLesStations()) 
            {
                if (station.getNomStation().equals(stationDepart)) 
                {
                    stationExiste = true;
                    break;
                }
            }
            if (!stationExiste) 
            {
                etat = "attente_station_depart";
                return "Désolé, la station " + stationDepart + " n'existe pas. Veuillez choisir une autre station.";
            }
            else
            {
            	listeDesLignes = getAllNames();
            	etat = "attente_ligne";
                Station dep = Busway.trouverStation(stationDepart);
            	voyagesp=Busway.trouverVoyageLigne(dep);
            	return Reserver.generateTextForScrollPane(voyagesp); 
            }
            
        } 
        else if (response.startsWith("Réservation en cours pour la ligne")) 
        {
        	etat = "attente_cin_nom";
        	//etat = "attente_ligne";
        	if (!listeDesLignes.contains("Ligne "+ligneChoisie)) 
            {
        		etat = "attente_ligne";
                return "Désolé, la ligne " + ligneChoisie + " n'existe pas. Veuillez choisir une autre ligne.";
            }
            return "Veuillez entrer votre CIN et votre nom pour confirmer la réservation.";
        }
        
        	
        return response;
    }
}


