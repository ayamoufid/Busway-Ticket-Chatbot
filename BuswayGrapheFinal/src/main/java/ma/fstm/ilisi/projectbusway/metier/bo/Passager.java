/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.bo;

import java.util.ArrayList;
import java.util.List;
import ma.fstm.ilisi.projectbusway.metier.dao.PassagerDAO;

/**
 *
 * @author Aya
 */
public class Passager 
{
    private String CIN;
    private String NomPassager;
    public static List<Passager> passagers = new ArrayList();
    
    public Passager() {
    }

    public Passager(String CIN, String NomPassager) {
        this.CIN = CIN;
        this.NomPassager = NomPassager;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getNomPassager() {
        return NomPassager;
    }

    public void setNomPassager(String NomPassager) {
        this.NomPassager = NomPassager;
    }   
    
    public static void CreerPassagers()
    {
        passagers = new PassagerDAO().retreive();
        //System.out.println("passagers "+passagers.getFirst().NomPassager);
    }
    
    public static Passager PassagerexisteDeja(String CIN) 
    {
        for (Passager passager : passagers) 
            if (passager.getCIN().equals(CIN)) 
                return passager;
        return null;
    }
}
