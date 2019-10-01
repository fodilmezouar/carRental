/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;
import locationcar.PrintReport;
import locationcar.dashboard.notification.NotificationController;
import org.controlsfx.dialog.ProgressDialog;

/**
 * FXML Controller class
 *
 * @author Fodil
 */
public class DashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane Anchor;
    @FXML
    private Button Accueil;
    @FXML
    private Label notification;
    @FXML
    private VBox menu;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        goToHome(null);
        noti();
     
  
    }  
    @FXML
    private void goToHome(ActionEvent e){
                noti();

        refreshNodes();
         
    }
    public void noti(){
        NotificationController notControle = new NotificationController();
        int nbrNotification = notControle.nbrNotif();
        System.out.println(nbrNotification);
        if (nbrNotification >0) {
            notification.setVisible(true);
            notification.setText(nbrNotification+"");
        }else{
            notification.setVisible(false);
        }
    }
    @FXML
    private void goToclients(ActionEvent e){
                noti();

         Anchor.getChildren().clear();
        
        Node node;
        
        
            try {
               node = (Node)FXMLLoader.load(this.getClass().getResource("/locationcar/view/clients.fxml"));
               AnchorPane.setBottomAnchor(node, 0.0);
               AnchorPane.setTopAnchor(node, 0.0);
               AnchorPane.setLeftAnchor(node, 0.0);
               AnchorPane.setRightAnchor(node, 0.0);
               Anchor.getChildren().add(node);
                
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    @FXML
    private void goToVehicules(ActionEvent e){
                noti();

         Anchor.getChildren().clear();
        
        Node node;
        
        
            try {
               node = (Node)FXMLLoader.load(this.getClass().getResource("/locationcar/view/vehicules.fxml"));
               AnchorPane.setBottomAnchor(node, 0.0);
               AnchorPane.setTopAnchor(node, 0.0);
               AnchorPane.setLeftAnchor(node, 0.0);
               AnchorPane.setRightAnchor(node, 0.0);
               Anchor.getChildren().add(node);
                
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    private void refreshNodes()
    {
                noti();

        Anchor.getChildren().clear();
        Node node;
        
        
            try {
               node = (Node)FXMLLoader.load(this.getClass().getResource("/locationcar/view/home.fxml"));
               AnchorPane.setBottomAnchor(node, 0.0);
               AnchorPane.setTopAnchor(node, 0.0);
               AnchorPane.setLeftAnchor(node, 0.0);
               AnchorPane.setRightAnchor(node, 0.0);
               Anchor.getChildren().add(node);
                
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }    
    }
    
    @FXML
    private void goToLocation(ActionEvent e){
                noti();

         Anchor.getChildren().clear();
        
        Node node;
        
        
            try {
               node = (Node)FXMLLoader.load(this.getClass().getResource("/locationcar/view/locations.fxml"));
               AnchorPane.setBottomAnchor(node, 0.0);
               AnchorPane.setTopAnchor(node, 0.0);
               AnchorPane.setLeftAnchor(node, 0.0);
               AnchorPane.setRightAnchor(node, 0.0);
               Anchor.getChildren().add(node);
                
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    @FXML
    private void goToHistoriques(ActionEvent event) {
                noti();

        Anchor.getChildren().clear();
        
        Node node;
        
        
            try {
               node = (Node)FXMLLoader.load(this.getClass().getResource("/locationcar/view/historique.fxml"));
               AnchorPane.setBottomAnchor(node, 0.0);
               AnchorPane.setTopAnchor(node, 0.0);
               AnchorPane.setLeftAnchor(node, 0.0);
               AnchorPane.setRightAnchor(node, 0.0);
               Anchor.getChildren().add(node);
                
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    @FXML
    private void goToNoti(ActionEvent event) {
                noti();

          Anchor.getChildren().clear();
        Node node;
            try {
               node = (Node)FXMLLoader.load(this.getClass().getResource("/locationcar/view/notification.fxml"));
               AnchorPane.setBottomAnchor(node, 0.0);
               AnchorPane.setTopAnchor(node, 0.0);
               AnchorPane.setLeftAnchor(node, 0.0);
               AnchorPane.setRightAnchor(node, 0.0);
               Anchor.getChildren().add(node);
                
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @FXML
    private void goToParametre(ActionEvent event) {
                noti();

        Anchor.getChildren().clear();
        Node node;
            try {
               node = (Node)FXMLLoader.load(this.getClass().getResource("/locationcar/view/setting.fxml"));
               AnchorPane.setBottomAnchor(node, 0.0);
               AnchorPane.setTopAnchor(node, 0.0);
               AnchorPane.setLeftAnchor(node, 0.0);
               AnchorPane.setRightAnchor(node, 0.0);
               Anchor.getChildren().add(node);
                
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
/*Task<Object> task = new Task<Object>() {
    @Override 
    protected Object call() throws Exception {
        for (int i = 0; i < 100; i++) {
            updateProgress(i, 99);
            updateMessage("progress : "+i);
            Thread.sleep(100);
        }
        return null;
      
        }
       
    


};
        ProgressDialog ps = new ProgressDialog(task);
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    */

   }
    
}
