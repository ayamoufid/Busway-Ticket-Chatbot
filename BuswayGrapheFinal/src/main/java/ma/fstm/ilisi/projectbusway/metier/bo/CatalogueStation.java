/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.fstm.ilisi.projectbusway.metier.bo;

import java.util.ArrayList;
import java.util.List;
import ma.fstm.ilisi.projectbusway.metier.dao.StationDAO;

/**
 *
 * @author Aya
 */
public class CatalogueStation 
{
	
    private List<Station> lesStations = new ArrayList();
    private static CatalogueStation cs= null;

    private CatalogueStation() 
    {
        StationDAO sdao = new StationDAO();
        lesStations = sdao.retreive();
    }
    
    public static CatalogueStation getCatalogueStation()
    {
        if(cs == null)
        {
            cs = new CatalogueStation();
            return cs;
        }
        else 
            return cs;
    }

    public CatalogueStation(List<Station> l) 
    {
        this.lesStations= l;
    }
    
    public List<Station> getLesStations() {
        return lesStations;
    }

    public void setLesStations(List<Station> s) {
        this.lesStations = s;
    }
    
    public void addStation(Station s)
    {
        lesStations.add(s);
    }
}
