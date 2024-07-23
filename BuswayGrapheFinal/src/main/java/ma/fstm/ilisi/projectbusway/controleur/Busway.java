/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ma.fstm.ilisi.projectbusway.controleur;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static ma.fstm.ilisi.projectbusway.metier.bo.Passager.PassagerexisteDeja;
import ma.fstm.ilisi.projectbusway.metier.bo.*;
import ma.fstm.ilisi.projectbusway.presentation.BusDispo;
import static ma.fstm.ilisi.projectbusway.metier.bo.Arret.*;
import ma.fstm.ilisi.projectbusway.metier.dao.PassagerDAO;
import ma.fstm.ilisi.projectbusway.metier.dao.ReservationDAO;

/**
 *
 * @author Aya
 */
public class Busway {

    private static final Random random = new Random();
    private static final String API_KEY = "4637c825013b426fb25eee1947afb244";
    public Busway(){
        
    }
    
    public static List<Voyage> trouverLesVoyages(Station from, Station to, Date heureDep) 
    {
       List<Voyage> voyages = Voyage.ObtenirListeVoyages();
       List<Voyage> voyagestrouves = new ArrayList();
       for (Voyage voyage : voyages) 
       {
           if (voyage.getLeDepart().getNomStation().equals(from.getNomStation()) && voyage.getlArrivee().getNomStation().equals(to.getNomStation()) &&
               voyage.getDateDepart().equals(heureDep)) 
                 voyagestrouves.add(voyage);
       }
       return voyagestrouves;
    }
    
    public static Voyage trouverVoyage(Station from, Station to, Date heureDep) 
    {
       List<Voyage> voyages = Voyage.ObtenirListeVoyages();
       for (Voyage voyage : voyages) 
       {
           System.out.println(voyage.getLeDepart().getNomStation());
           System.out.println(voyage.getlArrivee().getNomStation());
           if (voyage.getLeDepart() != null && voyage.getLeDepart().getNomStation().equals(from.getNomStation()) && voyage.getlArrivee() != null && voyage.getlArrivee().getNomStation().equals(to.getNomStation()) &&
               voyage.getDateDepart().equals(heureDep) ) 
               return voyage;
       }
       return null;
    }
    
    
    public static Voyage trouverVoyage(Station from) 
    {
       List<Voyage> voyages = Voyage.ObtenirListeVoyages();
       for (Voyage voyage : voyages) 
       {
           // il faut ajouter aussi les arrets
           if (voyage.getLeDepart() != null && voyage.getLeDepart().getNomStation().equals(from.getNomStation())) 
               return voyage;
       }
       return null;
    }
     
    
    public static Station trouverStation(String nom) 
    {
        CatalogueStation s = CatalogueStation.getCatalogueStation();
        List<Station> stations = s.getLesStations();
        for (Station station : stations) 
        {
            if (station.getNomStation().equals(nom)) 
                return station;
        }
        return null;
    }
        
    
    public static boolean reserver(String from, String to, Time heureDep) 
    {
        Station sfrom = trouverStation(from);
        Station sto = trouverStation(to);
        Voyage voyage = trouverVoyage(sfrom, sto, heureDep);
        if (voyage != null) 
        {
            Bus bus = voyage.getBus();
            int placesLibres = bus.getNumeroPlacesLibres();
            if (placesLibres > 0) 
            {
                Reservation reservation = new Reservation();
                reservation.setHeureReservation(heureDep);
                reservation.setStationFrom(sfrom);
                voyage.addReservation(reservation);
                bus.setNumeroPlacesLibres(placesLibres - 1);
                return true;
            } 
            else    return false;
        } 
        else    return false;
    }
    
    
    public static boolean reserver(Voyage voyage) 
    {   
        Bus bus = voyage.getBus();
        int placesLibres = bus.getNumeroPlacesLibres();
        if (placesLibres > 0) 
        {
            //Passager p = new Passager();
            Reservation reservation = new Reservation();
            reservation.setHeureReservation(voyage.getDateDepart());
            reservation.setStationFrom(voyage.getLeDepart());
            //reservation.setPassager(p);
            voyage.addReservation(reservation);
            bus.setNumeroPlacesLibres(placesLibres - 1);
            return true;
        } 
        else    return false;
    }
    
    
    public static boolean reserver(Voyage voyage,String cin ,String nom,Station dep) 
    {   
        Bus bus = voyage.getBus();
        int placesLibres = bus.getNumeroPlacesLibres();
        if (placesLibres > 0) 
        {
            Passager pp = PassagerexisteDeja(cin);
            if(pp == null)  
            {
                pp = new Passager(cin,nom);
                new PassagerDAO().create(pp);
            }             
            if (voyage.getLeDepart().getNomStation().equals(dep.getNomStation()))
            {
                Reservation rs= new Reservation(voyage.getDateDepart(),pp,voyage,dep);
                new ReservationDAO().create(rs);
                voyage.addReservation(rs);
            }
            else
            {
                for (Arret r : arretvoyage1(voyage)) 
                {
                    if (r.getStation().getNomStation().equals(dep.getNomStation())) 
                    {
                        Reservation rs= new Reservation(r.getHeureArret(),pp,voyage,dep);
                        new ReservationDAO().create(rs);
                        voyage.addReservation(rs);
                    }
                }
            }
            bus.setNumeroPlacesLibres(placesLibres - 1);
            return true;
        } 
        else    return false;
    }
    
    public static void passerv(List<Bus> dbus)
    {
        for(Bus b:dbus)
         for (Voyage v :b.getLesVoyages() ) 
            System.out.println("id "+v.getIdVoyage());        
        new BusDispo(dbus);
    }
    
    public static List<Voyage> trouverVoyageLigne(Station f)
    {
        //Arret arretInstance = new Arret();
        Arret.CreerArrets();
        System.out.println("plus proche  "+f.getNomStation());
        System.out.println("Nombre de voyages :  "+Voyage.ObtenirListeVoyages().size());
    	List<Voyage> voyagesp=  new ArrayList();
    	for (Voyage voyage : Voyage.ObtenirListeVoyages()) 
    	{
            if (voyage.getLeDepart().getNomStation().equals(f.getNomStation())) 
            {
                 voyagesp.add(voyage);
                System.out.println("fond");
            }
            else
            {
                for (Arret r : arretvoyage1(voyage)) 
                {
                    if (r.getStation().getNomStation().equals(f.getNomStation()))
                    {
                        voyagesp.add(voyage);
                        System.out.println("found");
                        break;
                    }
                }
            }
    	}
	return voyagesp;
    }
    
    
  

 public static List<Station> arretvoyage(Voyage v) {
        List<Station> st = new ArrayList<>();
        for (List<Arret> vararr : new List[]{lesarrets}) {
            for (Arret arret : vararr) {
                // Ajouter l'arrêt à la liste des stations
                if(arret.getVoyage().getIdVoyage()==v.getIdVoyage())
                st.add(arret.getStation());
                
                else break;
            }
        }
        return st;
    }
    

 
  public static List<Arret> arretvoyage1(Voyage v) {
	  
        List<Arret> st = new ArrayList<>();
        for (Arret vararr : Arret.lesarrets)
        {
        	  if(vararr.getVoyage().getIdVoyage()==v.getIdVoyage())
               st.add(vararr);
        }
        return st;
    }
    
   
    public static void createQRCode(String qrCodeData, String filePath, String charset, Hashtable hintMap, int qrCodeheight, int qrCodewidth)
            throws WriterException, IOException 
    {
        BitMatrix matrix = new QRCodeWriter().encode(new String(qrCodeData.getBytes(charset), charset), BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
        MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));
    }
    
    
    public static BufferedImage generateQRCode(String content) {
        try {
            // Utiliser la bibliothèque ZXing pour générer le QR code
            MultiFormatWriter writer = new MultiFormatWriter();
            return MatrixToImageWriter.toBufferedImage(writer.encode(content, BarcodeFormat.QR_CODE, 200, 200));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
public static DonneesStationAleatoire genererDonneesAleatoires(
        CatalogueStation catalogueStation,
        int nbPlacesLibres,
        int capaciteMax,
        String heureArriveeStationPrecedente) {

    // Génération d'un nombre aléatoire pour l'indice de la station
    int indiceStationAleatoire = random.nextInt(catalogueStation.getLesStations().size());

    // Récupération de la station aléatoire
    Station stationAleatoire = catalogueStation.getLesStations().get(indiceStationAleatoire);

    // Calcul de l'écart temporel aléatoire
    int ecartTemporel = random.nextInt(15 - 5) + 5;

    // **Ajout de l'heure actuelle à l'écart temporel**
    LocalDateTime dateTimeArriveeStationActuelle = LocalDateTime.now().plusMinutes(ecartTemporel);

    // **Modification du format de l'heure**
    DateTimeFormatter formatterHeure = DateTimeFormatter.ofPattern("HH:mm");
    String dateTimeArriveeStationActuelleString = dateTimeArriveeStationActuelle.format(formatterHeure);

    // Génération du nombre de personnes entrant et sortant aléatoire
    int nbEntreeAleatoire = Math.min(random.nextInt(nbPlacesLibres), nbPlacesLibres); // Limiter l'entrée en fonction des places libres
    int nbSortieAleatoire = Math.min(random.nextInt(capaciteMax - nbPlacesLibres), capaciteMax - nbPlacesLibres); // Limiter la sortie en fonction des places restantes

    return new DonneesStationAleatoire(
            stationAleatoire.getNomStation(),
            dateTimeArriveeStationActuelleString,
            nbEntreeAleatoire,
            nbSortieAleatoire
    );
}


public static BufferedImage generateQrCodeReservation(String numeroBus, String heureReservation, String heureDepart,String nomStation) throws WriterException, IOException {
    // Contenu du QR code
    String content = "NumeroBus:" + numeroBus + "\nHeureReservation:" + heureReservation + "\nHeureDepart:" + heureDepart+ "\nNom de station:" + nomStation;

    // Configuration des paramètres du QR code
    Map<EncodeHintType, Object> hintMap = new HashMap<>();
    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); // Ajuster le niveau de correction d'erreur
    hintMap.put(EncodeHintType.MARGIN, 2); // Ajuster la marge autour du QR code

    // Génération de la matrice de bits du QR code
    BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 200, 200, hintMap);

    // Conversion de la matrice de bits en image
    BufferedImage qrCodeImage = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < matrix.getWidth(); x++) {
        for (int y = 0; y < matrix.getHeight(); y++) {
            qrCodeImage.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
        }
    }

    return qrCodeImage;
}
private static final double EARTH_RADIUS_KM = 6371.0;

public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) 
{
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceKm = EARTH_RADIUS_KM * c;
        return distanceKm;
}



public static String findNearestStation(double userLat, double userLon, List<Station> stations) 
{
        double minDistance = Double.MAX_VALUE;
        String nearestStationName = "";
        for (Station station : stations) 
        {
            double distance = calculateDistance(userLat, userLon, station.getLat(), station.getLon());
            if (distance < minDistance) 
            {
                minDistance = distance;
                nearestStationName = station.getNomStation();
            }
        }
        return nearestStationName;
    }
    

private static String sendHttpRequest(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }


 private static double extractLatitudeFromResponse(String response) {
        try {
        // Recherche de l'indice du champ "lat" dans la réponse JSON
        int latIndex = response.indexOf("\"latitude\":");

        // Si l'indice est trouvé, extrayez la valeur de la latitude
        if (latIndex != -1) {
            int start = latIndex + 12;
            int end = response.indexOf("\",", start); // Fin de la valeur de la latitude
            String latitudeString = response.substring(start, end);
            double latitude = Double.parseDouble(latitudeString);
            return latitude;
        } else {
            System.err.println("Champ \"latitude\" introuvable dans la réponse JSON.");
            return 0.0; // Gérer le cas d'erreur de manière appropriée
        }
    } catch (NumberFormatException e) {
        System.err.println("Erreur de format de nombre : " + e.getMessage());
        return 0.0; // Gérer le cas d'erreur de manière appropriée
    }
    }
 
 
  private static double extractLongitudeFromResponse(String response) {
        try {
        // Recherche de l'indice du champ "lat" dans la réponse JSON
        int latIndex = response.indexOf("\"longitude\":");
        if (latIndex != -1) {
            int start = latIndex + 13;
            int end = response.indexOf("\",", start); // Fin de la valeur de la latitude
            String latitudeString = response.substring(start, end);
            double latitude = Double.parseDouble(latitudeString);
            return latitude;
        } else {
            System.err.println("Champ \"longitude\" introuvable dans la réponse JSON.");
            return 0.0; // Gérer le cas d'erreur de manière appropriée
        }
    } catch (Exception e) {
        e.printStackTrace();
        return 0.0; // Gérer le cas d'erreur de manière appropriée
    }
    }
    public static void main(String[] args ) throws Exception
    {     
        // Sample reservation details
        /*String numeroBus = "123";
        String heureReservation = "10:30";
        String heureDepart = "11:00";
        String nomStation = "Medina";
        try {
            // Generate QR code image
            BufferedImage qrCodeImage = generateQrCodeReservation(numeroBus, heureReservation, heureDepart,nomStation);

            // Create a custom dialog class (assuming you don't have one)
            ReservationDialog dialog = new ReservationDialog(qrCodeImage, numeroBus, heureReservation, heureDepart,nomStation);
            dialog.setVisible(true);
         } catch (WriterException e) {
                // Handle WriterException (e.g., print error message)
                System.err.println("Error generating QR code: " + e.getMessage());
            } catch (IOException e) {
                // Handle IOException (e.g., print error message)
                System.err.println("Error creating QR code image: " + e.getMessage());
            }*/
        
        /*Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez la latitude : ");
        double latitude = scanner.nextDouble();
        System.out.print("Entrez la longitude : ");
        double longitude = scanner.nextDouble();       
        CatalogueStation cs = CatalogueStation.getCatalogueStation();
        List<Station> ls = cs.getLesStations();
        String nom = findNearestStation(latitude,longitude,ls);
        System.out.print("le nom " + nom);*/
        
        
        String userIpAddress = "105.159.156.106"; // Remplacez par l'adresse IP réelle de l'utilisateur
            String apiUrl = "https://api.ipgeolocation.io/ipgeo?apiKey=" + API_KEY + "&ip=" + userIpAddress;

            String response = sendHttpRequest(apiUrl);

            // Analysez la réponse JSON pour extraire la latitude et la longitude
            double userLat = extractLatitudeFromResponse(response);
            double userLon = extractLongitudeFromResponse(response);

            System.out.println("Latitude de l'utilisateur : " + userLat);
            System.out.println("Longitude de l'utilisateur : " + userLon);
            CatalogueStation cs = CatalogueStation.getCatalogueStation();
            List<Station> ls = cs.getLesStations();
            String nom = findNearestStation(userLat,userLon,ls);
            System.out.print("le nom " + nom);
        } 
    
    public static String trouverPlusProche() throws Exception
    {
        String url = "https://api.ipify.org/";
        String responsee = sendHttpRequest(url);
        String userIpAddress = responsee.trim(); 
        String apiUrl = "https://api.ipgeolocation.io/ipgeo?apiKey=" + API_KEY + "&ip=" + userIpAddress;
        String response = sendHttpRequest(apiUrl);
        double userLat = extractLatitudeFromResponse(response);
        double userLon = extractLongitudeFromResponse(response);
        CatalogueStation cs = CatalogueStation.getCatalogueStation();
        List<Station> ls = cs.getLesStations();
        String nom = findNearestStation(userLat,userLon,ls);
        return nom;
    }
        
    }
class DonneesStationAleatoire {

    private String nomStation;
    private String dateTimeArriveeStation;
    private int nbEntree;
    private int nbSortie;

    public DonneesStationAleatoire(
            String nomStation,
            String dateTimeArriveeStation,
            int nbEntree,
            int nbSortie) {
        this.nomStation = nomStation;
        this.dateTimeArriveeStation = dateTimeArriveeStation;
        this.nbEntree = nbEntree;
        this.nbSortie = nbSortie;
    }

    public String getNomStation() {
        return nomStation;
    }

    public void setNomStation(String nomStation) {
        this.nomStation = nomStation;
    }

    public String getDateTimeArriveeStation() {
        return dateTimeArriveeStation;
    }

    public void setDateTimeArriveeStation(String dateTimeArriveeStation) {
        this.dateTimeArriveeStation = dateTimeArriveeStation;
    }

    public int getNbEntree() {
        return nbEntree;
    }

    public void setNbEntree(int nbEntree) {
        this.nbEntree = nbEntree;
    }

    public int getNbSortie() {
        return nbSortie;
    }

    public void setNbSortie(int nbSortie) {
        this.nbSortie = nbSortie;
    }

    @Override
    public String toString() {
        return "DonneesStationAleatoire{" +
                "nomStation='" + nomStation + '\'' +
                ", dateTimeArriveeStation='" + dateTimeArriveeStation + '\'' +
                ", nbEntree=" + nbEntree +
                ", nbSortie=" + nbSortie +
                '}';
    }
}

class ReservationDialog extends JDialog {

    public ReservationDialog(BufferedImage qrCodeImage, String numeroBus, String heureReservation, String heureDepart,String nomstation) {
        super();

        // Set dialog title and size
        setTitle("Reservation Details");
        setSize(400, 300);

        // Layout components
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Add QR code image label
        JLabel qrCodeLabel = new JLabel(new ImageIcon(qrCodeImage));
        contentPanel.add(qrCodeLabel, BorderLayout.CENTER);

        // Create a panel for reservation info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.add(new JLabel("Bus Number: " + numeroBus));
        infoPanel.add(new JLabel("Reservation Time: " + heureReservation));
        infoPanel.add(new JLabel("Departure Time: " + heureDepart));
        infoPanel.add(new JLabel("Nom station: " + nomstation));
        contentPanel.add(infoPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);
    }
    
}



