/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.locations.OptionsLocation;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import static java.util.concurrent.TimeUnit.DAYS;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import locationcar.Alert.ClassAlert;
import locationcar.Modele.Location;
import locationcar.dashboard.notification.NotificationController;
import locationcar.dataBase.DataBase;

/**
 * FXML Controller class
 *
 * @author computeruser
 */
public class RendreController implements Initializable {
    private static java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
    private DataBase base;
    String dateAncien;
    private int idLocation;
    private int idVehicule;
    private String numChassis=null;
    String fullname;
    String modele;
    String dateSortie;
    String heureSortie;
    int ancienKilo;

    int montant;
    private LocalDate dateEntrer;
    private int nouveauMontant;
    private int nbrJours;
    @FXML
    private Label date_prevue;
    @FXML
    private Label heure_prevue;
    @FXML
    private TextField date_resti;
    @FXML
    private TextField heure_resti;
    @FXML
    private TextField jours_supp;
    @FXML
    private TextField tarif_supp;
     LocalDate dateRST;
        LocalDate datePRV;
        long daysBetween;
    @FXML
    private TextField plusKilo;
    @FXML
    private TextField newKilo;
    @FXML
    private Label ancienLabel;
    DateTimeFormatter f2 = DateTimeFormatter.ofPattern("HH:mm");
    String now  = LocalTime.now().format(f2);
    @FXML
    private Button btnClose;
    /**
     * Initializes the controller class.s
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        base  = new DataBase();
        date_resti.setText(LocalDate.now()+"");
        heure_resti.setText(LocalTime.now().format(f2)+"");
        plusKilo.textProperty().addListener((observable, oldValue, newValue) -> {
            int addi = Integer.parseInt(plusKilo.getText());
            
            newKilo.setText(addi+ancienKilo+"");
               });
    }    
    public void getData(Location l){
        fullname=l.getNomClient();
        modele=l.getNomVehicule();
        montant = l.getMontantApayer();
                        System.out.println("ana lakdim"+montant);

        idLocation = l.getIDLocation();
        nbrJours = l.getNbrJours();
        dateAncien = l.getDateEntree();
        dateSortie = l.getDateSortie();
        heureSortie = l.getHeureSortie();
        idVehicule = base.getIdVehicule(idLocation);
        numChassis=base.getNumChassis(idVehicule);
        ancienKilo=base.getKilometrage(idVehicule);
        ancienLabel.setText("Ancien Kilométrage:"+ancienKilo);
        int tarif = base.getTarif(idVehicule);
        int heureAncien = LocalTime.parse(l.getHeureEntree()).getHour();
        int minAncien = LocalTime.parse(l.getHeureEntree()).getMinute();
        date_prevue.setText(dateAncien);
        heure_prevue.setText( LocalTime.parse(l.getHeureEntree())+"");
        LocalDate dateRST = LocalDate.parse(date_resti.getText());
        LocalDate datePRV = LocalDate.parse(date_prevue.getText());
        daysBetween = ChronoUnit.DAYS.between(datePRV, dateRST);
        if(daysBetween>=0)
        jours_supp.setText(daysBetween+"");
        int heureRendre = 0;
        int minRendre = 0;
        
         try {
             heureRendre = LocalTime.parse(heure_resti.getText(),f2).getHour();
             minRendre = LocalTime.parse(heure_resti.getText(),f2).getMinute();
                     System.out.println("DAYS BETWEEN "+heureRendre);
        System.out.println("DAYS BETWEEN "+minRendre);

            
            } catch (Exception e) {
             System.out.println("Erreur;");
            

            }
       
        if(heureAncien==0) heureAncien =24;
        if(heureRendre==0) heureRendre =24;
        int addition;
        if(daysBetween>=0){
        if(daysBetween!=0){
            addition = (int) (tarif*daysBetween);
            tarif_supp.setText(addition+"");
        }
        else {
        if(heureRendre-heureAncien==1){
            if(minAncien<=minRendre)
            tarif_supp.setText("500");
            else
              tarif_supp.setText("");

       }
        if(heureRendre-heureAncien==2){
            if( minAncien<=minRendre)
            tarif_supp.setText("1000");
            else
              tarif_supp.setText("500");

        }
        if(heureRendre-heureAncien==3){
            if( minAncien<=minRendre)
            tarif_supp.setText("1500");
            else
            tarif_supp.setText("1000");
        }
        if(heureRendre-heureAncien>3){
            addition = tarif;
            tarif_supp.setText(addition+"");
        }
        
        
    }
        }   

    }
    private void updateLocation(){
        
            if(!tarif_supp.getText().isEmpty())
        nouveauMontant = montant+Integer.parseInt(tarif_supp.getText());
        else
          nouveauMontant = montant;
                System.out.println("ana montant"+nouveauMontant);
                
            try {
                                String sql =  "INSERT INTO Historique (NomClient,NumChassis,ModeleVehicule,DateSortie,DateEntree,HeureSortie,HeureEntree\n" +
                       ",DateRestitution,HeureRestitution,MontantApaye,TarifSupp) VALUES (?,?,?,?,?,?,?,?,?,?,?)" ;
                PreparedStatement ps = base.prepareStat(sql);
                
                ps.setString(1, fullname);
                ps.setString(2, numChassis);
                ps.setString(3, modele);
                ps.setString(4, Date.valueOf(dateSortie).toString());
                ps.setString(5, Date.valueOf(dateAncien).toString());
                ps.setString(6, heureSortie);
                ps.setString(7, heure_prevue.getText());
                ps.setString(8, date_resti.getText());
                ps.setString(9, heure_resti.getText());
                ps.setInt(10, nouveauMontant);
                if(!this.tarif_supp.getText().isEmpty())
                ps.setInt(11, Integer.parseInt(this.tarif_supp.getText()));
                    else
                    ps.setNull(11, java.sql.Types.INTEGER);
                Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirm.setTitle("Confiramtion dialogue");
                alertConfirm.setHeaderText(" Confirmation d'opération !");
                alertConfirm.setContentText("êtes-vous sûr ?");
                Optional<ButtonType> result = alertConfirm.showAndWait();
                if (result.get() == ButtonType.OK){
                    int ex = ps.executeUpdate();
                    if (ex>0) {
                        base.setKilo(idVehicule,Integer.parseInt(newKilo.getText()));
                        base.changeStat(idVehicule);
                        ps.close();
                        ClassAlert.infoAlert("Opération réussie !", "Succès");
                        base.deleteLocation(idLocation);
                        Stage stage = (Stage)  date_resti.getScene().getWindow();
                             // do what you have to do
                         stage.close();
                        
                        
                        
                    }else{
                        ClassAlert.infoAlert("There are something wrong in data base", "Erreur");
                    }

        }} catch (SQLException ex) {
                Logger.getLogger(RendreController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    @FXML
    private void confirmer_rendre(ActionEvent event) {
        if(daysBetween<0){
                   ClassAlert.errorAlert("Opération non permise !", "Erreur de saisie");
                   return;

                }
        if(newKilo.getText().isEmpty()) {
         ClassAlert.infoAlert("Entrez le nouveau kilométrage !", "Erreur");
         return;
        }
        updateLocation();
       
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    
}
