/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.bo;

import java.sql.Time;
import java.util.*;
import ma.fstm.ilisi.projectbusway.metier.dao.ReservationDAO;

/**
 *
 * @author Aya
 */
public class Reservation 
{
    private int idReservation;
    private Time heureReservation;
    private Passager passager;
    private Voyage voyage;
    private Station stationFrom;
    public static List<Reservation> reserv;
    public static List<Reservation> reservMem = new ArrayList();
    public static int ident = 1;
    
    public Reservation() 
    {
        this.idReservation = ident++;
        reservMem.add(this);
    }

    public Reservation(int idReservation, Time heureReservation) 
    {
        this.idReservation = idReservation;
        this.heureReservation = heureReservation;
        reservMem.add(this);
    }
    
     public Reservation(int idReservation, Time heureReservation, Passager p , Voyage v) 
    {
        this.idReservation = idReservation;
        this.heureReservation = heureReservation;
        this.passager = p;
        this.voyage = v;
        reservMem.add(this);
    }

    public Reservation(int idReservation, Time heureReservation, Passager p, Voyage v, Station f) 
    {
        this.idReservation = idReservation;
        this.heureReservation = heureReservation;
        this.passager = p;
        this.voyage = v;
        this.stationFrom = f;
        reservMem.add(this);
    }  
    
     public Reservation( Time heureReservation, Passager p, Voyage v, Station f ) 
    {
        this.idReservation = ident++;
        this.heureReservation = heureReservation;
        this.passager = p;
        this.voyage = v;
        this.stationFrom = f; 
        reservMem.add(this);
    }  
        
     public Reservation( Time heureReservation, Passager p) 
     {
    	 this.idReservation = ident++;
         this.heureReservation = heureReservation;
         this.passager = p;
         reservMem.add(this);
     }  

    public Reservation(Time heureReservation, Passager p, Voyage v) 
    {
        this.idReservation = ident++;
        this.heureReservation = heureReservation;
        this.passager = p;
        this.voyage = v; 
        reservMem.add(this);
    }
    
    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public Time getHeureReservation() {
        return heureReservation;
    }

    public void setHeureReservation(Time heureReservation) {
        this.heureReservation = heureReservation;
    }

    public Passager getPassager() {
        return passager;
    }

    public void setPassager(Passager p) {
        this.passager = p;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage v) {
        this.voyage = v;
    }

    public Station getStationFrom() {
        return stationFrom;
    }

    public void setStationFrom(Station f) {
        this.stationFrom = f;
    }
   
    static 
    {
        CreerReservations();
    }

    public static Reservation getReservation(String CIN, Time heure)
    {
        if(reservMem != null)
        {
            for(Reservation res : reservMem)
            {
                if(res.getPassager().getCIN().equals(CIN) &&
                        res.getHeureReservation().equals(heure))
                    return res;
            }
        }
        return null;
    }
    
    
    public static Reservation getReservationgr(String CIN, Time heure)
    {
        for(Reservation res : reserv)
        {
            if(res.getPassager().getCIN().equals(CIN) &&
                    res.getHeureReservation().equals(heure))
                return res;
        }
        return null;
    }
    
    
    public static void CreerReservations() 
    {
        reserv = new ReservationDAO().retrieveReservations();
        System.out.println("reservationss passager : " + reserv.get(1).passager.getNomPassager());
    }
    

    @Override
    public String toString() {
        return "Reservation{" + "idReservation=" + idReservation + ", heureReservation=" + heureReservation + ", passager=" + passager + ", stationFrom=" + stationFrom + '}';
    }
}

