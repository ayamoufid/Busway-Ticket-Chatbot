/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.bo;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ma.fstm.ilisi.projectbusway.metier.dao.ReservationDAO;
import ma.fstm.ilisi.projectbusway.metier.dao.VoyageDAO;

/**
 *
 * @author Aya
 */

public class Voyage 
{
    private int idVoyage;
    private Bus leBus;
    private List<Reservation> lesReservations = new ArrayList();
    private Time dateDepart;//heureDepart HH:mm
    private Time dateArrivee;//heure arrivee HH:mm
    private Station leDepart;
    private Station lArrivee; 
    private static List<Voyage> tousLesVoyages = new ArrayList();
    public static Voyage v1,v2,v3,v4,v5,v6,v7,v8;
    private static int ident1 = 1;
    
    public Voyage() 
    {
        this.idVoyage = ident1++;
        tousLesVoyages.add(this);
    }

    public Voyage(int idVoyage) 
    {
        this.idVoyage = idVoyage;
        tousLesVoyages.add(this);
    }

    public Voyage(int idVoyage, Bus leBus, List<Reservation> lesReservations, Time dateDepart, Time dateArrivee, Station leDepart, Station lArrivee) {
        this.idVoyage = idVoyage;
        this.leBus = leBus;
        this.lesReservations = lesReservations;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.leDepart = leDepart;
        this.lArrivee = lArrivee;
        tousLesVoyages.add(this);
    }
    
    
    public Voyage( Bus leBus, List<Reservation> lesReservations,  Station leDepart, Station lArrivee) {

        this.idVoyage = ident1++;
        this.leBus = leBus;
        this.lesReservations = lesReservations;
        this.leDepart = leDepart;
        this.lArrivee = lArrivee;
        tousLesVoyages.add(this);
    }
    
    
     public Voyage( Bus leBus, List<Reservation> lesReservations, Time dateDepart, Time dateArrivee, Station leDepart, Station lArrivee) {

         this.idVoyage = ident1++;
        this.leBus = leBus;
        this.lesReservations = lesReservations;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.leDepart = leDepart;
        this.lArrivee = lArrivee;
        tousLesVoyages.add(this);
    }

    
    public int getIdVoyage() {
        return idVoyage;
    }

    public void setIdVoyage(int idVoyage) {
        this.idVoyage = idVoyage;
    }

    public Bus getBus() {
        return leBus;
    }

    public void setBus(Bus leBus) {
        this.leBus = leBus;
    }

    public List<Reservation> getLesReservations() {
        return lesReservations;
    }

    public void setLesReservations(List<Reservation> lesReservations) {
        this.lesReservations = lesReservations;
    }

    public Time getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Time dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Time getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Time dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public Station getLeDepart() {
        return leDepart;
    }

    public void setLeDepart(Station leDepart) {
        this.leDepart = leDepart;
    }

    public Station getlArrivee() {
        return lArrivee;
    }

    public void setlArrivee(Station lArrivee) {
        this.lArrivee = lArrivee;
    }
    
    
    public static List<Voyage> ObtenirListeVoyages() 
    {  
        return tousLesVoyages;
    }
    
    
     public void addReservation(Reservation reservation) 
     {
        lesReservations.add(reservation);
    }
     
     public static Time genrerHeure1(int min) {
         Calendar calendar = Calendar.getInstance(); // Obtient la date et l'heure actuelles
         calendar.add(Calendar.MINUTE, min); // Ajoute 10 minutes à l'heure actuelle
         return new Time(calendar.getTimeInMillis()); // Retourne le nouvel objet Time qui est 10 minutes dans le futur
     }
      
      public static Time genrerHeure2(int hour) {
         Calendar calendar = Calendar.getInstance(); // Obtient la date et l'heure actuelles
         calendar.add(Calendar.HOUR, hour); 
         return new Time(calendar.getTimeInMillis()); // Retourne le nouvel objet Time qui est 10 minutes dans le futur
     }

    @Override
    public String toString() {
        return "Voyage{" + "idVoyage=" + idVoyage + ", dateDepart=" + dateDepart + ", dateArrivee=" + dateArrivee + ", leDepart=" + leDepart + ", lArrivee=" + lArrivee + '}';
    }
         
     public static void CreerVoyages()
     {
       tousLesVoyages = new VoyageDAO().retrieve();
      System.out.println("les reservation://///////////////////////////");
      for(Voyage v:tousLesVoyages)
      {
    	 
    	 List<Reservation> rs =new ReservationDAO().getPassagersReserves(v.getBus().getIdBus());
    	 v.setLesReservations(rs);
       System.out.println("bus : " +v.getBus().getIdBus());
       for(Reservation r:rs)
       {
    	   System.out.println("reserv :" +r.getPassager().getNomPassager());
       }
       
      }
        
      
      
     }
     
     
     
     public static boolean voyageExisteDeja(Voyage voyageAInserer, List<Voyage> voyagesExistants) {
         for (Voyage voyage : voyagesExistants) {
             // Comparer les attributs pertinents pour vérifier si le voyage existe déjà
             if (voyage.getIdVoyage()==voyageAInserer.getIdVoyage())
                 return true;
             }
         // Aucun voyage correspondant trouvé, le voyage n'existe pas déjà
         return false;
     }
}

