/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import locationcar.dataBase.DataBase;

/**
 *
 * @author Fodil
 */
public class LocationCar extends Application {
    DataBase base ;
    @Override
    public void start(Stage stage) {


        try {
            base = new DataBase();
            if (base.getStatAgence() == 1) {
                Parent root = FXMLLoader.load(this.getClass().getResource("/locationcar/view/dashboard.fxml")); 
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else{
                Parent root = FXMLLoader.load(this.getClass().getResource("/locationcar/view/initApp.fxml"));
                Scene scene = new Scene(root);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();

            }
            
        } catch (IOException ex) {
            Logger.getLogger(LocationCar.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }

 
    
}
