/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.bo;

import java.util.List;

/**
 *
 * @author Aya
 */
public class Conducteur 
{
    private int idConducteur;
    private String nomConducteur;

    
    static Conducteur c1,c2,c3,c4;
    public Conducteur() 
    {    
        c1 = new Conducteur(1,"aya");
        c2 = new Conducteur(2,"aya1");
        c3 = new Conducteur(3,"aya2");
        c4 = new Conducteur(4,"aya3");

    }
    
    
    public Conducteur(int idConducteur, String nomConducteur) {
        this.idConducteur = idConducteur;
        this.nomConducteur = nomConducteur;
    }
   
    public int getIdConducteur() {
        return idConducteur;
    }

    public void setIdConducteur(int idConducteur) {
        this.idConducteur = idConducteur;
    }

    public String getNomConducteur() {
        return nomConducteur;
    }

    public void setNomConducteur(String nomConducteur) {
        this.nomConducteur = nomConducteur;
    }
    
    
}
