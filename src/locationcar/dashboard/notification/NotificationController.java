/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.dashboard.notification;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import locationcar.Modele.Location;
import locationcar.Modele.Vehicule;
import locationcar.dataBase.DataBase;
/**
 * FXML Controller class
 *
 * @author Fodil
 */
public class NotificationController implements Initializable {

    private DataBase base;
    private  ObservableList<Location> list ;
    private  ObservableList<Vehicule> listVehicule ;

    @FXML
    private TableView<Location> Table;
    @FXML
    private TableColumn<Location, Integer> Id_col;

    @FXML
    private TableColumn<Location, String> Nom_col;

    @FXML
    private TableColumn<Location, String> Vehicul_col;

    @FXML
    private TableColumn<Location, String> Date_sortie_col;

    @FXML
    private TableColumn<Location, String> Nombrejr_col;

    @FXML
    private TableColumn<Location, String> Date_entrer;

    @FXML
    private TableColumn<Location, String> Hsortie_col;

    @FXML
    private TableColumn<Location, String> Hentrer_col;

    @FXML
    private TableColumn<Location, String> Montant_col;
   
    @FXML
    private TableColumn<Vehicule, Integer> IDColumn;
    @FXML
    private TableColumn<Vehicule, Integer> numChassisColumn;
    @FXML
    private TableColumn<Vehicule, String> matriculeColumn;
    @FXML
    private TableColumn<Vehicule, String> marqueColumn;
    @FXML
    private TableColumn<Vehicule, String> modeleColumn;
    @FXML
    private TableColumn<Vehicule, Integer> kilometrageColumn;
    @FXML
    private TableColumn<Vehicule, Integer> carburantColumn;
    @FXML
    private TableColumn<Vehicule,String> statutColumn;
    @FXML
    private TableView<Vehicule> vehiculeTable;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        base = new DataBase();
        initColumn();
        initColumnCharge();
        loadData();
        loadDataCharge();
      
    } 
    
    private void initColumn() {
        Id_col.setCellValueFactory(new PropertyValueFactory<>("IDLocation"));
        Nom_col.setCellValueFactory(new PropertyValueFactory<>("NomClient"));
        Date_entrer.setCellValueFactory(new PropertyValueFactory<>("dateEntree"));
        Date_sortie_col.setCellValueFactory(new PropertyValueFactory<>("dateSortie"));
        Hentrer_col.setCellValueFactory(new PropertyValueFactory<>("heureEntree"));
        Hsortie_col.setCellValueFactory(new PropertyValueFactory<>("heureSortie"));
        Nombrejr_col.setCellValueFactory(new PropertyValueFactory<>("nbrJours"));
        Montant_col.setCellValueFactory(new PropertyValueFactory<>("montantApayer"));
        Vehicul_col.setCellValueFactory(new PropertyValueFactory<>("NomVehicule"));
        
    }
    private void initColumnCharge() {
        
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("IDVehicule"));
        marqueColumn.setCellValueFactory(new PropertyValueFactory<>("nameMarque"));
        numChassisColumn.setCellValueFactory(new PropertyValueFactory<>("numChassis"));
        matriculeColumn.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        kilometrageColumn.setCellValueFactory(new PropertyValueFactory<>("kilometrage"));
        carburantColumn.setCellValueFactory(new PropertyValueFactory<>("carburantActuel"));
        modeleColumn.setCellValueFactory(new PropertyValueFactory<>("modele"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        
        
    }
    public void loadData() {
      
        list=FXCollections.observableArrayList();
        list.clear();
        String qu = "select * from Location where DateEntree < current_date";
        try {
            
            ResultSet rs = base.execQuery(qu);
            
            while(rs.next()){
                int idLocation = rs.getInt("IDLocation");
                int idVehicule = rs.getInt("IDVehicule");
                int idClient = rs.getInt("IDClient");
                String dateSortie = rs.getString("DateSortie");
                String DateEntree = rs.getString("DateEntree");
                int NombreJour = rs.getInt("NombreJours");
                String HeureEntree = rs.getString("HeureEntree");
                String HeureSortie = rs.getString("HeureSortie");
                int TarifSupp = rs.getInt("TarifSupp");
                int MontantApaye = rs.getInt("MontantApaye");
                int tva = rs.getInt("Tva");
                int remise = rs.getInt("remise");
                String modele = base.getModeleVehicule(idVehicule);
                String fullname = base.getFullname(idClient);
                list.add(new Location(idLocation, fullname, modele,
                dateSortie,NombreJour,DateEntree,HeureSortie,HeureEntree,MontantApaye,TarifSupp,tva,remise));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(NotificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table.setItems(null);
        Table.setItems(list);
        
    }
    private void loadDataCharge () {
      
        listVehicule=FXCollections.observableArrayList();
        listVehicule.clear();
        String qu = "select IDVehicule,IDMarque,NumChassis,DateAssur,ProchAssur,DateScanner,ProchScanner"
                + ", Kilometrage,ProchVidange,modele,remarques"
                + ",derniereChaine,ProchChaine,tarifJour"
                + ",julianday(ProchScanner)-julianday() as ScannerNoti,julianday(ProchAssur)-julianday() as AssuranceNoti "
                + ",ProchVidange - kilometrage as VidangeNoti,ProchChaine - kilometrage as ChaineNoti from Vehicule where julianday(ProchAssur)-julianday()<10 or"
                + " julianday(ProchScanner)-julianday()<10 or (ProchVidange - kilometrage <500 and ProchVidange>0) or (ProchChaine - kilometrage <500 and ProchChaine>0)";
        try {
            
            try (ResultSet rs = base.execQuery(qu)) {
                while(rs.next()){
                    int IDVehicule = rs.getInt("IDVehicule");
                    int IDMarque = rs.getInt("IDMarque");
                    int nombreAssurance = rs.getInt("AssuranceNoti");
                    int nombreScanner = rs.getInt("ScannerNoti");
                    int nombreVidange = rs.getInt("VidangeNoti");
                    int nombreChaine = rs.getInt("ChaineNoti");
                    String NumChassis = rs.getString("NumChassis");
                    
                    String DateAssur = rs.getString("DateAssur");
                    String ProchAssur = rs.getString("ProchAssur");
                    String DateScanner = rs.getString("DateScanner");
                    String ProchScanner = rs.getString("ProchScanner");
                    int Kilometrage = rs.getInt("Kilometrage");
                    int ProchVidange = rs.getInt("ProchVidange");
                    
                    String modele = rs.getString("modele");
                    String remarques = rs.getString("remarques");
                    int derniereChaine = rs.getInt("derniereChaine");
                    int ProchChaine = rs.getInt("ProchChaine");
                    int tarifJour = rs.getInt("tarifJour");
                    String vidangeNoti ="";
                    String assuranceDescription = "";
                    String scannerNoti = "";
                    String chaineDisNoti = "";
                    if (nombreAssurance<0) {
                        assuranceDescription = "Retard de "+(nombreAssurance*-1)+ " Jours";
                    }else{
                        assuranceDescription = "État Normal";
                        if (nombreAssurance<10)        
                        assuranceDescription+=","+(nombreAssurance)+" Jours Restants";        
                                
                               
                    }
                    if (nombreScanner<0) {
                        scannerNoti = "Retard de "+(nombreScanner*-1)+" Jours";
                    }else{
                        scannerNoti = "État Normal";
                        if (nombreScanner<10)        
                        scannerNoti+=","+(nombreScanner)+" Jours Restants";
                    }
                    if (nombreChaine<0) {
                        chaineDisNoti = "Retard de "+(nombreChaine*-1)+" KM";
                    }else{
                        chaineDisNoti = "État Normal";
                        if (nombreChaine<500)
                         chaineDisNoti+=","+(nombreChaine)+" KM Restants";

                    }
                    if (nombreVidange<0) {
                        vidangeNoti = "Retard de "+(nombreVidange*-1)+" KM";
                    }else{
                        vidangeNoti = "État Normal";
                        if (nombreVidange<500)
                         vidangeNoti+=","+(nombreVidange)+" KM Restants";
                    }
                    
                    
                    
                    listVehicule.add(new Vehicule(IDVehicule, IDMarque,assuranceDescription, NumChassis, scannerNoti, DateAssur, ProchAssur,DateScanner,ProchScanner,
                            Kilometrage,ProchVidange,vidangeNoti,modele,remarques,derniereChaine,ProchChaine,tarifJour,chaineDisNoti));
                }
                rs.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(NotificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        vehiculeTable.setItems(null);
        vehiculeTable.setItems(listVehicule);
        
        
    }
    
     public int nbrNotif() {
        int i = 0;
        base = new DataBase();
        Date cejour = new Date();
        String query = "select IDLocation from Location where DateEntree < current_date " ;
        String queryAssurance = "select * from Vehicule where julianday(ProchAssur)-julianday()<10 or julianday(ProchScanner)-julianday()<10 or (ProchVidange - kilometrage <500 and ProchVidange>0) or (ProchChaine - kilometrage <500 and ProchChaine>0)" ;
        try {
            ResultSet rs = base.execQuery(query);
            ResultSet rsAss = base.execQuery(queryAssurance);

            while (rs.next()) {
           
                        i++;
                    }  
            while (rsAss.next()) {                
                i++;
            }
            rs.close();
        } catch (Exception e) {
            System.err.printf("Erruer" + e);
        }
        return i;
    }

 
}
