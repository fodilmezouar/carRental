/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.initApp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import locationcar.Alert.ClassAlert;
import locationcar.dataBase.DataBase;

/**
 * FXML Controller class
 *
 * @author Fodil
 */
public class InitAppController implements Initializable {
    private File file;
    private DataBase base;
    @FXML
    private TextField textImage;
    @FXML
    private TextField nom_Agence;
    @FXML
    private TextField Adress_Agence;
    @FXML
    private TextField telAgence;
    @FXML
    private TextField emailAgence;
    @FXML
    private Button image_file;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        base = new DataBase();
       
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
    private void insertAgence() throws FileNotFoundException{
        try {
            String path_image = textImage.getText();
            String nomAgence = nom_Agence.getText();
            String adressAgence = Adress_Agence.getText();
            String numTel = telAgence.getText();
            String email = emailAgence.getText();
            
            String sql =  "INSERT INTO Agence (Nom_Agence,Adresse,NumTel,Email,Logo,statut) "
                    + "VALUES(?,?,?,?,?,?)" ;
            PreparedStatement ps = base.prepareStat(sql);
            if (file!= null) {

                ps.setBytes(5,readFile(path_image));
            }else{
                ps.setNull(5, java.sql.Types.BLOB);
            }
            ps.setString(1, nomAgence);
            ps.setString(2, adressAgence);
            ps.setString(3,numTel);
            ps.setString(4,email);
            ps.setInt(6,1);
             Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirm.setTitle("Confiramtion dialog");
                alertConfirm.setHeaderText(" Confirmation d'ajout ! ");
                alertConfirm.setContentText("êtes-vous sûr ?");
                Optional<ButtonType> result = alertConfirm.showAndWait();
                if (result.get() == ButtonType.OK){
                    int ex = ps.executeUpdate();
                    if (ex>0) {
                        ClassAlert.infoAlert("succès !", "Succès");

                    }else{
                        ClassAlert.infoAlert("Erreur dans la base de données !", "Erreur");
                    }
        }} catch (SQLException ex) {
            Logger.getLogger(InitAppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void browseImage(ActionEvent event) {
        synchronized (this){
         FileChooser chooser = new FileChooser();
         //fileChooser.setCurrentDirectory(new File());
         FileChooser.ExtensionFilter extnsion = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
         chooser.getExtensionFilters().add(extnsion);
        
        file = chooser.showOpenDialog(image_file.getScene().getWindow());
         
        
         if(file != null){
             
             String path = file.getAbsolutePath();
             textImage.setText(path);
            
              }
         else {
             System.out.println("No Data");
             
         }
    }
    }
    void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/locationcar/view/dashboard.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Location Voiture");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {

        }
    }
    @FXML
    private void operationAnuller(ActionEvent event) {
    }

    @FXML
    private void operationOk(ActionEvent event) {
        try {
            insertAgence();
            ((Stage) Adress_Agence.getScene().getWindow()).close();
            loadMain();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InitAppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
