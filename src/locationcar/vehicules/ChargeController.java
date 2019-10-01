/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.vehicules;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import locationcar.Alert.ClassAlert;
import locationcar.Modele.Vehicule;
import locationcar.dashboard.notification.NotificationController;
import locationcar.dataBase.DataBase;
import locationcar.locations.OptionsLocation.RendreController;

/**
 * FXML Controller class
 *
 * @author computeruser
 */

public class ChargeController implements Initializable {
    private DataBase base;
    private int idVehicule;
    String prochAssur;
    String prochScanner;
     String lastAssur;
    String lastScanner;
    int lastChaine;
    int prochChaine;
    @FXML
    private ComboBox<String> type_charge;

    @FXML
    private DatePicker dateProch_charge;

    @FXML
    private TextField kilo_charge;

    @FXML
    private TextArea description;

    @FXML
    private TextField depense;
    @FXML
    private DatePicker lastDate_charge;
    @FXML
    private TextField lastkilo_charge;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            base  = new DataBase();
            intialiserType();
            typeChargeEvent();
       
    } 
   
    public void getDataVehicule( Vehicule vh){
           idVehicule = vh.getIDVehicule();
            
            prochAssur = vh.getNextAssur();
            prochScanner = vh.getNextScanner();
            prochChaine = vh.getProchChaine();
            lastAssur = vh.getLastAssur();
            lastScanner = vh.getLastScanner();
            lastChaine = vh.getLastChaine();
            int selected = type_charge.getSelectionModel().getSelectedIndex();

              switch (selected) {
               case 0: 
                   if(lastAssur!=null)
                   lastDate_charge.setValue(LocalDate.parse(lastAssur));
                        break;
               case 1: 
                        if(lastScanner!=null)
                        lastDate_charge.setValue(LocalDate.parse(lastScanner));
                        break;
           
               case 3: lastkilo_charge.setText(lastChaine+"");
                        break;
               default:
                        System.out.println("NO CHOICE");
                        break;
                        }
 
            
             
            }

    @FXML
    private void ajouterCharge(ActionEvent event) {
            if (AjoutDatanull() == true) {
            ClassAlert.errorAlert("Veuillez saisir les données !", "Erreur de saisie");
            

        
        }else
        { 
            if(!kilo_charge.isDisabled()){
            Integer kilo = null;
                 try {
                 kilo = Integer.parseInt(this.kilo_charge.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {kilométrage prochain charge (*)} correctement !", "Erreur de saisie"); 
                       return;

                    }
        }
             if(!lastkilo_charge.isDisabled()){
            Integer kilo = null;
                 try {
                 kilo = Integer.parseInt(this.lastkilo_charge.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {kilométrage prochain charge (*)} correctement !", "Erreur de saisie"); 
                       return;

                    }
        }
            
                 Integer depenses = null;
                 try {
                 depenses = Integer.parseInt(this.depense.getText());

                }
                    catch (Exception e) {
                       ClassAlert.errorAlert("Veuillez saisir {Dépense (*) (DA)} correctement !", "Erreur de saisie");

                    }
        try {
                
                String sql =  "INSERT INTO Charge (IDVehicule,DateCharge,TypeCharge,DesignationCharge,Depense) "
                        + "VALUES (?,?,?,?,?)" ;
                PreparedStatement ps = base.prepareStat(sql);
                String type = type_charge.getSelectionModel().getSelectedItem();
                ps.setInt(1, idVehicule);
                ps.setString(2, LocalDate.now().toString());
                ps.setString(3, type);
                ps.setString(4, description.getText());
                ps.setInt(5, Integer.parseInt(depense.getText()));
                Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirm.setTitle("Confiramtion dialogue");
                alertConfirm.setHeaderText(" Confirmation d'opération !");
                alertConfirm.setContentText("êtes-vous sûr ?");
                Optional<ButtonType> result = alertConfirm.showAndWait();
                if (result.get() == ButtonType.OK){
                    int ex = ps.executeUpdate();
                    if (ex>0) {
                       ps.close();

                       int selected = type_charge.getSelectionModel().getSelectedIndex();
                        if(selected!=4){
                        String requete = "UPDATE Vehicule ";
                        requete += "";
                        switch (selected) {
               case 0: requete += " SET DateAssur = '"+lastDate_charge.getValue().toString()+"',ProchAssur='"+dateProch_charge.getValue().toString()+"'";
                        break;
               case 1: requete += "SET DateScanner = '"+lastDate_charge.getValue().toString()+"',ProchScanner='"+dateProch_charge.getValue().toString()+"'";
                        break;
               case 2: requete += "SET ProchVidange = "+kilo_charge.getText()+" ";
                        break;
               case 3: requete += "SET derniereChaine = "+lastkilo_charge.getText()+",ProchChaine="+kilo_charge.getText()+" ";
                        break;
                        default:System.out.println("NO CHOICE");
                        break;
                        }
                        requete += "WHERE IDVehicule = "+idVehicule+"";
                        PreparedStatement ps2 = base.prepareStat(requete);
                        ps2.execute();
                        ps2.close();
                    }
                        ClassAlert.infoAlert("Opération réussie !", "Succès");
                        Stage stage = (Stage)  depense.getScene().getWindow();
                        
                            NotificationController notif = new NotificationController();
                            notif.nbrNotif();
                         stage.close();
                        
                        
                    }else{
                        ClassAlert.infoAlert("There are something wrong in data base", "Erreur");
                    }

        }} catch (SQLException ex) {
                Logger.getLogger(RendreController.class.getName()).log(Level.SEVERE, null, ex);
            }}
        
    }
    public boolean AjoutDatanull(){
    return (!lastDate_charge.isDisabled() && lastDate_charge.getValue()==null) 
            ||(!dateProch_charge.isDisabled() && dateProch_charge.getValue()==null) || 
            (!kilo_charge.isDisabled() && kilo_charge.getText().isEmpty()) ||
            (!lastkilo_charge.isDisabled() && lastkilo_charge.getText().isEmpty())
            || depense.getText().isEmpty();
            
    
    }
    public void intialiserType(){
    List<String> list = new ArrayList<String>();
        list.add("Assurance");
        list.add("Scanner");
        list.add("Vidange");
        list.add("Chaine distribution");
        list.add("Autres");
        ObservableList obList = FXCollections.observableList(list);
        type_charge.getItems().clear();
        type_charge.setItems(obList);
        type_charge.getSelectionModel().select(0);
    }
   public void typeChargeEvent(){
             type_charge.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)->{
              int selected = type_charge.getSelectionModel().getSelectedIndex();
              if(selected == 4){
              dateProch_charge.setDisable(true);
              lastDate_charge.setDisable(true);
              kilo_charge.setDisable(true);
              lastkilo_charge.setDisable(true);
              dateProch_charge.setValue(null);
              lastDate_charge.setValue(null);
              kilo_charge.setText("");
              lastkilo_charge.setText("");
              }
              else{
              if(selected==0 || selected==1){
              dateProch_charge.setDisable(false);
              lastDate_charge.setDisable(false);
              kilo_charge.setDisable(true);
              lastkilo_charge.setDisable(true);
              kilo_charge.setText("");
              lastkilo_charge.setText("");
              
              }
              else if(selected==2){
              dateProch_charge.setDisable(true);
              lastDate_charge.setDisable(true);
              dateProch_charge.setValue(null);
              lastDate_charge.setValue(null);
              kilo_charge.setDisable(false);
              lastkilo_charge.setDisable(true);
              lastkilo_charge.setText("");
              }
               else if(selected==3){
              dateProch_charge.setDisable(true);
              lastDate_charge.setDisable(true);
              dateProch_charge.setValue(null);
              lastDate_charge.setValue(null);
              kilo_charge.setDisable(false);
              lastkilo_charge.setDisable(false);
              }
              
              
              }
              int selectedd = type_charge.getSelectionModel().getSelectedIndex();

              switch (selectedd) {
               case 0: lastDate_charge.setValue(LocalDate.parse(lastAssur));
                        break;
               case 1: 
                        lastDate_charge.setValue(LocalDate.parse(lastScanner));
                        break;
           
               case 3: lastkilo_charge.setText(lastChaine+"");
                        break;
               default:
                        System.out.println("NO CHOICE");
                        break;
                        }
});
   
   }
}
