/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.vehicules;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import locationcar.Alert.ClassAlert;
import locationcar.Modele.Client;
import locationcar.Modele.Charge;
import locationcar.Modele.Vehicule;
import locationcar.clients.ClientsController;
import locationcar.clients.ModifierClientController;
import locationcar.dataBase.DataBase;

/**
 * FXML Controller class
 *
 * @author Fodil
 */
public class VehiculesController implements Initializable {
    private DataBase Bdd;
    private  ObservableList<Vehicule> list ;
    private  ObservableList<Charge> listCharge ;
    private boolean listeComplet = false ;
    @FXML
    private ComboBox<String> marque;
    @FXML
    private TextField numChassis;
    @FXML
    private TextField matricule;
    @FXML

    private TextField modele;
    @FXML
    private TextField carburantActuel;
    @FXML
    private TextField KiloActuel;
    @FXML
    private TextField prochVidange;
    @FXML
    private DatePicker lastAssurance;
    @FXML
    private DatePicker nextAssurance;
    @FXML
    private DatePicker lastScanner;
    @FXML
    private DatePicker nextScanner;
    @FXML
    private TextArea remarques;
     @FXML
    private TextField lastChaine;
    @FXML
    private TextField nextChaine;
    @FXML
    private TextField tarifJour;
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
    private TableView<Vehicule> vehiculeTable;
    @FXML
    private TextField searchVehicules;
    @FXML
    private ComboBox<String> filterByMarque;
    @FXML
    private TableColumn<Vehicule,String> statutColumn;
    @FXML
    private TextField searchVehiculesMaint;
    @FXML
    private ComboBox<String> filterByType;
    @FXML
    private TableView<Charge> Maintenance_table;
    @FXML
    private TableColumn<Charge, Integer> numChassisColumnM;
    @FXML
    private TableColumn<Charge, String> matriculeColumnM;
    @FXML
    private TableColumn<Charge, String> modeleColumnM;
    @FXML
    private TableColumn<Charge, String> TypeCharge_col;
    @FXML
    private TableColumn<Charge, String> Descerption_col;
    @FXML
    private TableColumn<Charge, Integer> Depesnes_col;
     @FXML
    private TableColumn<Charge, String> DateCharge_col;
    @FXML
    private MenuItem panneMenu;
    @FXML
    private ContextMenu context;
   
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bdd = new DataBase();
        intialiserType();
        loadData();
        loadDataCharge();
        remplirMarques();
        remplirMarqueFilter();
        initColumn();
        initColumnCharge();
            searchVehicules.textProperty().addListener((observable, oldValue, newValue) -> {
                searchFonction();
               });
             filterByMarque.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
                                 searchFonction();
               });
             searchVehiculesMaint.textProperty().addListener((observable, oldValue, newValue) -> {
                SearchFonctionCharge();
               });
             filterByType.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
                                 SearchFonctionCharge();
               });
             
    }   
    
     private void initColumnCharge() {
        numChassisColumnM.setCellValueFactory(new PropertyValueFactory<>("NumChassis"));
        matriculeColumnM.setCellValueFactory(new PropertyValueFactory<>("Matricule"));
        modeleColumnM.setCellValueFactory(new PropertyValueFactory<>("ModeleVehicule"));
        DateCharge_col.setCellValueFactory(new PropertyValueFactory<>("DateCharge"));
        TypeCharge_col.setCellValueFactory(new PropertyValueFactory<>("TypeCharge"));
        Descerption_col.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Depesnes_col.setCellValueFactory(new PropertyValueFactory<>("Depense"));
    }
    @FXML
    private void saveVehiculeAction(ActionEvent event) {
             if (dataIsEmpty()== true) {
            ClassAlert.errorAlert("Veuillez saisir les données obligatoires !", "Erreur de saisie"); 
        }else
        {
       
            try {
                
                String matricule = this.matricule.getText();
                String modele = this.modele.getText();
                String carburantActuel = this.carburantActuel.getText();
                Integer KiloActuel=null;
                String proch_vid = prochVidange.getText();
                 String last_chaine = lastChaine.getText();
                String next_chaine = nextChaine.getText();
                 if(!this.KiloActuel.getText().isEmpty()){
                try {
                    KiloActuel = Integer.parseInt(this.KiloActuel.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {kilométrage actuel (KM)} correctement !", "Erreur de saisie");

                    }
            }
                Integer ProchVidange = null;
                 if(!this.prochVidange.getText().isEmpty()){
                try {
                    ProchVidange = Integer.parseInt(this.prochVidange.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {Prochaine vidange (KM)} correctement !", "Erreur de saisie");

                    }
                }
                
                Integer lastChaine = null;
                if(!this.lastChaine.getText().isEmpty()){
 
                try {
                lastChaine = Integer.parseInt(this.lastChaine.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {Chaine de distribution (KM)} correctement !", "Erreur de saisie"); 

                    }
                }
                Integer nextChaine = null;
                   if(!this.nextChaine.getText().isEmpty()){
  
                try {
                    nextChaine = Integer.parseInt(this.nextChaine.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {Prochain changement (KM)} correctement !", "Erreur de saisie");  
                    }
            }

                Integer tarifJour = null;
                 try {
                 tarifJour = Integer.parseInt(this.tarifJour.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {Tarif du jour (DA) (*)} correctement !", "Erreur de saisie"); 
;
                    }
                 
                LocalDate lastAssurance = this.lastAssurance.getValue();
                LocalDate nextAssurance = this.nextAssurance.getValue();
                LocalDate lastScanner = this.lastScanner.getValue();
                LocalDate nextScanner = this.nextScanner.getValue();
                if(lastAssurance!=null && nextAssurance!=null){
                    long daysBetween = ChronoUnit.DAYS.between(lastAssurance,nextAssurance );
                    if(daysBetween<0){
                    ClassAlert.errorAlert("{Date prochaine assurance} doit être supérieure à {Date derniére assurance}!", "Erreur de saisie"); 
                    return;
                    }
                } 
                if(lastScanner!=null && nextScanner!=null){
                    long daysBetween = ChronoUnit.DAYS.between(lastScanner,nextScanner );
                    if(daysBetween<0){
                    ClassAlert.errorAlert("{Date prochain scanner} doit être supérieure à {Date dernier scanner}!", "Erreur de saisie"); 
                    return;
                    }
                }
                if(ProchVidange!=null){
                    if(ProchVidange<KiloActuel){
                    ClassAlert.errorAlert("{Prochaine vidange (KM)} doit être supérieur à {Kilometrage actuel(*)}!", "Erreur de saisie"); 
                    return;
                    }
                }
                if(lastChaine!=null && nextChaine!=null){
                   if(nextChaine<lastChaine){
                    ClassAlert.errorAlert("{Prochain changement (KM)} doit être supérieur à {Chaine de distrubution(KM)}!", "Erreur de saisie"); 
                    return;
                    }
                }
                String remarques = this.remarques.getText();
                String selectedMarque = marque.getSelectionModel().getSelectedItem().toString();

                String sql =  "INSERT INTO Vehicule (NumChassis,Matricule,DateAssur,ProchAssur,DateScanner,ProchScanner,Kilometrage,ProchVidange,NiveauCarburant"
                        + ",modele,remarques,IDMarque,derniereChaine,ProchChaine,tarifJour) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                PreparedStatement ps = Bdd.prepareStat(sql);
                ps.setString(1, numChassis.getText());
                ps.setString(2, matricule);
                if(lastAssurance!=null)
                ps.setString(3,Date.valueOf(lastAssurance).toString());
                else
                    ps.setString(3,null);
                if(nextAssurance!=null)
                ps.setString(4,Date.valueOf(nextAssurance).toString());
                else
                    ps.setString(4,null);
                if(lastScanner!=null)
                ps.setString(5,Date.valueOf(lastScanner).toString());
                else
                    ps.setString(5,null);
                if(nextScanner!=null)
                ps.setString(6,Date.valueOf(nextScanner).toString());
                else
                    ps.setString(6,null);
                if(!this.KiloActuel.getText().isEmpty())
                ps.setInt(7, Integer.parseInt(this.KiloActuel.getText()));
                if(!this.prochVidange.getText().isEmpty())
                ps.setInt(8, Integer.parseInt(this.prochVidange.getText()));
                    else
                    ps.setNull(8, java.sql.Types.INTEGER);
                 if(carburantActuel!=null)
                 ps.setString(9,carburantActuel);
                 else
                    ps.setNull(9, java.sql.Types.INTEGER); 
                if(!modele.isEmpty())
                ps.setString(10,modele);
                else
                    ps.setString(10,null);
                if(!remarques.isEmpty())
                ps.setString(11,remarques);
                else
                    ps.setString(11,null);
                int IDMarqueParsed = Bdd.getIDMarque(selectedMarque);
                ps.setInt(12,IDMarqueParsed);
                if(!last_chaine.isEmpty())
                ps.setInt(13,lastChaine);
                else
                    ps.setNull(13, java.sql.Types.INTEGER);
               if(!next_chaine.isEmpty())
                 ps.setInt(14,nextChaine);
               else
                    ps.setNull(14, java.sql.Types.INTEGER);
                if(!this.tarifJour.getText().isEmpty())
                ps.setInt(15, Integer.parseInt(this.tarifJour.getText()));
                 Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirm.setTitle("Confiramtion dialog");
                alertConfirm.setHeaderText(" Confirmation d'ajout ! ");
                alertConfirm.setContentText("êtes-vous sûr ?");
                Optional<ButtonType> result = alertConfirm.showAndWait();
                if (result.get() == ButtonType.OK){
                    int ex = ps.executeUpdate();
                    if (ex>0) {
                        ClassAlert.infoAlert("Vous avez ajouté le vehicule avec succès !", "Succès");
                        reset();
                        loadData();
                    }else{
                        ClassAlert.infoAlert("Erreur dans la base de données !", "Erreur");
                    }

        }} catch (SQLException ex) {
                System.out.print("Erreur de saisie"); 
            }
     }
 }
    public void reset(){
    ArrayList<TextField> tflist = new ArrayList<TextField>();
        tflist.add(numChassis);
        tflist.add(matricule);
        tflist.add(modele);
        tflist.add(carburantActuel);
        tflist.add(KiloActuel);
        tflist.add(prochVidange);
        tflist.add(lastChaine);
        tflist.add(nextChaine);
        tflist.add(tarifJour);
        remarques.clear();
        lastAssurance.setValue(null);
        nextAssurance.setValue(null);
        lastScanner.setValue(null);
        nextScanner.setValue(null);
        marque.getSelectionModel().selectFirst();
        for(TextField tf:tflist){
            tf.clear();
        }}
    @FXML
    private void resetAction(ActionEvent event) {
        reset();
    }

    @FXML
    private void addNewMarque(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/locationcar/view/marques.fxml"));
            Parent parent = loader.load();
            MarquesController controler = (MarquesController)loader.getController();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Marques");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding( eventt -> {
                loadData();
                remplirMarques();
                marque.getSelectionModel().selectLast();
                
                    ;} );
        } catch (IOException ex) {
            Logger.getLogger(VehiculesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private boolean dataIsEmpty(){
        return  marque.getSelectionModel().getSelectedIndex()==0 || numChassis.getText().isEmpty()|| matricule.getText().isEmpty()
                || modele.getText().isEmpty()|| carburantActuel.getText().isEmpty()|| KiloActuel.getText().isEmpty()| tarifJour.getText().isEmpty();
  
    }
    private void remplirMarques(){
        marque.getItems().clear();
        ObservableList<String>  list0 = FXCollections.observableArrayList();
        list0.add("Sélectionner une marque");
        String qu = "select * from Marque";
           ResultSet rs = Bdd.execQuery(qu);
        try {
            while(rs.next()){
                list0.add(rs.getString("Constructeur"));

                        }
            rs.close();
        } catch (SQLException ex) {
                Logger.getLogger(VehiculesController.class.getName()).log(Level.SEVERE, null, ex);
        }
                 marque.setItems(list0);
                 marque.setValue( marque.getItems().get(0));
    }
    private void remplirMarqueFilter(){
        filterByMarque.getItems().clear();
        ObservableList<String>  list0 = FXCollections.observableArrayList();
        list0.add("Toutes les marques");
        String qu = "select * from Marque";
           ResultSet rs = Bdd.execQuery(qu);
        try {
            while(rs.next()){
                list0.add(rs.getString("Constructeur"));

                        }
            rs.close();
        } catch (SQLException ex) {
                Logger.getLogger(VehiculesController.class.getName()).log(Level.SEVERE, null, ex);
        }
                 filterByMarque.setItems(list0);
                 filterByMarque.setValue( filterByMarque.getItems().get(0));
    }
  
    private String getNameMarque(int id){
         String qu = "select Constructeur from Marque where IDMarque = "+id+"";
         String nameReturned = null;
           ResultSet rs = Bdd.execQuery(qu);
        try {
            while(rs.next()){
                nameReturned = rs.getString("Constructeur");

                        }
            rs.close();
        } catch (SQLException ex) {
                Logger.getLogger(VehiculesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nameReturned;
    }
    
    
    
    
    
    private void initColumn() {
        
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
        String qu = "SELECT * FROM Vehicule";
        try {
            
            ResultSet rs = Bdd.execQuery(qu);
            
            while(rs.next()){
                int IDVehicule = rs.getInt("IDVehicule");
                int IDMarque = rs.getInt("IDMarque");
                String nameMarque = getNameMarque(IDMarque);
                String NumChassis = rs.getString("NumChassis");
                String Matricule = rs.getString("Matricule");
                String DateAssur = rs.getString("DateAssur");
                String ProchAssur = rs.getString("ProchAssur");
                String DateScanner = rs.getString("DateScanner");
                String ProchScanner = rs.getString("ProchScanner");
                int Kilometrage = rs.getInt("Kilometrage");
                int ProchVidange = rs.getInt("ProchVidange");
                String NiveauCarburant = rs.getString("NiveauCarburant");
                String modele = rs.getString("modele");
                String remarques = rs.getString("remarques");
                int derniereChaine = rs.getInt("derniereChaine");
                int ProchChaine = rs.getInt("ProchChaine");
                int tarifJour = rs.getInt("tarifJour");
                int statut = rs.getInt("statut");
                String statutString = null;
                 switch (statut) {
                    case 0:  statutString = "Disponible";
                     break;
                    case 1:  statutString = "Loué";
                     break;
                    case 2:  statutString = "En panne";
                 }

                list.add(new Vehicule(IDVehicule, IDMarque,nameMarque, NumChassis, Matricule, DateAssur, ProchAssur,DateScanner,ProchScanner,
                Kilometrage,ProchVidange,NiveauCarburant,modele,remarques,derniereChaine,ProchChaine,tarifJour,statutString));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        vehiculeTable.setItems(null);
        vehiculeTable.setItems(list);
        
    }
 
     
    private void searchFonction() {
        String rech = searchVehicules.getText();
        String qu;
        String marque = filterByMarque.getSelectionModel().getSelectedItem().toString();
        int ID = Bdd.getIDMarque(marque);
        if(rech.trim().isEmpty() && marque.equals("Toutes les marques")){
                        listeComplet=true;
                        loadData();

        }
        else{
            if(!marque.equals("Toutes les marques")){ 
            qu = "select * from Vehicule where "
                            + "IDMarque = "+ID+" and  "
                            + "( NumChassis like '%"+rech+"%' OR "
                            + "Matricule like '%"+rech+"%' OR "
                            + "modele like '%"+rech+"%' )";
            } else {
            qu = "select * from Vehicule where "
                            + "( NumChassis like '%"+rech+"%' OR "
                            + "Matricule like '%"+rech+"%' OR "
                            + "modele like '%"+rech+"%' )";}

           ResultSet rs = Bdd.execQuery(qu);
            list.clear();
            try {
           while(rs.next()){
                int IDVehicule = rs.getInt("IDVehicule");
                int IDMarque = rs.getInt("IDMarque");
                String nameMarque = getNameMarque(IDMarque);
                String NumChassis = rs.getString("NumChassis");
                String Matricule = rs.getString("Matricule");
                String DateAssur = rs.getString("DateAssur");
                String ProchAssur = rs.getString("ProchAssur");
                String DateScanner = rs.getString("DateScanner");
                String ProchScanner = rs.getString("ProchScanner");
                int Kilometrage = rs.getInt("Kilometrage");
                int ProchVidange = rs.getInt("ProchVidange");
                String NiveauCarburant = rs.getString("NiveauCarburant");
                String modele = rs.getString("modele");
                String remarques = rs.getString("remarques");
                int derniereChaine = rs.getInt("derniereChaine");
                int ProchChaine = rs.getInt("ProchChaine");
                int tarifJour = rs.getInt("tarifJour");
                int statut = rs.getInt("statut");
                String statutString = null;
                 switch (statut) {
                    case 0:  statutString = "Disponible";
                     break;
                    case 1:  statutString = "Loué";
                     break;
                    case 2:  statutString = "En panne";
                 }

                list.add(new Vehicule(IDVehicule, IDMarque,nameMarque, NumChassis, Matricule, DateAssur, ProchAssur,DateScanner,ProchScanner,
                Kilometrage,ProchVidange,NiveauCarburant,modele,remarques,derniereChaine,ProchChaine,tarifJour,statutString));
            }
           rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        vehiculeTable.setItems(null);
        vehiculeTable.setItems(list);
        }
        
    }
    private void setEnPanne(int id){
        try {
            int stat = Bdd.statVehicule(id);
            System.out.println("HHHHHH"+stat);
            String qu = "UPDATE Vehicule SET statut =";
            if (stat == 0) {
                qu +=2 + " WHERE IDVehicule = "+id;
            }else if (stat == 2){
                qu +=0+" WHERE IDVehicule = "+id;
            }
            System.out.println("AAAAAA"+stat);
            PreparedStatement ps = Bdd.prepareStat(qu);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Erreur§§");
        }
    }

    public void loadDataCharge() {
      
        listCharge=FXCollections.observableArrayList();
        listCharge.clear();
        String qu = "SELECT * FROM Charge";
        try {
            
            ResultSet rs = Bdd.execQuery(qu);
            
            while(rs.next()){
                int IDCharge = rs.getInt("NumCharge");
                int IDVehicule = rs.getInt("IDVehicule");
                String numChassis = Bdd.getNumChassis(IDVehicule);
                String matricule = Bdd.getMatricule(IDVehicule);
                String modele = Bdd.getModeleVehicule(IDVehicule);
                String DateCharge = rs.getString("DateCharge");
                String TypeCharge = rs.getString("TypeCharge");
                String Design = rs.getString("DesignationCharge");
                int Depense = rs.getInt("Depense");
                
               
                

                listCharge.add(new Charge(IDCharge, IDVehicule,numChassis, matricule, modele, DateCharge,TypeCharge,Design,Depense));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Maintenance_table.setItems(null);
        Maintenance_table.setItems(listCharge);
        
    }
    public void SearchFonctionCharge() {
         String qu;
        String search = searchVehiculesMaint.getText();
        int type = filterByType.getSelectionModel().getSelectedIndex();
        String typeValue = filterByType.getSelectionModel().getSelectedItem();
        listCharge=FXCollections.observableArrayList();
        listCharge.clear();
        if(type == 0)
            qu = "SELECT * FROM Charge NATURAL JOIN (select IDVehicule,NumChassis,Matricule,modele from Vehicule) WHERE NumChassis like '%"+search+"%' "
                + " OR Matricule like '%"+search+"%' OR modele like '%"+search+"%';";
        else
            qu = "SELECT * FROM Charge NATURAL JOIN (select IDVehicule,NumChassis,Matricule,modele from Vehicule) WHERE "
                    + "TypeCharge like '"+typeValue+"'"
                    + " AND (NumChassis like '%"+search+"%' "
                + " OR Matricule like '%"+search+"%' OR modele like '%"+search+"%');";
            
        try {
            
            ResultSet rs = Bdd.execQuery(qu);
            
            while(rs.next()){
                int IDCharge = rs.getInt("NumCharge");
                int IDVehicule = rs.getInt("IDVehicule");
                String numChassis = Bdd.getNumChassis(IDVehicule);
                String matricule = Bdd.getMatricule(IDVehicule);
                String modele = Bdd.getModeleVehicule(IDVehicule);
                String DateCharge = rs.getString("DateCharge");
                String TypeCharge = rs.getString("TypeCharge");
                String Design = rs.getString("DesignationCharge");
                int Depense = rs.getInt("Depense");
                
               
                

                listCharge.add(new Charge(IDCharge, IDVehicule,numChassis, matricule, modele, DateCharge,TypeCharge,Design,Depense));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Maintenance_table.setItems(null);
        Maintenance_table.setItems(listCharge);
        
    }
    public void intialiserType(){
    List<String> list = new ArrayList<String>();
        list.add("Tous les types");
        list.add("Assurance");
        list.add("Scanner");
        list.add("Vidange");
        list.add("Chaine distribution");
        list.add("Autres");
        ObservableList obList = FXCollections.observableList(list);
        filterByType.getItems().clear();
        filterByType.setItems(obList);
        filterByType.getSelectionModel().select(0);
    }
    @FXML
    private void mettre_en_panne(ActionEvent event) {
        Vehicule selectedForEdit = vehiculeTable.getSelectionModel().getSelectedItem();
        int stat = Bdd.statVehicule(selectedForEdit.getIDVehicule());
        setEnPanne(selectedForEdit.getIDVehicule());
        loadData();

            
            
        
    }

    @FXML
    private void maintenance(ActionEvent event) {
        try {
            Vehicule selectedForEdit = vehiculeTable.getSelectionModel().getSelectedItem();
            if (selectedForEdit == null) {
                return;
            }
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/locationcar/view/charge.fxml"));
            Parent parent = loader.load();
            ChargeController controler = (ChargeController)loader.getController();
            controler.getDataVehicule(selectedForEdit);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.setTitle("Maintenance Vehicule");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding( eventt -> {loadData();loadDataCharge();} );
        } catch (IOException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

     @FXML
    private void modifier_vehicule(ActionEvent event) {
        try {
            Vehicule selectedForEdit = vehiculeTable.getSelectionModel().getSelectedItem();
            if (selectedForEdit == null) {
                return;
            }
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/locationcar/view/modifierVehicule.fxml"));
            Parent parent = loader.load();
            ModifierVehiculeController controler = (ModifierVehiculeController)loader.getController();
            controler.getDataVehicule(selectedForEdit);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Modifer Vehicule");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding( eventt -> {loadData();loadDataCharge();} );
        } catch (IOException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    @FXML
     private void supprimer(ActionEvent event) {
        Vehicule selectedTodelete = vehiculeTable.getSelectionModel().getSelectedItem();
        int idVehicule = selectedTodelete.getIDVehicule();
        String qu = "DELETE FROM Vehicule WHERE IDVehicule ="+idVehicule ;
        if(!Bdd.getModeleVehicule(idVehicule).equals(1)){
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Confiramtion dialog");
            alertConfirm.setHeaderText(" Confirmation de suppression ! ");
            alertConfirm.setContentText("êtes-vous sûr ?");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK){
            if (Bdd.execAction(qu)) {
                loadData();
                loadDataCharge();
                ClassAlert.infoAlert("Supression avec Succes","Succes");
            }else{
                ClassAlert.infoAlert("Supression avec fail","fail");
            }
        }
            
        }else{
            ClassAlert.warningAlert("voiture n'est pas dispo","!");
        }
    }
    
      
    }


    

