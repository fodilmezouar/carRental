/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.historiques;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import locationcar.Modele.Historique;
import locationcar.Modele.Location;
import locationcar.dataBase.DataBase;

/**
 * FXML Controller class
 *
 * @author Fodil
 */
public class HistoriqueController implements Initializable {
    private DataBase base;    private  ObservableList<Historique> list ;
    private boolean listeComplet = false ;
    
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Historique> Table;
    @FXML
    private TableColumn<Historique, Integer> Id_col;
    @FXML
    private TableColumn<Historique, String> Nom_col;
    @FXML
    private TableColumn<Historique, String> chassis_col;
    @FXML
    private TableColumn<Historique, String> Vehicul_col;
    @FXML
    private TableColumn<Historique, String> Date_sortie_col;
    @FXML
    private TableColumn<Historique, String> Date_entrer;
    @FXML
    private TableColumn<Historique, Integer> Hsortie_col;
    @FXML
    private TableColumn<Historique, Integer> Hentrer_col;
    @FXML
    private TableColumn<Historique, String> dateRest_col;
    @FXML
    private TableColumn<Historique, Integer> heureRest_col;
    @FXML
    private TableColumn<Historique, Integer> tarifSupp_col;
    @FXML
    private TableColumn<Historique, Integer> montant_col;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        base  = new DataBase();
        initColumn();
        loadData();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchFonction();
               });
    }    
     private void initColumn() {
        Id_col.setCellValueFactory(new PropertyValueFactory<>("IDHistorique"));
        Nom_col.setCellValueFactory(new PropertyValueFactory<>("NomClient"));
        Date_entrer.setCellValueFactory(new PropertyValueFactory<>("DateEntree"));
        Date_sortie_col.setCellValueFactory(new PropertyValueFactory<>("DateSortie"));
        Hentrer_col.setCellValueFactory(new PropertyValueFactory<>("HeureEntree"));
        Hsortie_col.setCellValueFactory(new PropertyValueFactory<>("HeureSortie"));
        heureRest_col.setCellValueFactory(new PropertyValueFactory<>("HeureRestitution"));
        dateRest_col.setCellValueFactory(new PropertyValueFactory<>("DateRestitution"));
        Vehicul_col.setCellValueFactory(new PropertyValueFactory<>("ModeleVehicule"));
        tarifSupp_col.setCellValueFactory(new PropertyValueFactory<>("TarifSupp"));
        montant_col.setCellValueFactory(new PropertyValueFactory<>("MontantApaye"));
        chassis_col.setCellValueFactory(new PropertyValueFactory<>("NumChassis"));
        
    }
      public void loadData() {
      
        list=FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT * FROM Historique";
        try {
            
            ResultSet rs = base.execQuery(qu);
            
            while(rs.next()){
                int IDHistorique = rs.getInt("IDHistorique");
                String NomClient = rs.getString("NomClient");
                String NumChassis = rs.getString("NumChassis");
                String ModeleVehicule = rs.getString("ModeleVehicule");
                String DateSortie = rs.getString("DateSortie");
                String DateEntree = rs.getString("DateEntree");
                String HeureSortie = rs.getString("HeureSortie");
                String HeureEntree = rs.getString("HeureEntree");
                String DateRestitution = rs.getString("DateRestitution");
                String HeureRestitution = rs.getString("HeureRestitution");
                int TarifSupp = rs.getInt("TarifSupp");
                int MontantApaye = rs.getInt("MontantApaye");
                list.add(new Historique(IDHistorique, NomClient, NumChassis,
                ModeleVehicule,DateSortie,DateEntree,HeureSortie,HeureEntree,DateRestitution,HeureRestitution,TarifSupp,MontantApaye));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(HistoriqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table.setItems(null);
        Table.setItems(list);
        
    }
    public void searchFonction() {
        String search = searchField.getText();
        list=FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT * FROM Historique WHERE  NomClient like '%"+search+"%' OR NumChassis like '%"+search+"%'"
                + "OR ModeleVehicule like '%"+search+"%'";
        try {
            
            ResultSet rs = base.execQuery(qu);
            
            while(rs.next()){
                int IDHistorique = rs.getInt("IDHistorique");
                String NomClient = rs.getString("NomClient");
                String NumChassis = rs.getString("NumChassis");
                String ModeleVehicule = rs.getString("ModeleVehicule");
                String DateSortie = rs.getString("DateSortie");
                String DateEntree = rs.getString("DateEntree");
                String HeureSortie = rs.getString("HeureSortie");
                String HeureEntree = rs.getString("HeureEntree");
                String DateRestitution = rs.getString("DateRestitution");
                String HeureRestitution = rs.getString("HeureRestitution");
                int TarifSupp = rs.getInt("TarifSupp");
                int MontantApaye = rs.getInt("MontantApaye");
                list.add(new Historique(IDHistorique, NomClient, NumChassis,
                ModeleVehicule,DateSortie,DateEntree,HeureSortie,HeureEntree,DateRestitution,HeureRestitution,TarifSupp,MontantApaye));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(HistoriqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table.setItems(null);
        Table.setItems(list);
        
    }
}
