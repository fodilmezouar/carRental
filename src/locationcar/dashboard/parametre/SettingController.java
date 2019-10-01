/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.dashboard.parametre;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import locationcar.Alert.ClassAlert;
import locationcar.dataBase.DataBase;

/**
 * FXML Controller class
 *
 * @author Fodil
 */
public class SettingController implements Initializable {
    private File file;
    private DataBase base;
    private byte[] imageBytes=null;
    @FXML
    private CheckBox checkAlter;
    @FXML
    private TextField textImage;
    @FXML
    private Button btnImage;
    @FXML
    private TextField nom_Agence;
    @FXML
    private TextField Adress_Agence;
    @FXML
    private TextField telAgence;
    @FXML
    private TextField emailAgence;
    @FXML
    private ImageView imageView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        base = new DataBase();
        getData();
    }    
     private byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }
     @FXML
     private void modiferDonneeCheck(ActionEvent e){
         if (!checkAlter.isSelected()) {
             nom_Agence.setDisable(true);
             emailAgence.setDisable(true);
             Adress_Agence.setDisable(true);
             telAgence.setDisable(true);
             btnImage.setDisable(true);
         }else{
             nom_Agence.setDisable(false);
             emailAgence.setDisable(false);
             telAgence.setDisable(false);
             Adress_Agence.setDisable(false);
             btnImage.setDisable(false);
         }
     }
     private void getData(){
        try {
            String queryAgence = "SELECT Nom_Agence,Adresse,NumTel,Email,Logo FROM Agence";
            ResultSet rsAgence = base.execQuery(queryAgence);
            String nomAG = "",adressAgen = "",numTel ="",email="";
            
            while(rsAgence.next()){
                nomAG = rsAgence.getString("Nom_Agence");
                adressAgen = rsAgence.getString("Adresse");
                numTel = rsAgence.getString("NumTel");
                email = rsAgence.getString("Email");
                imageBytes = rsAgence.getBytes("Logo");
            }
            nom_Agence.setText(nomAG);
            Adress_Agence.setText(adressAgen);
            emailAgence.setText(email);
            telAgence.setText(numTel);
             InputStream input = new ByteArrayInputStream(imageBytes);
                
                Image imge = new Image(input);
                imageView.setImage(imge);
            rsAgence.close();
        } catch (SQLException ex) {
            Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    private void updateAgence() throws FileNotFoundException{
        try {
            String path_image = textImage.getText();
            String nomAgence = nom_Agence.getText();
            String adressAgence = Adress_Agence.getText();
            String numTel = telAgence.getText();
            String email = emailAgence.getText();
            
            String sql =  "UPDATE  Agence SET Nom_Agence=?,Adresse=?,NumTel=?,Email=?,Logo=?";
            PreparedStatement ps = base.prepareStat(sql);
            if (file!= null) {

                ps.setBytes(5,readFile(path_image));
            }else{
                if (imageBytes != null) {
                    ps.setBytes(5, imageBytes);
                }else{
                ps.setNull(5, java.sql.Types.BLOB);
                }
            }
            ps.setString(1, nomAgence);
            ps.setString(2, adressAgence);
            ps.setString(3,numTel);
            ps.setString(4,email);
             Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirm.setTitle("Confiramtion dialog");
                alertConfirm.setHeaderText(" Confirmation de modification ! ");
                alertConfirm.setContentText("êtes-vous sûr ?");
                Optional<ButtonType> result = alertConfirm.showAndWait();
                if (result.get() == ButtonType.OK){
                    int ex = ps.executeUpdate();
                    if (ex>0) {
                        ps.close();
                        ClassAlert.infoAlert("succès !", "Succès");
                        ps.close();
                    }else{
                        ClassAlert.infoAlert("Erreur dans la base de données !", "Erreur");
                    }
        }} catch (SQLException ex) {
            Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void browseImage(ActionEvent event) {
        synchronized (this){
         FileChooser chooser = new FileChooser();
         FileChooser.ExtensionFilter extnsion = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
         chooser.getExtensionFilters().add(extnsion);
        
        file = chooser.showOpenDialog(btnImage.getScene().getWindow());
         
        
         if(file != null){
             
             try {
                 String path = file.getAbsolutePath();
                 textImage.setText(path);
                 path = file.toURI().toURL().toString();
                 Image img = new Image(path);
                 imageView.setImage(img);
                 
             } catch (MalformedURLException ex) {
                 Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
             }
            
              }
         else {
             System.out.println("No Data");
             
         }
    }
    }
    @FXML
    private void opOk(ActionEvent e) throws FileNotFoundException{
        updateAgence();
    }
    
}
