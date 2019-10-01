/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.clients;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import locationcar.Alert.ClassAlert;
import locationcar.Modele.Client;
import locationcar.dataBase.DataBase;

/**
 * FXML Controller class
 *
 * @author Fodil
 */
public class ClientsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private DataBase base;
    private  ObservableList<Client> list ;
    private boolean listeComplet = false ;


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
    private RadioButton particulier;

    @FXML
    private RadioButton societe;
    
       @FXML
    private TableView<Client> Table;

    @FXML
    private TableColumn<Client, Integer> Id_col;

    @FXML
    private TableColumn<Client, String> Type_col;

    @FXML
    private TableColumn<Client, String> Soc_col;

    @FXML
    private TableColumn<Client, String> Nom_col;

    @FXML
    private TableColumn<Client, String> Date_col;

    @FXML
    private TableColumn<Client, String> Tel_col;

    @FXML
    private TableColumn<Client, String> Adrs_col;
    
    @FXML
    private TextField Search;
    @FXML
    private ToggleGroup Mygrp;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        base = new DataBase();
         particulier.setSelected(true);
        initColumn();
        eventSociete();
        loadData();
        
        
       
    }  
    private void initColumn() {
        Id_col.setCellValueFactory(new PropertyValueFactory<>("IdClient"));
        Type_col.setCellValueFactory(new PropertyValueFactory<>("TypeClient"));
        Soc_col.setCellValueFactory(new PropertyValueFactory<>("NomSociete"));
        Nom_col.setCellValueFactory(new PropertyValueFactory<>("NomClient"));
        Date_col.setCellValueFactory(new PropertyValueFactory<>("DateNaissanceClient"));
        Tel_col.setCellValueFactory(new PropertyValueFactory<>("TelephoneClient"));
        Adrs_col.setCellValueFactory(new PropertyValueFactory<>("AdressCLient"));
        
    }
     @FXML
    void ajouterClient(ActionEvent event) {
       insertClient();
    }
    
    private void insertClient(){
        if (AjoutDatanull() == false) {
            ClassAlert.errorAlert("Veuillez saisir les données !", "Erreur de saisie"); 
        }else
        {
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
                String numPermi = permis.getText();
                LocalDate datecni;
                LocalDate datepermis;
                String lieu_nais = lieu_naiss.getText();
                String remarques = this.remarque.getText();
                String numtlfn = num_tlfn.getText();
                String adress = adresse.getText();
                String email = this.email.getText();
                int numCni;
                String typeClient;
                if (societe.isSelected()) {
                    typeClient = "Société";
                }else{
                    typeClient = "Particulier";
                }
                if (nomSoc.isEmpty()) {
                    nomSoc = null;
                }
                if (email.isEmpty()) {
                    email = null;
                }
                if (adress.isEmpty()) {
                    adress = null;
                }
                if (remarques.isEmpty()) {
                    remarques = null;
                }
                String sql =  "INSERT INTO Client (TypeClient,NomSociete,Nom,Prenom,DateNaiss,LieuNaiss,"
                        + "NumPasseport,DatePermis,NumTel,DatePasseport,Adresse,Email,Remarques,NumPermis) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
                PreparedStatement ps = base.prepareStat(sql);
                ps.setString(1, typeClient);
                ps.setString(2, nomSoc);
                ps.setString(3,nom);
                ps.setString(4,prenom);
                ps.setString(5,Date.valueOf(dateNaissance).toString());
                ps.setString(6, lieu_nais);
                if (!cni.getText().isEmpty()) {
                   ps.setString(7, cni.getText());
                }else{

                    ps.setString(7, null);
                }
                if( date_permis.getValue() != null){
                    datepermis = date_permis.getValue();
                    ps.setString(8, Date.valueOf(datepermis).toString());
                }else{
                    ps.setString(8, null);
                }
                if (numtlfn.isEmpty()) {
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
                if (!permis.getText().isEmpty()) {
                    numPermi = permis.getText();
                    ps.setString(14, numPermi);
                }else{
                    ps.setString(14, null);
                }
                
                Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirm.setTitle("Confiramtion dialog");
                alertConfirm.setHeaderText(" Confirmation d'ajout ! ");
                alertConfirm.setContentText("êtes-vous sûr ?");
                Optional<ButtonType> result = alertConfirm.showAndWait();
                if (result.get() == ButtonType.OK){
                    int ex = ps.executeUpdate();
                    if (ex>0) {
                        ps.close();
                        ClassAlert.infoAlert("Vous avez ajouté le client avec succès !", "Succès");
                        loadData();
                        ClearClient(null);
                    }else{
                        ClassAlert.infoAlert("Erreur dans la base de données !", "Erreur");
                    }

        }} catch (SQLException ex) {
                Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @FXML
    private void recherhceListe(ActionEvent event) {
        String rech = Search.getText();
        if(!rech.trim().isEmpty()){
            String qu = "select * from Client where "
                            + "( Nom like '"+rech+"%' OR "
                            + "TypeClient like '"+rech+"%' OR "
                            //+ "maisonEdition like'%"+rech+"%' OR "
                            + "Prenom like '%"+rech+"%' OR "
                            + "LieuNaiss like '%"+rech+"%' )";


            
            ResultSet rs = base.execQuery(qu);
            list.clear();
            try {
                while(rs.next()){
                int idClient = rs.getInt("IDClient");
                String typeClient = rs.getString("TypeClient");
                String nomSoc = rs.getString("NomSociete");
                String DateNaissance = rs.getString("DateNaiss");
                String adress = rs.getString("Adresse");
                String telephone = rs.getString("NumTel");
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                String nomPrenom = nom+" / "+prenom;
                list.add(new Client(idClient, typeClient,nomSoc, nomPrenom, DateNaissance, telephone, adress));
            }
                rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table.setItems(null);
        Table.setItems(list);
            listeComplet=true;
        }
        else{
            if(listeComplet){
               
                loadData();
                listeComplet=false;
            }
        }
    }
    @FXML
    private void recherhceListe_v2(KeyEvent event) {
        Search.textProperty().addListener((observable, oldValue, newValue) -> {
            recherhceListe(new ActionEvent());
        });
    }
    public void loadData() {
      
        list=FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT * FROM Client";
        try {
            
            ResultSet rs = base.execQuery(qu);
            
            while(rs.next()){
                int idClient = rs.getInt("IDClient");
                String typeClient = rs.getString("TypeClient");
                String nomSoc = rs.getString("NomSociete");
                String DateNaissance = rs.getString("DateNaiss");
                String adress = rs.getString("Adresse");
                String telephone = rs.getString("NumTel");
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                String nomPrenom = nom+" / "+prenom;
                list.add(new Client(idClient, typeClient,nomSoc, nomPrenom, DateNaissance, telephone, adress));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table.setItems(null);
        Table.setItems(list);
        
    }
 
    private boolean AjoutDatanull(){
        return !nom.getText().isEmpty() || !prenom.getText().isEmpty() || date_naiss.getValue() != null;
    }
    @FXML
    private void ClearClient(ActionEvent event){
        ArrayList<TextField> tflist = new ArrayList<TextField>();
        tflist.add(nom);
        tflist.add(prenom);
        tflist.add(lieu_naiss);
        tflist.add(email);
        tflist.add(num_tlfn);
        tflist.add(adresse);
        tflist.add(permis);
        tflist.add(cni);
        tflist.add(nom_soci);
        remarque.clear();
        date_naiss.setValue(null);
        date_cni.setValue(null);
        date_permis.setValue(null);
        for(TextField tf:tflist){
            tf.clear();
        }
        
    }

    @FXML
    private void modifier_client(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/locationcar/view/ModifierClient.fxml"));
        try {
            Client selectedForEdit = Table.getSelectionModel().getSelectedItem();
            if (selectedForEdit == null) {
                return;
            }
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            ModifierClientController controler = fxmlLoader.getController();
            controler.getData(selectedForEdit);
            controler.getData(selectedForEdit);
            Stage nStage = new Stage();
//            addProductController.addSupplyerStage(nStage);
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
            nStage.setOnHiding( eventt -> {loadData();} );

        } catch (IOException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    

    @FXML
 private void supprimer(ActionEvent event) {
           Client selectedTodelete = Table.getSelectionModel().getSelectedItem();
        int idClient = selectedTodelete.getIdClient();
        String qu = "DELETE FROM Client WHERE IDClient ="+idClient ;
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Confiramtion dialog");
            alertConfirm.setHeaderText(" Confirmation de suppression ! ");
            alertConfirm.setContentText("êtes-vous sûr ?");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK){
            if (base.execAction(qu)) {
                loadData();
                ClassAlert.infoAlert("Supression avec Succes","Succes");
            }else{
                ClassAlert.infoAlert("Supression avec fail","fail");
            }
            }
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
    
    
}
