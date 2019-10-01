/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.clients;

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
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import locationcar.Alert.ClassAlert;
import locationcar.Modele.Client;
import locationcar.dataBase.DataBase;
import locationcar.locations.LocationsController;

/**
 * FXML Controller class
 *
 * @author Fodil
 */
public class ModifierClientController implements Initializable {
    private DataBase base;
    @FXML
    private RadioButton particulier;
    @FXML
    private ToggleGroup Mygrp;
    @FXML
    private RadioButton societe;
    @FXML
    private TextField nom_soci;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private DatePicker date_naiss;
    @FXML
    private TextField lieu_naiss;
    @FXML
    private TextField cni;
    @FXML
    private DatePicker date_cni;
    @FXML
    private TextField permis;
    @FXML
    private DatePicker date_permis;
    @FXML
    private TextField num_tlfn;
    @FXML
    private TextField adresse;
    @FXML
    private TextField email;
    @FXML
    private TextArea remarque;
    @FXML
    private TextField idClient;
    @FXML
    private Button btnClose;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        base = new DataBase();
                eventSociete();

        
    } 
      public void eventSociete(){
    societe.selectedProperty().addListener((ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) -> {
        if (isNowSelected) { 
            nom_soci.setDisable(false);
        } else {
            nom_soci.setDisable(true);
        }
    });
    particulier.selectedProperty().addListener((ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) -> {
        if (isNowSelected) { 
            nom_soci.setDisable(true);
        } else {
            nom_soci.setDisable(false);
        }
    });
    

    }
    public void getData( Client client){
        int  idClient = client.getIdClient();
            
            String fullname = client.getNomClient();
            String[] splitClient = fullname.split("/ ");
            String nomClient = splitClient[0];
            String prenomClient = "";
            String address = client.getAdressCLient();
            String dateNaiss = client.getDateNaissanceClient();
            String typeClient = client.getTypeClient();
            String telephone = client.getTelephoneClient();
            LocalDate date_naissance = LocationsController.LOCAL_DATE(dateNaiss);
            String query = "SELECT Nom,Prenom,NomSociete,DatePasseport,NumPasseport,Email,NumPermis,DatePermis,Remarques,LieuNaiss FROM Client WHERE IDClient ="+idClient;
            String societe ="";
            String email ="";
            String numPass ="";
            String datePass ="";
            String numPermis ="";
            String datePermis ="";
            String remarque ="";
            String lieuNaiss ="";
        try {
            
            ResultSet rs = base.execQuery(query);
            while(rs.next()){
                societe = rs.getString("NomSociete");
                datePass = rs.getString("DatePasseport");
                numPass = rs.getString("NumPasseport");
                email = rs.getString("Email");
                numPermis = rs.getString("NumPermis");
                datePermis = rs.getString("DatePermis");
                remarque = rs.getString("Remarques");
                lieuNaiss = rs.getString("LieuNaiss");
                prenomClient = rs.getString("Prenom");
                nomClient = rs.getString("Nom");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModifierClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (typeClient.equals("Société")) {
            this.societe.setSelected(true);
            nom_soci.setText(societe);
        }else{
          particulier.setSelected(true);
        }
        if (datePass!= null) {
            LocalDate date_pass = LocationsController.LOCAL_DATE(datePass);
            date_cni.setValue(date_pass);
        }
        if (datePermis!= null) {
            LocalDate date_permis = LocationsController.LOCAL_DATE(datePermis);
            this.date_permis.setValue(date_permis);
        }
        this.idClient.setText(idClient+"");
        nom.setText(nomClient);
        prenom.setText(prenomClient);
        this.adresse.setText(address);
        num_tlfn.setText(telephone);
        this.remarque.setText(remarque);
        this.email.setText(email);
        cni.setText(numPass);
        permis.setText(numPermis);
        lieu_naiss.setText(lieuNaiss);
        date_naiss.setValue(date_naissance);
        
        
        
    }

    @FXML
    private void ClearClient(ActionEvent event) {
    }

    @FXML
    private void ModifierClient(ActionEvent event) {
        if(date_naiss.getValue()!=null){
                    long daysBetween = ChronoUnit.DAYS.between(date_naiss.getValue(),LocalDate.now());
                    if(daysBetween<0){
                    ClassAlert.errorAlert("{Date de naissance} doit être inférieure à la date d'aujourd'hui!", "Erreur de saisie"); 
                    return;
                    }
                }
            if(date_permis.getValue()!=null){
                    long daysBetween = ChronoUnit.DAYS.between(date_permis.getValue(),LocalDate.now());
                    if(daysBetween<0){
                    ClassAlert.errorAlert("{Date de permis} doit être inférieure à la date d'aujourd'hui!", "Erreur de saisie"); 
                    return;
                    }
                }
            if(date_cni.getValue()!=null){
                    long daysBetween = ChronoUnit.DAYS.between(date_cni.getValue(),LocalDate.now());
                    if(daysBetween<0){
                    ClassAlert.errorAlert("{Date de passeport} doit être inférieure à la date d'aujourd'hui!", "Erreur de saisie"); 
                    return;
                    }
                }
        
        try {
            String nom = this.nom.getText();
            String prenom = this.prenom.getText();
            String nomSoc = nom_soci.getText();
            LocalDate dateNaissance = date_naiss.getValue();
            LocalDate datecni;
            LocalDate datepermis;
            String lieu_nais = lieu_naiss.getText();
            String remarques = this.remarque.getText();
            String numPass = this.cni.getText();
            String numPermis = this.permis.getText();
            String numtlfn = num_tlfn.getText();
            String adress = adresse.getText();
            String email = this.email.getText();
            String typeClient;
            if (societe.isSelected()) {
                typeClient = "Société";
            }else{
                typeClient = "Particulier";
            }
            if (nomSoc == "") {
                nomSoc = null;
            }
            if (email == "") {
                email = null;
            }
            if (adress =="") {
                adress = null;
            }
            if (remarques == "") {
                remarques = null;
            }
            if (numPass == "") {
                numPass = null;
            }
             if (numPermis == "") {
                numPermis = null;
            }
            String sql =  "UPDATE  Client SET TypeClient=?, NomSociete=?, Nom=?, Prenom=?, DateNaiss=?, LieuNaiss=?,"
                    + " NumPasseport=?, DatePermis=?, NumTel=?, DatePasseport=?, Adresse=?, Email=?, Remarques=?, NumPermis=? "
                    + " WHERE IDClient = "+Integer.parseInt(idClient.getText());
            System.out.println(sql);
            PreparedStatement ps = base.prepareStat(sql);
            ps.setString(1, typeClient);
            ps.setString(2, nomSoc);
            ps.setString(3,nom);
            ps.setString(4,prenom);
            ps.setString(5,Date.valueOf(dateNaissance).toString());
            ps.setString(6, lieu_nais);
             ps.setString(7,numPass);
             if( date_permis.getValue() != null){
                datepermis = date_permis.getValue();
                ps.setString(8, Date.valueOf(datepermis).toString());
            }else{
                ps.setString(8, null);
            }
            if (numtlfn == "") {
                numtlfn = null;
            }
            ps.setString(9,numtlfn);
            if( date_cni.getValue()!=null ){
                datecni = date_cni.getValue();
                ps.setString(10, Date.valueOf(datecni).toString());
            }else{
                ps.setString(10, null);
            }
            ps.setString(11, adress);
            ps.setString(12, email);
            ps.setString(13,remarques);
            ps.setString(14,numPermis);
            
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Confiramtion dialog");
            alertConfirm.setHeaderText(" Confirmation de modification ! ");
            alertConfirm.setContentText("êtes-vous sûr ?");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK){
                int ex = ps.executeUpdate();
                if (ex>0) {
                    ps.close();
                    ClassAlert.infoAlert("Vous avez modifier le client avec succès !", "Succès");
                    
                    ClearClient(null);
                }else{
                    ClassAlert.infoAlert("Erreur dans la base de données !", "Erreur");
                }
            }   } catch (SQLException ex) {
            Logger.getLogger(ModifierClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = (Stage) idClient.getScene().getWindow();
    // do what you have to do
        stage.close();
    
}
    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
