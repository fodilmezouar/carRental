/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.locations;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import locationcar.Alert.ClassAlert;
import locationcar.Modele.Location;
import locationcar.Modele.VehiculeCmb;
import locationcar.dashboard.notification.NotificationController;
import locationcar.dataBase.DataBase;
import static locationcar.locations.LocationsController.LOCAL_DATE;
import static locationcar.locations.LocationsController.getKeyFromValue;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Fodil
 */
public class ModifierLocationController implements Initializable {
    private int idLocation;
    private DataBase base;
    private int  val;
    private HashMap<Integer,String> clientHM= new HashMap<>();
    private ArrayList clientNom = new ArrayList();
    private ArrayList clientNom2 = new ArrayList();
    private HashMap<Integer,String> clientHM2= new HashMap<>();
    private static java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
    ObservableList<VehiculeCmb> vehiculcm = FXCollections.observableArrayList();
    DateTimeFormatter f2 = DateTimeFormatter.ofPattern("HH:mm");

    @FXML
    private TextField Clienttf;
    @FXML
    private Label nomPrenom;
    @FXML
    private Label dateNaiss;
    @FXML
    private Label NPermis;
    @FXML
    private ComboBox<VehiculeCmb> CmbVehicules;
    @FXML
    private DatePicker DateSortie;
    @FXML
    private TextField HSortie;
    @FXML
    private TextField Nbrjour;
    @FXML
    private DatePicker DateEntree;
    @FXML
    private TextField Hentrer;
    @FXML
    private TextField TarifJour;
    @FXML
    private TextField Remise;
    @FXML
    private TextField Tva;
    @FXML
    private TextField MontantPay;
    int idClient =0,idVehicule=0;
    int idClientS=0;
    @FXML
    private Button btnClose;
    @FXML
    private CheckBox test2client;
    @FXML
    private HBox client2;
    @FXML
    private TextField Clienttf1;
    @FXML
    private Label nomPrenom1;
    @FXML
    private Label dateNaiss1;
    @FXML
    private Label NPermis1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        base = new DataBase();
         remplireVehiculeDisponible();
         selectId();
        Clienttf.textProperty().addListener((observable, oldValue, newValue) -> {
        onEnter();
});
         Clienttf1.textProperty().addListener((observable, oldValue, newValue) -> {
        onEnter2();
});
        autoComplete();
        autoComplete2();
           TarifJour.textProperty().addListener((observable, oldValue, newValue) -> {
                calculMontant();
               });
        Remise.textProperty().addListener((observable, oldValue, newValue) -> {
                calculMontant();
               });
        Tva.textProperty().addListener((observable, oldValue, newValue) -> {
                calculMontant();
               });
        Nbrjour.textProperty().addListener((observable, oldValue, newValue) -> {
                calculMontant();
               });
    } 
    private void onEnter(){
         String nomHM = Clienttf.getText();
         String sended=null;
         String[] full=null;
         if(nomHM.contains("/")){
             String[] fully = nomHM.split(" / ");
             sended = fully[0]+" "+fully[1];
             System.out.println("Sended : "+sended);

         }
         else
             sended=nomHM;
         int key=0;
         for (Integer name: clientHM.keySet()){

            String keyo =name.toString();
            String value = clientHM.get(name).toString();  
            System.out.println(keyo + " " + value);  


} 
        if (clientHM.containsValue(sended)) {
            key = (int) getKeyFromValue(clientHM, sended);
            System.out.println("Key : "+key);
           
        }
        try {
            String query = "SELECT Nom,Prenom,DateNaiss,NumPermis FROM Client WHERE "
                    + "IDClient = "+key;
            ResultSet rs = base.execQuery(query);
            String dateNaiss=null,nomPrenom=null;
            String numPermis="";
            while(rs.next()){
                numPermis = rs.getString("NumPermis");
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                nomPrenom = nom + " "+prenom;
                dateNaiss = rs.getString("DateNaiss");
            }
            this.nomPrenom.setText(nomPrenom);
            this.dateNaiss.setText(dateNaiss);
            this.NPermis.setText(numPermis);
            
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ModifierLocationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void autoComplete(){
        try {
            clientNom.clear();
            String query = "SELECT IDClient,Nom,Prenom FROM Client";
            ResultSet rs = base.execQuery(query);
            
            while(rs.next()){
                int idClient = rs.getInt("IDClient");
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                String nomPrenom = nom + " "+prenom;
                clientHM.put(idClient,nomPrenom);
                clientNom.add(nomPrenom);
            }
            
            TextFields.bindAutoCompletion(Clienttf,clientHM.values());
            
        } catch (SQLException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void remplireVehiculeDisponible(){
        CmbVehicules.getItems().clear();
        ObservableList<String>  list0 = FXCollections.observableArrayList();
        String qu = "select * from Vehicule WHERE statut = 0";
           ResultSet rs = base.execQuery(qu);
        try {
            while(rs.next()){
                vehiculcm.addAll(new VehiculeCmb(rs.getInt(1),rs.getString("Matricule")+"/"+rs.getString("modele"),rs.getInt("tarifJour")));
                        }
        } catch (SQLException ex) {
                Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                 CmbVehicules.setItems(vehiculcm);
        CmbVehicules.setConverter(new StringConverter<VehiculeCmb>() {
            @Override
            public String toString(VehiculeCmb object) {
              return object.getName();
                
            }

            @Override
            public VehiculeCmb fromString(String string) {
                return CmbVehicules.getItems().stream().filter(ap -> 
            ap.getName().equals(string)).findFirst().orElse(null);
    }
        });
    }
     private void selectId(){
        CmbVehicules.valueProperty().addListener((obs, oldval, newval) -> {
                    if(newval != null){
                        val = newval.getID();
                        TarifJour.setText(newval.getTarif()+"");
                    }
                });
    }
      void calculMontant() {
        int tarifJour,tva,remise,nbrjour;

        if (Remise.getText().isEmpty()) {
            remise =0;
        }else{
            remise = Integer.parseInt(Remise.getText());
        }
        if (TarifJour.getText().isEmpty()) {
            tarifJour =0;
        }else{
            tarifJour = Integer.parseInt(TarifJour.getText());
        }
       
        if (Tva.getText().isEmpty()) {
            tva =0;
        }else{
            tva = Integer.parseInt(Tva.getText());
        }
        if (Nbrjour.getText().isEmpty()) {
            nbrjour = 0;
        }else{
            nbrjour =Integer.parseInt(Nbrjour.getText());
        }
        int montantJourne = tarifJour * nbrjour;
        int montantRemise =( tarifJour * remise/100 );
        int montantPaye = montantJourne + (montantJourne-(montantRemise*nbrjour));
        MontantPay.setText(montantPaye+"");
        

    }
      private boolean AjoutDatanull() {
        return HSortie.getText().isEmpty() || Hentrer.getText().isEmpty() || Clienttf.getText().isEmpty() || MontantPay.getText().isEmpty()
                || DateEntree.getValue() == null || DateSortie.getValue() == null || Nbrjour.getText().isEmpty() || TarifJour.getText().isEmpty();
                
    }
         private void updateLocation(){
            if (AjoutDatanull() == true) {
            ClassAlert.errorAlert("Veuillez saisir les données !", "Erreur de saisie");
           

        
        }else
        {
             try {
            LocalTime HS = LocalTime.parse(HSortie.getText(), f2);
            LocalTime HE = LocalTime.parse(Hentrer.getText(), f2);
            
            } catch (Exception e) {
            ClassAlert.errorAlert("L'heure doit être sous format(HH:mm) !", "Erreur de saisie");
            return;

            }
   
            try {
              
                String sql =  "UPDATE  Location SET IDClient=?,IDVehicule=?,DateSortie=?,NombreJours=?,DateEntree=?,HeureSortie=?,"
                        + "HeureEntree=?,MontantApaye=?,Tva=?,remise=?,idClientS=? WHERE IDLocation = "+idLocation ;
                PreparedStatement ps = base.prepareStat(sql);
                String nomHM = Clienttf.getText();
                String sended=null;
                String[] full=null;
                if(nomHM.contains("/")){
             String[] fully = nomHM.split(" / ");
             sended = fully[0]+" "+fully[1];
             System.out.println("Sended : "+sended);

                }
                 else
             sended=nomHM;
                int key=0,key2=0;
                if (clientHM.containsValue(sended)) {
                    key = (int) getKeyFromValue(clientHM, sended);
                }
                
                ps.setInt(1, key);
                ps.setInt(2, val);
                ps.setString(3, Date.valueOf(DateSortie.getValue()).toString());
                ps.setInt(4, Integer.parseInt(Nbrjour.getText()));
                ps.setString(5, Date.valueOf(DateEntree.getValue()).toString());
                ps.setString(6, HSortie.getText());
                ps.setString(7, Hentrer.getText());
                ps.setInt(8, Integer.parseInt(MontantPay.getText()));
                if(!Tva.getText().isEmpty())
                 ps.setInt(9,Integer.parseInt(Tva.getText()));
               else
                    ps.setNull(9, java.sql.Types.INTEGER);
                if(!Remise.getText().isEmpty())
                 ps.setInt(10,Integer.parseInt(Remise.getText()));
               else
                    ps.setNull(10, java.sql.Types.INTEGER);
                String nomHM2 = Clienttf1.getText();
                if (clientHM2.containsValue(nomHM2)) {
                    key2 = (int) getKeyFromValue(clientHM2, nomHM2);
                }
                ps.setInt(11,key2);
                Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirm.setTitle("Confiramtion dialogue");
                alertConfirm.setHeaderText(" Confirmation de modification !");
                alertConfirm.setContentText("êtes-vous sûr ?");
                Optional<ButtonType> result = alertConfirm.showAndWait();
                if (result.get() == ButtonType.OK){
                    int ex = ps.executeUpdate();
                    if (ex>0) {
                        changeStat(idVehicule);
                        changeStat(val);
                        remplireVehiculeDisponible();
                        clearLocation(null);
                        ps.close();
                        ClassAlert.infoAlert("Location modifier avec succès !", "Succès");
                        Stage stage = (Stage) Clienttf.getScene().getWindow();
                             // do what you have to do
                         stage.close();
                
                        
                        
                    }else{
                        ClassAlert.infoAlert("There are something wrong in data base", "Erreur");
                    }

        }} catch (SQLException ex) {
                Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
            
        }}
    }
    public void getData( Location location){
            idLocation = location.getIDLocation();
            String query = "SELECT IDClient,idClientS,IDVehicule FROM Location WHERE IDLocation ="+idLocation;
            int nombreJour = location.getNbrJours();
            String dateSortie = location.getDateSortie();
            String dateEntrer = location.getDateEntree();
            String HeurEntrer = location.getHeureEntree();
            String HeurSortie = location.getHeureSortie();
            int montant = location.getMontantApayer();
            int tva = location.getTva();
            int remise = location.getRemise();
            
            String nomClient = location.getNomClient();
            String[] full = nomClient.split(" / ");
            String dateNaiss = "";
            String numPermis ="";
            String dateNaissS = "";
            String numPermisS ="";
            String matricule = "";
            String modele = "";
            String nomCS = "";
            String prenomCS = "";
            

        try {  
            ResultSet rs = base.execQuery(query);
            while(rs.next()){
                idClient = rs.getInt("IDClient");
                idVehicule = rs.getInt("IDVehicule");  
                idClientS = rs.getInt("idClientS"); 
            }
            rs.close();
            String quCLIENT = "SELECT DateNaiss,NumPermis FROM Client WHERE IDClient="+idClient;
            String quVEHICULE = "SELECT Matricule,modele,tarifJour FROM Vehicule WHERE IDVehicule="+idVehicule;
            
            ResultSet rsClient = base.execQuery(quCLIENT);
            ResultSet rsVehicule = base.execQuery(quVEHICULE);
            clientHM.put(idClient,full[0]+" "+full[1]);
            while (rsClient.next()) {
                dateNaiss = rsClient.getString("DateNaiss");
                numPermis = rsClient.getString("NumPermis");
                
            }
            rsClient.close();
            if(idClientS == 0){
                test2client.setSelected(false);
            }else{
                test2client.setSelected(true);
                client2.setDisable(false);
                String quCLIENTS = "SELECT DateNaiss,NumPermis,Nom,Prenom FROM Client WHERE IDClient="+idClientS;
                ResultSet rsClientS = base.execQuery(quCLIENTS);
                while (rsClientS.next()) {
                dateNaissS = rsClientS.getString("DateNaiss");
                numPermisS = rsClientS.getString("NumPermis");
                nomCS = rsClientS.getString("Nom");
                prenomCS = rsClientS.getString("Prenom");
                }
                clientHM2.put(idClientS,nomCS+" "+prenomCS);
                Clienttf1.setText(nomCS+" "+prenomCS);
            }
            while(rsVehicule.next()){
                matricule = rsVehicule.getString("Matricule");
                modele = rsVehicule.getString("modele");
                while (rsClient.next()) {
                dateNaiss = rsClient.getString("DateNaiss");
                numPermis = rsClient.getString("NumPermis");
                
            }
                
            }
            rsVehicule.close();
            if (dateEntrer!= null) {
            LocalDate date_entrer = LocationsController.LOCAL_DATE(dateEntrer);
            DateEntree.setValue(date_entrer);
        }
            if (dateSortie!= null) {
            LocalDate date_sortie = LocationsController.LOCAL_DATE(dateSortie);
            DateSortie.setValue(date_sortie);
        }
            int tarif = base.getTarif(idVehicule);
            CmbVehicules.setValue(new VehiculeCmb(idVehicule, matricule+"/"+modele, tarif));
            Clienttf.setText(nomClient);
            Hentrer.setText(HeurEntrer);
            HSortie.setText(HeurSortie);
            NPermis.setText(numPermis);
            nomPrenom.setText(nomClient);
            Nbrjour.setText(nombreJour+"");
            Tva.setText(tva+"");
            Remise.setText(remise+"");
            this.dateNaiss.setText(dateNaiss);
            TarifJour.setText(tarif+"");
            
           
            MontantPay.setText(montant+"");
        } catch (SQLException ex) {
            Logger.getLogger(ModifierLocationController.class.getName()).log(Level.ALL.SEVERE, null, ex);
        }
      
        
        
        
    }

     @FXML
    void heurAction() {
        HSortie.textProperty().addListener((observable, oldValue, newValue) -> {
            Hentrer.setText(HSortie.getText());
        });
    }

     private void changeStat(int id){
        try {
            int stat = base.statVehicule(id);
            String qu = "UPDATE Vehicule SET statut =";
            if (stat == 0) {
                qu +=1 + " WHERE IDVehicule = "+id;
            }else{
                qu +=0+" WHERE IDVehicule = "+id;
            }
            PreparedStatement ps = base.prepareStat(qu);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void clearLocation(ActionEvent event){
        ArrayList<TextField> tflist = new ArrayList<TextField>();
        tflist.add(Clienttf);
        tflist.add(Hentrer);
        tflist.add(HSortie);
        tflist.add(TarifJour);
        tflist.add(Remise);
        tflist.add(Nbrjour);
        tflist.add(Tva);
        tflist.add(MontantPay);
        
        for(TextField tf:tflist){
            tf.clear();
        }
        NPermis.setText("");
        dateNaiss.setText("");
        nomPrenom.setText("");
        DateEntree.setValue(null);
        CmbVehicules.setValue(null);
        
    }

 @FXML
    void dateCalc(ActionEvent event) {
        int nbrJour =0;
        if(!Nbrjour.getText().isEmpty()){
        try{
         nbrJour= Integer.parseInt(Nbrjour.getText());
        }catch(NumberFormatException num){
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, num);					
	}
        Calendar c =  Calendar.getInstance();
        LocalDate dateSortie = DateSortie.getValue();
       
        c.set(dateSortie.getYear(), dateSortie.getMonthValue()-1, dateSortie.getDayOfMonth()+nbrJour);
        java.util.Date date=c.getTime();
        LocalDate dateEntrer = LOCAL_DATE(dateFormat.format(date));
        DateEntree.setValue(dateEntrer);
        } 
        
    }
    @FXML
    private void dateCalc_v2(){
        Nbrjour.textProperty().addListener((observable, oldValue, newValue) -> {
            dateCalc(new ActionEvent());
        });
    }
      public static Object getKeyFromValue(HashMap hm, Object value) {
    for (Object o : hm.keySet()) {
      if (hm.get(o).equals(value)) {
        return o;
      }
    }
    return null;
  }

    @FXML
    private void confirmer(ActionEvent event) {
        updateLocation();
        
    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void actionClient2(ActionEvent event) {
        if(test2client.isSelected()){
            client2.setDisable(false);
        }else{
            client2.setDisable(true);
            Clienttf1.clear();
        }
        
    }
     private void autoComplete2(){
        try {
            clientNom2.clear();
            String query = "SELECT IDClient,Nom,Prenom FROM Client";
            ResultSet rs = base.execQuery(query);
            
            while(rs.next()){
                int idClient = rs.getInt("IDClient");
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                String nomPrenom = nom + " "+prenom;
                clientHM2.put(idClient,nomPrenom);
                clientNom2.add(nomPrenom);
            }
            rs.close();
            
            TextFields.bindAutoCompletion(Clienttf1,clientHM2.values());
            
        } catch (SQLException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      private void onEnter2(){
         String nomHM = Clienttf1.getText();
         int key=0;
        if (clientHM2.containsValue(nomHM)) {
            key = (int) getKeyFromValue(clientHM2, nomHM);
        }
        try {
            String query = "SELECT Nom,Prenom,DateNaiss,NumPermis FROM Client WHERE "
                    + "IDClient = "+key;
            ResultSet rs = base.execQuery(query);
            String dateNaiss=null,nomPrenom=null;
            int numPermis=0;
            while(rs.next()){
                numPermis = rs.getInt("NumPermis");
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                nomPrenom = nom + " "+prenom;
                dateNaiss = rs.getString("DateNaiss");
            }
            this.nomPrenom1.setText(nomPrenom);
            this.dateNaiss1.setText(dateNaiss);
            this.NPermis1.setText(numPermis+"");
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
