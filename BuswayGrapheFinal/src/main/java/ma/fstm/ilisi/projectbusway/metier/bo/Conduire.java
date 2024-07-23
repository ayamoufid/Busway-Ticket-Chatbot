/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.bo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aya
 */
public class Conduire 
{
    private Bus bus;
    private Conducteur chauffeur;
    public static List<Conduire> cond= new ArrayList();
    
    CatalogueBus b = CatalogueBus.getCatalogueBus();
    public Conduire() 
    {   new Conducteur();
        Conduire cn1= new Conduire(b.getLesBus().get(0),Conducteur.c1);
        cond.add(cn1);
        Conduire cn2= new Conduire(b.getLesBus().get(1),Conducteur.c2);
        cond.add(cn2);
        Conduire cn3= new Conduire(b.getLesBus().get(0),Conducteur.c3);
        cond.add(cn3);
        Conduire cn4= new Conduire(b.getLesBus().get(1),Conducteur.c4);
        cond.add(cn4);
        
    }

    public Conduire(Bus lesBus, Conducteur lesChauffeurs) {
        this.bus = lesBus;
        this.chauffeur = lesChauffeurs;
    }

    public Bus getLesBus() {
        return bus;
    }

    public void setLesBus(Bus lesBus) {
        this.bus = lesBus;
    }

    public Conducteur getLesChauffeurs() {
        return chauffeur;
    }

    public void setLesChauffeurs(Conducteur lesChauffeurs) {
        this.chauffeur = lesChauffeurs;
    }
    
    
}
