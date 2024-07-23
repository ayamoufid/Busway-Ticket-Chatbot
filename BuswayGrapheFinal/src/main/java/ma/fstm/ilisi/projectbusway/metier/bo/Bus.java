/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.bo;


import java.util.ArrayList;
import java.util.List;
import ma.fstm.ilisi.projectbusway.metier.dao.VoyageDAO;

/**
 *
 * @author Aya
 */
public class Bus 
{
    private int idBus;
    private int numerOBus;
    private int numeroPlacesLimite;
    private int numeroPlacesLibres;
    
    public List<Voyage> lesVoyages = new ArrayList();
    public static int ident3 = 1;
    
    private static List<Bus> tousLesBus = new ArrayList();

    
    public Bus() {
        this.idBus = ident3++;
    }

    @Override
    public String toString() {
        return " numerOBus=" + numerOBus;
    }

    public Bus(int numeroBus) 
    {
        this.idBus = ident3++;
        this.numerOBus = numeroBus;
        tousLesBus.add(this);
    }

     public Bus(int numerOBus, int numeroPlacesLimite) {
         this.idBus = ident3++;
        this.numerOBus = numerOBus;
        this.numeroPlacesLimite = numeroPlacesLimite;
        this.numeroPlacesLibres = numeroPlacesLimite;
        tousLesBus.add(this);
    }
    
     
    public Bus(int id,int numerOBus, int numeroPlacesLimite) {
        
        this.idBus = id;
        this.numerOBus = numerOBus;
        this.numeroPlacesLimite = numeroPlacesLimite;
        this.numeroPlacesLibres = numeroPlacesLimite;
        tousLesBus.add(this);
    }
    

    public Bus(int idBus,int numerOBus, int numeroPlacesLimite, int numeroPlacesLibres, List<Voyage> lesVoyages) {
       this.idBus = idBus;
        this.numerOBus = numerOBus;
        this.numeroPlacesLimite = numeroPlacesLimite;
        this.numeroPlacesLibres = numeroPlacesLibres;
        this.lesVoyages = lesVoyages;
        tousLesBus.add(this);
    }

    public int getIdBus() {
        return idBus;
    }

    public void setIdBus(int idBus) {
        this.idBus = idBus;
    }

    public static List<Bus> getTousLesBus() {
        return tousLesBus;
    }

    public static void setTousLesBus(List<Bus> tousLesBus) {
        Bus.tousLesBus = tousLesBus;
    }
   
    public int getNumeroBus() {
        return numerOBus;
    }

    public void setNumeroBus(int numerOBus) {
        this.numerOBus = numerOBus;
    }

    public int getNumeroPlacesLimite() {
        return numeroPlacesLimite;
    }

    public void setNumeroPlacesLimite(int numeroPlacesLimite) {
        this.numeroPlacesLimite = numeroPlacesLimite;
    }

    public int getNumeroPlacesLibres() {
        return numeroPlacesLibres;
    }

    public void setNumeroPlacesLibres(int numeroPlacesLibres) {
        this.numeroPlacesLibres = numeroPlacesLibres;
    }

    public List<Voyage> getLesVoyages() {
        return lesVoyages;
    }

    public void setLesVoyages(List<Voyage> lesVoyages) {
        this.lesVoyages = lesVoyages;
    }
    
    
    public int calculerPlacesLibres() 
    {
        int placesOccupees = 0;        
        for (Voyage voyage : lesVoyages) 
            placesOccupees += voyage.getLesReservations().size();
        //numeroPlacesLibres = numeroPlacesLimite - placesOccupees;
        return numeroPlacesLibres;
    }
    
    //here
    public int calculatePlacesLibres(Voyage v) 
    {
        int placesOccupees = 0;        
        for (Voyage voyage : lesVoyages) 
            if(voyage == v)
            {
                placesOccupees += v.getLesReservations().size();
                break;
            }
        //numeroPlacesLibres = numeroPlacesLimite - placesOccupees;
        return numeroPlacesLibres;
    }
    
    public void addVoyage(Voyage voyage) 
    {
        lesVoyages.add(voyage);
    }
       
    public static List<Bus> obtenirListeBus() 
    {
        return tousLesBus;
    }
    
    
    public static List<Bus> obtenirListeBusDispo()
    {
        List<Bus> bus = CatalogueBus.getLesBus();
        return bus;
    }
       
    
    
    public static Bus ChercherBusparId(int id)
    {
        for(Bus b : CatalogueBus.getLesBus())
            if(b.getIdBus() == id)
                return b;
        return null;
    }
    
    public static void AssocierBusVoyage()
    {
        for(Bus b : Bus.getTousLesBus())
        {
            b.setLesVoyages(new VoyageDAO().retrieveParBus(b));
           // System.out.println("busvoyage "+b.getLesVoyages().getFirst());
        }
    }
    public static Bus ChercherBusparNumero(int num)
    {
    	System.out.println("tessssssssssssssssss");
        for(Bus b : CatalogueBus.getLesBus())
        {
        	System.out.println("tessssssssssssssssss"+b.getNumeroBus());
            if(b.getNumeroBus() == num)
                return b;
        }
        return null;
    }
}