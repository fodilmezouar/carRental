/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.locations.OptionsLocation;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import locationcar.Alert.ClassAlert;
import locationcar.Modele.Location;
import locationcar.dashboard.notification.NotificationController;
import locationcar.dataBase.DataBase;
import locationcar.locations.LocationsController;
import static locationcar.locations.LocationsController.LOCAL_DATE;

/**
 * FXML Controller class
 *
 * @author Fodil
 */
public class ProlongerController implements Initializable {
    private static java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
    private DataBase base;
    String dateAncien;
    private int idLocation;
    private int idVehicule;
    private LocalDate dateEntrer;
    private int montantAncien;
    private int nbrJours;
    @FXML
    private Label anciendate;
    @FXML
    private TextField nombreJours;
    @FXML
    private Label nouvdate;
    @FXML
    private Button btnClose;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        base = new DataBase();
        
    }
    public void getData(Location l){
        
        idLocation = l.getIDLocation();
        nbrJours = l.getNbrJours();
        dateAncien = l.getDateEntree();
        anciendate.setText(dateAncien);
        montantAncien = l.getMontantApayer();
        
        
    }
    private void updateLocation(){
        try {
            if (nombreJours.getText().isEmpty()) {
                ClassAlert.errorAlert("Veuillez saisir les données !", "Erreur de saisie"); 
            }else{
            int idVehicule = base.getIdVehicule(idLocation);
            int tarif = base.getTarif(idVehicule);
            int nbrjourProlonger = Integer.parseInt(nombreJours.getText());
            int nouvNbrJours = nbrJours+nbrjourProlonger;
            int montantProlonger = tarif*nbrjourProlonger *17/100;
            int montant = montantProlonger+montantAncien;
            String query = "UPDATE Location SET DateEntree=?,NombreJours=?,MontantApaye=? WHERE IDLocation = "+idLocation;
            PreparedStatement ps = base.prepareStat(query);
            ps.setString(1,nouvdate.getText());
            ps.setInt(2,nouvNbrJours);
            ps.setInt(3, montant);
            
             Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Confiramtion dialog");
            alertConfirm.setHeaderText(" Confirmation de prolongation ! ");
            alertConfirm.setContentText("êtes-vous sûr de continuer ?");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK){
                int ex = ps.executeUpdate();
                if (ex>0) {
                     ps.close();
                     ClassAlert.infoAlert("Opération réussie !", "Succès");
                     ps.close();
                     Stage stage = (Stage)   nombreJours.getScene().getWindow();
                             // do what you have to do
                         stage.close();
                        
                }else{
                    ClassAlert.infoAlert("Erreur dans la base de données !", "Erreur");
                }
            }   
        }} catch (SQLException ex) {
            Logger.getLogger(ProlongerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void proplonger(ActionEvent event) {
        updateLocation();
       
    }
    @FXML
    private void calcDate(ActionEvent e){
         int nbrJour =0;
        if(!nombreJours.getText().isEmpty()){
        try{
         nbrJour= Integer.parseInt(nombreJours.getText());
        }catch(NumberFormatException num){
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, num);					
	}
        
        Calendar c =  Calendar.getInstance();
        LocalDate dateancien = LocalDate.parse(dateAncien);
       
        c.set(dateancien.getYear(), dateancien.getMonthValue()-1, dateancien.getDayOfMonth()+nbrJour);
        java.util.Date date=c.getTime();
        dateEntrer = LOCAL_DATE(dateFormat.format(date));
        nouvdate.setText(dateEntrer.toString());
    }
        
}
    @FXML
    private void dateCalc_v2(){
        nombreJours.textProperty().addListener((observable, oldValue, newValue) -> {
            calcDate(new ActionEvent());
            
        });
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
