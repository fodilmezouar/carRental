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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import locationcar.Alert.ClassAlert;
import locationcar.Modele.Client;
import locationcar.Modele.Vehicule;
import locationcar.clients.ModifierClientController;
import locationcar.dashboard.notification.NotificationController;
import locationcar.dataBase.DataBase;
import locationcar.locations.LocationsController;

/**
 * FXML Controller class
 *
 * @author computeruser
 */
public class ModifierVehiculeController implements Initializable {
    DataBase bdd;
    @FXML
    private ComboBox<String> marqueEdit;
    @FXML
    private TextField numChassisEdit;
    @FXML
    private TextField matriculeEdit;
    @FXML
    private TextField modeleEdit;
    @FXML
    private TextField carburantActuelEdit;
    @FXML
    private TextField KiloActuelEdit;
    @FXML
    private TextField prochVidangeEdit;
    @FXML
    private DatePicker lastAssuranceEdit;
    @FXML
    private DatePicker nextAssuranceEdit;
    @FXML
    private DatePicker lastScannerEdit;
    @FXML
    private DatePicker nextScannerEdit;
    @FXML
    private TextField lastChaineEdit;
    @FXML
    private TextField nextChaineEdit;
    @FXML
    private TextField tarifJourEdit;
    @FXML
    private TextArea remarquesEdit;
    int idVehicule;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bdd = new DataBase();
        remplirMarques();
    }

  

public void getDataVehicule( Vehicule vh){
           idVehicule = vh.getIDVehicule();
            
            String marque = vh.getNameMarque();
            String numeroChassis = vh.getNumChassis();
            String matricule = vh.getMatricule();
            String modele = vh.getModele();
            String carburantActu = vh.getCarburantActuel();
            int kiloActuel = vh.getKilometrage();
            int prochVidange = vh.getProchVidange();
            String lastAssur = vh.getLastAssur();
            String prochAssur = vh.getNextAssur();
            String lastScanner = vh.getLastScanner();
            String prochScanner = vh.getNextScanner();
            int lastChaine = vh.getLastChaine();
             int prochChaine = vh.getProchChaine();
             int tarifJour = vh.getTarifJour();
             String remarques = vh.getRemarques();
             this.marqueEdit.setValue(marque);
             this.numChassisEdit.setText(numeroChassis+"");
             this.matriculeEdit.setText(matricule);
             this.modeleEdit.setText(modele);
             this.carburantActuelEdit.setText(carburantActu);
             this.KiloActuelEdit.setText(kiloActuel+"");
             this.prochVidangeEdit.setText(prochVidange+"");
             this.lastChaineEdit.setText(lastChaine+"");
             this.nextChaineEdit.setText(prochChaine+"");
             this.tarifJourEdit.setText(tarifJour+"");
             this.remarquesEdit.setText(remarques);
            if (lastAssur!= null) {
            LocalDate last_assur = LocationsController.LOCAL_DATE(lastAssur);
            this.lastAssuranceEdit.setValue(last_assur);
            }
             if (prochAssur!= null) {
            LocalDate proch_assur = LocationsController.LOCAL_DATE(prochAssur);
            this.nextAssuranceEdit.setValue(proch_assur);
            } 
             if (lastScanner!= null) {
            LocalDate last_scanner = LocationsController.LOCAL_DATE(lastScanner);
            this.lastScannerEdit.setValue(last_scanner);
            }
              if (prochScanner!= null) {
            LocalDate proch_scanner = LocationsController.LOCAL_DATE(prochScanner);
            this.nextScannerEdit.setValue(proch_scanner);
            }
    }
    private void remplirMarques(){
        marqueEdit.getItems().clear();
        ObservableList<String>  list0 = FXCollections.observableArrayList();
        String qu = "select * from Marque";
           ResultSet rs = bdd.execQuery(qu);
        try {
            while(rs.next()){
                list0.add(rs.getString("Constructeur"));

                        }
            rs.close();
        } catch (SQLException ex) {
                Logger.getLogger(VehiculesController.class.getName()).log(Level.SEVERE, null, ex);
        }
                 marqueEdit.setItems(list0);
    }

    @FXML
    private void addNewMarque(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/locationcar/vehicules/marques.fxml"));
            Parent parent = loader.load();
            MarquesController controler = (MarquesController)loader.getController();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Marques");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding( eventt -> {
                remplirMarques();
                marqueEdit.getSelectionModel().selectLast();
                    ;} );
        } catch (IOException ex) {
            Logger.getLogger(ModifierVehiculeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void resetActionEdit(ActionEvent event) {
    }
    private boolean dataIsEmpty(){
        return  marqueEdit.getSelectionModel().isEmpty() || numChassisEdit.getText().isEmpty()|| matriculeEdit.getText().isEmpty()
                || modeleEdit.getText().isEmpty()|| carburantActuelEdit.getText().isEmpty()|| KiloActuelEdit.getText().isEmpty()| tarifJourEdit.getText().isEmpty();
  
    }
    @FXML
    private void editVehiculeAction(ActionEvent event) throws IOException {
             if (dataIsEmpty()== true) {
            ClassAlert.errorAlert("Veuillez saisir les données obligatoires !", "Erreur de saisie"); 
        }else
        {
            String matriculee = this.matriculeEdit.getText();
                String modelee = this.modeleEdit.getText();
                String carburantActuel = this.carburantActuelEdit.getText();
                Integer KiloActuel=null;
                 if(!this.KiloActuelEdit.getText().isEmpty()){
                try {
                    KiloActuel = Integer.parseInt(this.KiloActuelEdit.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {kilométrage actuel (KM)} correctement !", "Erreur de saisie");

                    }
            }
                Integer ProchVidange = null;
                 if(!this.prochVidangeEdit.getText().isEmpty()){
                try {
                    ProchVidange = Integer.parseInt(this.prochVidangeEdit.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {Prochaine vidange (KM)} correctement !", "Erreur de saisie");

                    }
                }
                
                Integer lastChaine = null;
                if(!this.lastChaineEdit.getText().isEmpty()){
 
                try {
                lastChaine = Integer.parseInt(this.lastChaineEdit.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {Chaine de distribution (KM)} correctement !", "Erreur de saisie"); 

                    }
                }
                Integer nextChaine = null;
                   if(!this.nextChaineEdit.getText().isEmpty()){
  
                try {
                    nextChaine = Integer.parseInt(this.nextChaineEdit.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {Prochain changement (KM)} correctement !", "Erreur de saisie");  
                    }
            }

                Integer tarifJour = null;
                 try {
                 tarifJour = Integer.parseInt(this.tarifJourEdit.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {Tarif du jour (DA) (*)} correctement !", "Erreur de saisie"); 
;
                    }
                 
        try{   
        String selectedMarque = marqueEdit.getSelectionModel().getSelectedItem().toString();
            int selectedIDMarque = bdd.getIDMarque(selectedMarque);
            String numChassis = numChassisEdit.getText();
            String matricule = matriculeEdit.getText();
            String modele = modeleEdit.getText();
            String remarques = remarquesEdit.getText();
            String carbActuel = carburantActuelEdit.getText();
            String kiloActuel = KiloActuelEdit.getText();
            String prochVid = prochVidangeEdit.getText();
            String last_chaine = lastChaineEdit.getText();
            String next_chaine = nextChaineEdit.getText();
            String tarfif = tarifJourEdit.getText();
            LocalDate last_assur_date;
            LocalDate next_assur_date;
            LocalDate last_scanner_date;
            LocalDate next_scanner_date; 
            if(lastAssuranceEdit.getValue()!=null && nextAssuranceEdit.getValue()!=null){
                    long daysBetween = ChronoUnit.DAYS.between(lastAssuranceEdit.getValue(),nextAssuranceEdit.getValue() );
                    if(daysBetween<0){
                    ClassAlert.errorAlert("{Date derniére assurance} doit être supérieure à {Date prochaine assurance}!", "Erreur de saisie"); 
                    return;
                    }
                }
            if(lastScannerEdit.getValue()!=null && nextScannerEdit.getValue()!=null){
                    long daysBetween = ChronoUnit.DAYS.between(lastScannerEdit.getValue(),nextScannerEdit.getValue() );
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
            
            String sql =  "UPDATE  Vehicule SET IDMarque=?, NumChassis=?, Matricule=?, DateAssur=?, ProchAssur=?, DateScanner=?,"
                    + " ProchScanner=?, Kilometrage=?, ProchVidange=?, derniereChaine=?, ProchChaine=?, tarifJour=?, NiveauCarburant=?, modele=? ,remarques=?"
                    + " WHERE IDVehicule = "+idVehicule;
               PreparedStatement ps = bdd.prepareStat(sql);
               ps.setInt(1,selectedIDMarque);
                ps.setString(2,numChassis);
                ps.setString(3, matricule);
                if( lastAssuranceEdit.getValue() != null){
                last_assur_date = lastAssuranceEdit.getValue();
                ps.setString(4, Date.valueOf(last_assur_date).toString());
                }else{
                ps.setString(4, null);
                }
                
                 if( nextAssuranceEdit.getValue() != null){
                next_assur_date = nextAssuranceEdit.getValue();
                ps.setString(5, Date.valueOf(next_assur_date).toString());
                }else{
                ps.setString(5, null);
                }
                   if( lastScannerEdit.getValue() != null){
                last_scanner_date = lastScannerEdit.getValue();
                ps.setString(6, Date.valueOf(last_scanner_date).toString());
                }else{
                ps.setString(6, null);
                }
                   
                if( nextScannerEdit.getValue() != null){
                next_scanner_date = nextScannerEdit.getValue();
                ps.setString(7, Date.valueOf(next_scanner_date).toString());
                }else{
                ps.setString(7, null);
                }
                 if(kiloActuel!=null)
                ps.setInt(8, Integer.parseInt(kiloActuel));
                  if(!prochVid.isEmpty())
                ps.setInt(9, Integer.parseInt(prochVid));
                  else
                    ps.setNull(9, java.sql.Types.INTEGER);
                    if(!last_chaine.isEmpty())
                ps.setInt(10, Integer.parseInt(last_chaine));
                    else
                ps.setNull(10, java.sql.Types.INTEGER);
                      if(!next_chaine.isEmpty())
                ps.setInt(11, Integer.parseInt(next_chaine));
                      else
                    ps.setNull(11, java.sql.Types.INTEGER);
                  if(tarfif!=null)
                ps.setInt(12, Integer.parseInt(tarfif));
                ps.setString(13, carbActuel);
                ps.setString(14, modele);
                ps.setString(15, remarques);

            
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Confiramtion dialog");
            alertConfirm.setHeaderText(" Confirmation de modification ! ");
            alertConfirm.setContentText("êtes-vous sûr de continuer ?");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK){
                int ex = ps.executeUpdate();
                if (ex>0) {
                     
                     ClassAlert.infoAlert("Vous avez modifier le véhicule avec succès !", "Succès");
                     ps.close();
                   
                     Stage stage = (Stage) KiloActuelEdit.getScene().getWindow();
                    // do what you have to do
                    stage.close();
                  
                }else{
                    ClassAlert.infoAlert("Erreur dans la base de données !", "Erreur");
                }
            }   } catch (SQLException ex) {
            Logger.getLogger(ModifierClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
    }
    
}
