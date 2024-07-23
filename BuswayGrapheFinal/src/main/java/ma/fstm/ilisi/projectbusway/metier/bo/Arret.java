/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.bo;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import ma.fstm.ilisi.projectbusway.metier.dao.ArretDAO;
import ma.fstm.ilisi.projectbusway.metier.dao.BusDAO;

/**
 *
 * @author Aya
 */
public class Arret 
{
    private int idArret;
    private Time heureArret;
    private Voyage voyage;
    private Station station;
    private int ordre;
    public static List<Arret> lesarrets = new ArrayList();
    public static int ident2 = 1;
    
    public Arret() {
        this.idArret = ident2++;
    }

    public Arret(int idArret, Time heureArret, Voyage Voyage1, Station Station1) 
    {
        this.idArret = idArret;
        this.heureArret = heureArret;
        this.voyage = Voyage1;
        this.station = Station1;
    }
    
   

    @Override
    public String toString() {
        return "Arret{" + "idArret=" + idArret + ", heureArret=" + heureArret + ", voyage=" + voyage + ", station=" + station + '}';
    }
    
    
    public Arret(Time heureArret, Voyage Voyage1, Station Station1) 
    {
        this.idArret = ident2++;
        this.heureArret = heureArret;
        this.voyage = Voyage1;
        this.station = Station1;
    }
    

    public int getIdArret() {
        return idArret;
    }

    public void setIdArret(int idArret) {
        this.idArret = idArret;
    }

    public Time getHeureArret() {
        return heureArret;
    }

    public void setHeureArret(Time heureArret) {
        this.heureArret = heureArret;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage v) {
        this.voyage = v;
    }

    public Station getStation() {
    return station;
    }

    public void setStation(Station s) {
        this.station = s;
    }
    
    
    
    public int getOrdre() {
		return ordre;
	}

	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}

	public static Time addRandomMinutes(Time time) {
        Random random = new Random();
        int minutesToAdd = random.nextInt(11) + 5; // Génère un nombre aléatoire entre 5 et 15 inclus
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(time); 
        calendar.add(Calendar.MINUTE, minutesToAdd); 
        return new Time(calendar.getTimeInMillis());
    }
    
    public static Time addMinutes(double minutes, Time time) {
        // Convertir le nombre de minutes en entier
        int minutesToAdd = (int) Math.round(minutes); // Arrondir le nombre de minutes

        // Créer un objet Calendar pour manipuler le temps
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(time); 

        // Ajouter les minutes spécifiées au temps actuel
        calendar.add(Calendar.MINUTE, minutesToAdd); 

        // Retourner le nouveau temps après l'ajout des minutes
        return new Time(calendar.getTimeInMillis());
    }
    
    private static Comparator<Arret> arretComparator = new Comparator<Arret>() {
        @Override
        public int compare(Arret a1, Arret a2) {
            // Compare les IDs des bus
            int compareBusIds = Integer.compare(a1.getVoyage().getBus().getIdBus(), a2.getVoyage().getBus().getIdBus());
            if (compareBusIds == 0) {
                // Si les IDs de bus sont égaux, compare les heures d'arrêt
                return a1.getHeureArret().compareTo(a2.getHeureArret());
            }
            return compareBusIds;
        }
    };
    
    
    
    private static boolean arretsCrees = false;

    public static void CreerArrets() {
        if (!arretsCrees) 
        {
            lesarrets = new ArretDAO().retrieve();
            System.out.println("Creer Arret:");
            for(Arret a : lesarrets) 
            {
                System.out.println("id station " + a.getStation().getIdStation() + " heure " + a.getHeureArret() + " nom station " +a.getStation().getNomStation());
            }
            arretsCrees = true;
        } else {
            System.out.println("Les arrêts ont déjà été créés.");
        }
        
        
        
    }

         
}