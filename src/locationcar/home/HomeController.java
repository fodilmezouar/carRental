/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.home;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import locationcar.dataBase.DataBase;

/**
 * FXML Controller class
 *
 * @author Fodil
 */
public class HomeController implements Initializable {
    private DataBase base;
    private int carDispo = 0;
    private int carRental = 0;
    private int chifreDeJour = 0;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Label vehiculeDispo;
    @FXML
    private Label vehiculLouee;
    @FXML
    private Label chifreJour;
    @FXML
    private Label date;
    @FXML
    private Label adress;
    @FXML
    private Label numTel;
    @FXML
    private Label email;
    @FXML
    private ImageView logoLocation;
    @FXML
    private Label nomAgence;
    @FXML
    private Label vehiculEnPanne;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        base = new DataBase();
        Date actuelle = new Date();
        String dateAJour =  new SimpleDateFormat("yyyy-MM-dd").format(actuelle);
        date.setText(dateAJour);
        getDataHome();
        vehiculeDispo.setText(carDispo+"");
        vehiculLouee.setText(carRental+"");
    } 
    private void getDataHome(){
        try {
            String queryCarDispo ="SELECT count(IDVehicule) AS carDispo FROM Vehicule WHERE statut =0";
            String queryCarEnpanne ="SELECT count(IDVehicule) AS carEnpanne FROM Vehicule WHERE statut =2";
            String queryCarRenta ="SELECT count(IDVehicule) AS carRental FROM Vehicule WHERE statut =1";
            String revenuquery ="SELECT SUM(MontantApaye) as somme FROM Location WHERE date(DateSortie)= date('now')";
            String dispensequery ="SELECT SOMME(IDVehicule) AS dispenes FROM Charge WHERE statut =1";
            String queryAgence = "SELECT Nom_Agence,Adresse,NumTel,Email,Logo FROM Agence";
            ResultSet rsCarDis = base.execQuery(queryCarDispo);
            ResultSet rsCarRen = base.execQuery(queryCarRenta);
            ResultSet rsCarDown = base.execQuery(queryCarEnpanne);
            ResultSet rsAgence = base.execQuery(queryAgence);
            ResultSet rsrevenu = base.execQuery(revenuquery);
            int chiffreAju=0;
            String nomAG = "",adressAgen = "",numTel ="",email="";
            int carPanne=0;
            byte[] imageBytes=null;
            if (rsCarDis.next()) {
                carDispo = rsCarDis.getInt("carDispo");
            }
            if (rsCarRen.next()) {
                carRental = rsCarRen.getInt("carRental");
            }
            if (rsCarDown.next()) {
                carPanne = rsCarDown.getInt("carEnpanne");
            }
            if (rsrevenu.next()) {
                chiffreAju = rsrevenu.getInt("somme");
            }
            chifreJour.setText(chiffreAju+" Da");
            System.out.println(chiffreAju);
            while(rsAgence.next()){
                nomAG = rsAgence.getString("Nom_Agence");
                adressAgen = rsAgence.getString("Adresse");
                numTel = rsAgence.getString("NumTel");
                email = rsAgence.getString("Email");
                imageBytes = rsAgence.getBytes("Logo");
            }
            rsAgence.close();
            rsCarDis.close();
            rsCarDown.close();
            rsCarRen.close();
            rsrevenu.close();
            InputStream input = new ByteArrayInputStream(imageBytes);
                
                Image imge = new Image(input);
                vehiculEnPanne.setText(carPanne+"");
                logoLocation.setImage(imge);
                nomAgence.setText(nomAG.toUpperCase());
                adress.setText(adressAgen);
                this.email.setText(email);
                this.numTel.setText(numTel);
                
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
