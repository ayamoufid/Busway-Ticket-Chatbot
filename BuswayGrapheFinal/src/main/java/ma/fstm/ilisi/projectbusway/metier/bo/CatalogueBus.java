/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.bo;

import java.util.ArrayList;
import java.util.List;
import ma.fstm.ilisi.projectbusway.metier.dao.BusDAO;

/**
 *
 * @author Aya
 */
public class CatalogueBus 
{
    private static CatalogueBus cb = null;
    private static List<Bus> lesBus = new ArrayList();
    private CatalogueBus() 
    {    
        BusDAO bdao = new BusDAO();
        lesBus = bdao.retreive();
    }
    
    
    public static CatalogueBus getCatalogueBus()
    {
        if(cb == null)
        {
            cb = new CatalogueBus();
            return cb;
        }
        else return cb;
    }

    public CatalogueBus(List<Bus> l) 
    {
        this.lesBus= l;
    }
    
    public static List<Bus> getLesBus() 
    {
        return lesBus;
    }

    public void setLesBus(List<Bus> lesBus) 
    {
        this.lesBus = lesBus;
    }
    
    public void addBus(Bus b)
    {
        lesBus.add(b);
    }
}
