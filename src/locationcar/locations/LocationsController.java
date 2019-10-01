/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.locations;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import locationcar.Alert.ClassAlert;
import locationcar.Modele.Location;
import locationcar.Modele.VehiculeCmb;
import locationcar.PrintReport;
import locationcar.dataBase.DataBase;
import locationcar.locations.OptionsLocation.ProlongerController;
import locationcar.locations.OptionsLocation.RendreController;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Fodil
 */
public class LocationsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private DataBase base;
    private  ObservableList<Location> list ;
    private boolean listeComplet = false ;
    private ArrayList clientNom = new ArrayList();
    private HashMap<Integer,String> clientHM= new HashMap<>();
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

    @FXML
    private TableView<Location> Table;
    


    @FXML
    private TableColumn<Location, Integer> Id_col;

    @FXML
    private TableColumn<Location, String> Nom_col;

    @FXML
    private TableColumn<Location, String> Vehicul_col;

    @FXML
    private TableColumn<Location, String> Date_sortie_col;

    @FXML
    private TableColumn<Location, String> Nombrejr_col;

    @FXML
    private TableColumn<Location, String> Date_entrer;

    @FXML
    private TableColumn<Location, String> Hsortie_col;

    @FXML
    private TableColumn<Location, String> Hentrer_col;

    @FXML
    private TableColumn<Location, String> Montant_col;

    @FXML
    private TextField searchField;
    private static int  val;
    private static int  val2;
    int idLocation;
    int idClient;
    int idVehicule;
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        base = new DataBase();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        HSortie.setText(LocalTime.now().format(dtf).toString());
        Hentrer.setText(LocalTime.now().format(dtf).toString());
        remplireVehiculeDisponible();
        DateSortie.setValue(LocalDate.now());
        Clienttf.textProperty().addListener((observable, oldValue, newValue) -> {
        onEnter();
});
          Clienttf1.textProperty().addListener((observable, oldValue, newValue) -> {
        onEnter2();
});
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchFonction();
               });
        selectId();
        initColumn();
        loadData();
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
    private void selectId(){
        CmbVehicules.valueProperty().addListener((obs, oldval, newval) -> {
                    if(newval != null){
                        val = newval.getID();
                        TarifJour.setText(newval.getTarif()+"");
                    }
                });
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
            rs.close();
            
            TextFields.bindAutoCompletion(Clienttf,clientHM.values());
            
        } catch (SQLException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void onEnter(){
         String nomHM = Clienttf.getText();
         int key=0;
        if (clientHM.containsValue(nomHM)) {
            key = (int) getKeyFromValue(clientHM, nomHM);
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
            this.nomPrenom.setText(nomPrenom);
            this.dateNaiss.setText(dateNaiss);
            this.NPermis.setText(numPermis+"");
            rs.close();
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
    
    private void remplireVehiculeDisponible(){
        CmbVehicules.getItems().clear();
        ObservableList<String>  list0 = FXCollections.observableArrayList();
        String qu = "select * from Vehicule WHERE statut = 0";
           ResultSet rs = base.execQuery(qu);
        try {
            while(rs.next()){
                vehiculcm.addAll(new VehiculeCmb(rs.getInt(1),rs.getString("Matricule")+"/"+rs.getString("modele"),rs.getInt("tarifJour")));
                        }
            rs.close();
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
    @FXML
    private void report(ActionEvent event) throws IOException {
        try {
            Location selected = Table.getSelectionModel().getSelectedItem();
            int idLocation = selected.getIDLocation();
            int idVehicule = base.getIdVehicule(idLocation);
            int idClient = base.getIDClient(idLocation);
            int idClient2 = base.getIDClient(idLocation);
            BufferedImage bufImg = null;
            String query ="SELECT Nom_Agence,Adresse,NumTel,Email,Logo FROM Agence";
            ResultSet rsAgence = base.execQuery(query);
            String nomAG = "",adressAgen = "",numTel ="",email="";
            byte[] imageBytes=null;
            while(rsAgence.next()){
                nomAG = rsAgence.getString("Nom_Agence");
                adressAgen = rsAgence.getString("Adresse");
                numTel = rsAgence.getString("NumTel");
                email = rsAgence.getString("Email");
                imageBytes = rsAgence.getBytes("Logo");
            }
            rsAgence.close();
            InputStream input = new ByteArrayInputStream(imageBytes);
            bufImg = ImageIO.read(input);
            PrintReport view  = new PrintReport();
            input.close();
            view.previewFacture(idLocation,idClient,idVehicule,nomAG,numTel,email,adressAgen,bufImg);
        } catch (SQLException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
       
        
        if (Nbrjour.getText().isEmpty()) {
            nbrjour = 0;
        }else{
            nbrjour =Integer.parseInt(Nbrjour.getText());
        }
        int montantJourne = tarifJour * nbrjour;
        int montantRemise =( tarifJour * remise/100 );
        int montantPaye = montantJourne -(montantRemise*nbrjour);
        MontantPay.setText(montantPaye+"");
        

    }
     private void insertLocation() throws IOException{
 
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
            if(DateSortie.getValue()!=null && DateEntree.getValue()!=null){
                    long daysBetween = ChronoUnit.DAYS.between(DateSortie.getValue(),DateEntree.getValue() );
                    if(daysBetween<0){
                    ClassAlert.errorAlert("{Date entrée} doit être supérieure à {Date sortie}!", "Erreur de saisie"); 
                    return;
                    }
                }
            try {
              
                String sql =  "INSERT INTO Location (IDClient,IDVehicule,DateSortie,NombreJours,DateEntree,HeureSortie,"
                        + "HeureEntree,MontantApaye,Tva,remise,idClientS) VALUES(?,?,?,?,?,?,?,?,?,?,?)" ;
                PreparedStatement ps = base.prepareStat(sql);
                String nomHM = Clienttf.getText();
                String nomHM2 = Clienttf1.getText();
                int key=0,key2=0;
                if (clientHM.containsValue(nomHM)) {
                    key = (int) getKeyFromValue(clientHM, nomHM);
                }
                if (clientHM2.containsValue(nomHM2)) {
                    key2 = (int) getKeyFromValue(clientHM2, nomHM2);
                }
                ps.setInt(1, key);
                ps.setInt(2, val);
                ps.setString(3, Date.valueOf(DateSortie.getValue()).toString());
                ps.setInt(4, Integer.parseInt(Nbrjour.getText()));
                ps.setString(5, Date.valueOf(DateEntree.getValue()).toString());
                ps.setString(6,HSortie.getText());
                ps.setString(7,Hentrer.getText());
                ps.setInt(8, Integer.parseInt(MontantPay.getText()));  
                if(!Tva.getText().isEmpty())
                 ps.setInt(9,Integer.parseInt(Tva.getText()));
               else
                    ps.setNull(9, java.sql.Types.INTEGER);
                if(!Remise.getText().isEmpty())
                 ps.setInt(10,Integer.parseInt(Remise.getText()));
               else
                    ps.setNull(10, java.sql.Types.INTEGER);
                ps.setInt(11, key2);
                Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirm.setTitle("Confiramtion dialogue");
                alertConfirm.setHeaderText(" Confirmation d'ajout !");
                alertConfirm.setContentText("êtes-vous sûr ?");
                Optional<ButtonType> result = alertConfirm.showAndWait();
                if (result.get() == ButtonType.OK){
                    int ex = ps.executeUpdate();
                    if (ex>0) {
                        ps.close();
                        changeStat(val);
                        remplireVehiculeDisponible();
                        clearLocation(null);
                        loadData();
                           Alert alertConfirM = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirM.setTitle("Confiramtion dialogue");
                alertConfirM.setHeaderText("Impression de facture!");
                alertConfirM.setContentText("Voulez-vous imprimer la facture ?");
                Optional<ButtonType> resulT = alertConfirM.showAndWait();
                if (resulT.get() == ButtonType.OK){
                      try {
           
            BufferedImage bufImg = null;
            String query ="SELECT Nom_Agence,Adresse,NumTel,Email,Logo FROM Agence";
            ResultSet rsAgence = base.execQuery(query);
            String nomAG = "",adressAgen = "",numTel ="",email="";
            byte[] imageBytes=null;
            while(rsAgence.next()){
                nomAG = rsAgence.getString("Nom_Agence");
                adressAgen = rsAgence.getString("Adresse");
                numTel = rsAgence.getString("NumTel");
                email = rsAgence.getString("Email");
                imageBytes = rsAgence.getBytes("Logo");
            }
            rsAgence.close();
            InputStream input = new ByteArrayInputStream(imageBytes);
            bufImg = ImageIO.read(input);
            PrintReport view  = new PrintReport();
            view.previewFacture(base.getMaxID(),key,val,nomAG,numTel,email,adressAgen,bufImg);
        } catch (SQLException eX) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
                    }
                   ClassAlert.infoAlert("Location enregistrée avec succès !", "Succès");
                       
                    }else{
                        ClassAlert.infoAlert("There are something wrong in data base", "Erreur");
                    }

        }} catch (SQLException ex) {
                Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
     private void searchFonction() {
             String search = searchField.getText();
             list=FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT * FROM Location NATURAL JOIN (select IDVehicule,modele from Vehicule) NATURAL JOIN "
                + "(SELECT IDClient,Nom,Prenom FROM Client) WHERE Nom like '%"+search+"%' "
                + " OR Prenom like '%"+search+"%' OR modele like '%"+search+"%';";
        try {
            
            ResultSet rs = base.execQuery(qu);
            
            while(rs.next()){
                int idLocation = rs.getInt("IDLocation");
                int idVehicule = rs.getInt("IDVehicule");
                int idClient = rs.getInt("IDClient");
                String dateSortie = rs.getString("DateSortie");
                String DateEntree = rs.getString("DateEntree");
                int NombreJour = rs.getInt("NombreJours");
                String HeureEntree = rs.getString("HeureEntree");
                String HeureSortie = rs.getString("HeureSortie");
                int TarifSupp = rs.getInt("TarifSupp");
                int MontantApaye = rs.getInt("MontantApaye");
                int tva = rs.getInt("Tva");
                int remise = rs.getInt("remise");
                String modele = base.getModeleVehicule(idVehicule);
                String fullname = base.getFullname(idClient);
                list.add(new Location(idLocation, fullname, modele,
                dateSortie,NombreJour,DateEntree,HeureSortie,HeureEntree,MontantApaye,TarifSupp,tva,remise));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table.setItems(null);
        Table.setItems(list);
             
             
             
             
             
         }
     private void initColumn() {
        Id_col.setCellValueFactory(new PropertyValueFactory<>("IDLocation"));
        Nom_col.setCellValueFactory(new PropertyValueFactory<>("NomClient"));
        Callback<TableColumn<Location, String>, TableCell<Location, String>> cellFactory =
        new Callback<TableColumn<Location, String>, TableCell<Location, String>>() {
            public TableCell call(TableColumn p) {
                TableCell cell = new TableCell<Location, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                          
                        setText(getString());
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
                
                            long now = System.currentTimeMillis();
                            Date date1= new Date(now);
                        try {
                            java.util.Date dateEn = formatter.parse(getString());
                            if (date1.after(dateEn)) {
                                setStyle("-fx-background-color:red ");
                            }else{
                                setStyle("-fx-background-color:white ");
                                setTextFill(Color.BLACK);
                            }
                        
                            //setStyle("-fx-background-color: ");
                        } catch (ParseException ex) {
                            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                            
                       
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };


                return cell;
            }
        };
       Date_entrer.setCellFactory(cellFactory);
        Date_entrer.setCellValueFactory(new PropertyValueFactory<>("dateEntree"));
        Date_sortie_col.setCellValueFactory(new PropertyValueFactory<>("dateSortie"));
        Hentrer_col.setCellValueFactory(new PropertyValueFactory<>("heureEntree"));
        Hsortie_col.setCellValueFactory(new PropertyValueFactory<>("heureSortie"));
        Nombrejr_col.setCellValueFactory(new PropertyValueFactory<>("nbrJours"));
        Montant_col.setCellValueFactory(new PropertyValueFactory<>("montantApayer"));
        Vehicul_col.setCellValueFactory(new PropertyValueFactory<>("NomVehicule"));
        
    }
     public void loadData() {
      
        list=FXCollections.observableArrayList();
        list.clear();
        String qu = "SELECT * FROM Location";
        try {
            
            ResultSet rs = base.execQuery(qu);
            
            while(rs.next()){
                int idLocation = rs.getInt("IDLocation");
                int idVehicule = rs.getInt("IDVehicule");
                int idClient = rs.getInt("IDClient");
                String dateSortie = rs.getString("DateSortie");
                String DateEntree = rs.getString("DateEntree");
                int NombreJour = rs.getInt("NombreJours");
                String HeureEntree = rs.getString("HeureEntree");
                String HeureSortie = rs.getString("HeureSortie");
                int TarifSupp = rs.getInt("TarifSupp");
                int MontantApaye = rs.getInt("MontantApaye");
                int tva = rs.getInt("Tva");
                int remise = rs.getInt("remise");
                String modele = base.getModeleVehicule(idVehicule);
                String fullname = base.getFullname(idClient);
                list.add(new Location(idLocation, fullname, modele,
                dateSortie,NombreJour,DateEntree,HeureSortie,HeureEntree,MontantApaye,TarifSupp,tva,remise));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table.setItems(null);
        Table.setItems(list);
        
    }
    @FXML
    private void confirmer(ActionEvent e) throws IOException{
           insertLocation();
       
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
 /**
     *
     * @param dateString
     * @return
     */
    public static final LocalDate LOCAL_DATE (String dateString){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
     
}
    
    @FXML
    void heurAction() {
        HSortie.textProperty().addListener((observable, oldValue, newValue) -> {
            Hentrer.setText(HSortie.getText());
        });
    }

    private boolean AjoutDatanull() {
        return HSortie.getText().isEmpty() || Hentrer.getText().isEmpty() ||Clienttf.getText().isEmpty() || MontantPay.getText().isEmpty()
                || DateEntree.getValue() == null || DateSortie.getValue() == null || Nbrjour.getText().isEmpty() || TarifJour.getText().isEmpty()
                || CmbVehicules.getSelectionModel().getSelectedIndex()<0;
                
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
    private void modifier_location(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/locationcar/view/ModifierLocation.fxml"));
         try {
            Location selectedForEdit = Table.getSelectionModel().getSelectedItem();
            if (selectedForEdit == null) {
                System.out.println("hh");
                return;
            }
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            ModifierLocationController controler = fxmlLoader.getController();
            controler.getData(selectedForEdit);
            Stage nStage = new Stage();
//            addProductController.addSupplyerStage(nStage);
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
            nStage.setOnHiding( eventt -> {loadData();} );

        } catch (IOException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void prolonger(ActionEvent event) {
         FXMLLoader fxmlLoader = new FXMLLoader();
         fxmlLoader.setLocation(getClass().getResource("/locationcar/view/prolonger.fxml"));
         try {
            Location selectedForEdit = Table.getSelectionModel().getSelectedItem();
            if (selectedForEdit == null) {
                System.out.println("hh");
                return;
            }
           fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            ProlongerController controler = fxmlLoader.getController();
            controler.getData(selectedForEdit);
            Stage nStage = new Stage();
//            addProductController.addSupplyerStage(nStage);
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
            nStage.setOnHiding( eventt -> {loadData();} );

        } catch (IOException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void supprimer(ActionEvent event) {
             Location selectedTodelete = Table.getSelectionModel().getSelectedItem();
        int idLocation = selectedTodelete.getIDLocation();
        int idVehicule = base.getIdVehicule(idLocation);
        changeStat(idVehicule);
        String qu = "DELETE FROM Location WHERE IDLocation ="+idLocation ;
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Confiramtion dialog");
            alertConfirm.setHeaderText(" Confirmation de suppression ! ");
            alertConfirm.setContentText("êtes-vous sûr ?");
            Optional<ButtonType> result = alertConfirm.showAndWait();
            if (result.get() == ButtonType.OK){
            if (base.execAction(qu)) {
                
                remplireVehiculeDisponible();
                ClassAlert.infoAlert("Supression avec Succes","Succes");
                loadData();
            }else{
                ClassAlert.infoAlert("Supression avec fail","fail");
            }
            }
    }

    @FXML
    private void rendre(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/locationcar/view/rendre.fxml"));
        try {
            Location selectedForEdit = Table.getSelectionModel().getSelectedItem();
            if (selectedForEdit == null) {
                System.out.println("hh");
                return;
            }
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            RendreController controler = fxmlLoader.getController();
            controler.getData(selectedForEdit);
            Stage nStage = new Stage();
            nStage.setScene(scene);
            nStage.initModality(Modality.APPLICATION_MODAL);
            nStage.initStyle(StageStyle.TRANSPARENT);
            nStage.show();
            nStage.setOnHiding( eventt -> {loadData();} );

        } catch (IOException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void actionClient2(ActionEvent event) {
        if(test2client.isSelected()){
            client2.setDisable(false);
        }else{
            client2.setDisable(true);
        }
        
    }

    @FXML
    private void reportCondition(ActionEvent event) throws IOException {
         try {
            Location selected = Table.getSelectionModel().getSelectedItem();
            int idLocation = selected.getIDLocation();
            int idVehicule = base.getIdVehicule(idLocation);
            int idClient = base.getIDClient(idLocation);
            int idClient2 = base.getIDClient(idLocation);
            BufferedImage bufImg = null;
            String query ="SELECT Nom_Agence,Adresse,NumTel,Email,Logo FROM Agence";
            ResultSet rsAgence = base.execQuery(query);
            String nomAG = "",adressAgen = "",numTel ="",email="";
            byte[] imageBytes=null;
            while(rsAgence.next()){
                nomAG = rsAgence.getString("Nom_Agence");
                adressAgen = rsAgence.getString("Adresse");
                numTel = rsAgence.getString("NumTel");
                email = rsAgence.getString("Email");
                imageBytes = rsAgence.getBytes("Logo");
            }
            rsAgence.close();
            InputStream input = new ByteArrayInputStream(imageBytes);
            bufImg = ImageIO.read(input);
            PrintReport view  = new PrintReport();
            input.close();
            view.previewFactureCondition(idLocation,idClient,idVehicule,nomAG,numTel,email,adressAgen,bufImg);
        } catch (SQLException ex) {
            Logger.getLogger(LocationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
}
