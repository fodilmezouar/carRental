/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.vehicules;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import locationcar.Alert.ClassAlert;
import locationcar.Modele.Location;
import locationcar.Modele.Marque;
import locationcar.dataBase.DataBase;
import locationcar.locations.LocationsController;

/**
 * FXML Controller class
 *
 * @author computeruser
 */
public class MarquesController implements Initializable {
    private DataBase base;
    private  ObservableList<Marque> list ;

    @FXML
    private TextField marqueField;
    @FXML
    private Button supprimerMarque;
    @FXML
    private TableView<Marque> marqueTable;
    @FXML
    private TableColumn<Marque, Integer> idMarque_col;
    @FXML
    private TableColumn<Marque, String> nomMarque_col;

    /**
     * Initializes the controller class.
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        base  = new DataBase();
        initColumn();
        rowEvent();
        loadData();
    }    
    public void rowEvent(){
    marqueTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    if (newSelection != null) {
        supprimerMarque.setDisable(false);
    }
});
    }
    @FXML
    private void ajouter_Marque(ActionEvent event) {
        String marque = marqueField.getText();
        if (marque.isEmpty()) {
            ClassAlert.errorAlert("Veuillez saisir les données !", "Erreur de saisie");
            

        
        }else
        {
            try {
              
                String sql =  "INSERT INTO Marque (Constructeur) VALUES ('"+marque+"')";
                PreparedStatement ps = base.prepareStat(sql);               
                    int ex = ps.executeUpdate();
                    if (ex>0) {
                        loadData();
                        marqueField.setText("");
                        ps.close();
                        
                        
                    }else{
                        System.out.println("There are something wrong in data base");
                    }

        } catch (SQLException ex) {
                Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     private void initColumn() {
        idMarque_col.setCellValueFactory(new PropertyValueFactory<>("IDMarque"));
        nomMarque_col.setCellValueFactory(new PropertyValueFactory<>("Constructeur"));
        idMarque_col.setStyle( "-fx-alignment: CENTER;");
        nomMarque_col.setStyle( "-fx-alignment: CENTER;");
        
        
    }
     public void loadData() {
      
        list=FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT * FROM Marque";
        try {
            
            ResultSet rs = base.execQuery(qu);
            
            while(rs.next()){
                int idMarque = rs.getInt("IDMarque");
                String constructeur = rs.getString("Constructeur");
                list.add(new Marque(idMarque, constructeur));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        marqueTable.setItems(null);
        marqueTable.setItems(list);
        
    }
     @FXML
    private void supprimer_marque(ActionEvent event) {
        Marque selectedTodelete = marqueTable.getSelectionModel().getSelectedItem();
        int idMarque = selectedTodelete.getIDMarque();
        String qu = "DELETE FROM Marque WHERE IDMarque ="+idMarque ;
            if (base.execAction(qu)) {
                loadData();
                System.out.println("Supression avec Succes");
            }else{
                System.out.println("Supression avec fail");
            }
    }

    
}

