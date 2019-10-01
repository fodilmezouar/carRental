/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.dataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author Fodil
 */
public class DataBase {
    Connection conn =null; 
    Statement stm = null;
    PreparedStatement stmPrep = null;

    /**
     *
     * @return
     */
    public DataBase() {
        try{
           SQLiteConfig config = new SQLiteConfig(); //I add this configuration 
        config.enforceForeignKeys(true);  
     
            conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:RentalCar.sqlite",config.toProperties());
            System.out.println("connect to bdd");

        }catch(ClassNotFoundException | SQLException ex){
            System.out.println(ex);
            
        }
    }
    public static Connection DBConnector() {
        try{
           
            Connection conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:RentalCar.sqlite");
            System.out.println("connect to bdd");
            return conn;
        }catch(ClassNotFoundException | SQLException ex){
            System.out.println(ex);
            
        }
        return null;
    }
 
        /**
     *
     * @param query
     * @return
     */
    public PreparedStatement prepareStat(String query){
       
        try {
            stmPrep = conn.prepareStatement(query);
        } catch (SQLException ex) {
            System.out.println("Exeption at execQuery "+ex.getLocalizedMessage());
               return null;
        }
     return stmPrep;
    }
    public void changeStat(int id){
        try {
            int stat = statVehicule(id);
            String qu = "UPDATE Vehicule SET statut =";
            if (stat == 0) {
                qu +=1 + " WHERE IDVehicule = "+id;
            }else{
                qu +=0+" WHERE IDVehicule = "+id;
            }
            PreparedStatement ps = prepareStat(qu);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("erreur");
        }
    }
    public void setKilo(int id,int nouveau){
        try {
            String qu = "UPDATE Vehicule SET Kilometrage ="+nouveau+" WHERE IDVehicule ="+id+"";
            PreparedStatement ps = prepareStat(qu);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("erreur");
        }
    }
    public int getTarif(int id){
        int tarif =0;
        try {
            String query = "SELECT tarifJour FROM Vehicule WHERE IDVehicule ="+ id;
            
            ResultSet rst = execQuery(query);
            while(rst.next()){
                tarif = rst.getInt("tarifJour");
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tarif;
    }
    public int getMaxID(){
        int max = 0;
        try {
            String query = "Select max(IDLocation) as MaxID from Location";
            
            ResultSet rst = execQuery(query);
            while(rst.next()){
                max = rst.getInt("MaxID");
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return max;
    }
    public String getNumChassis(int id){
        String NumChassis =null;
        try {
            String query = "SELECT NumChassis FROM Vehicule WHERE IDVehicule ="+ id;
            
            ResultSet rst = execQuery(query);
            while(rst.next()){
                NumChassis = rst.getString("NumChassis");
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return NumChassis;
    }
    public int getKilometrage(int id){
        int kilo =0;
        try {
            String query = "SELECT Kilometrage FROM Vehicule WHERE IDVehicule ="+ id;
            
            ResultSet rst = execQuery(query);
            while(rst.next()){
                kilo = rst.getInt("Kilometrage");
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kilo;
    }
    public String getMatricule(int id){
        String Matricule =null;
        try {
            String query = "SELECT Matricule FROM Vehicule WHERE IDVehicule ="+ id;
            
            ResultSet rst = execQuery(query);
            while(rst.next()){
                Matricule = rst.getString("Matricule");
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Matricule;
    }
    public int getIdVehicule(int id){
        int idVehicule =0;
        try {
            String query = "SELECT IDVehicule FROM Location WHERE IDLocation ="+ id;
            
            ResultSet rst = execQuery(query);
            while(rst.next()){
                idVehicule = rst.getInt("IDVehicule");
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idVehicule;
    }
    public int getIDClient(int id){
        int idClient =0;
        try {
            String query = "SELECT IDClient FROM Location WHERE IDLocation ="+ id;
            
            ResultSet rst = execQuery(query);
            while(rst.next()){
                idClient = rst.getInt("IDClient");
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idClient;
    }
    public int getIDClient2(int id){
        int idClient =0;
        try {
            String query = "SELECT idClientS FROM Location WHERE IDLocation ="+ id;
            
            ResultSet rst = execQuery(query);
            while(rst.next()){
                idClient = rst.getInt("idClientS");
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idClient;
    }
    
    
    public int statVehicule(int id){
        int stat =0;
        try {
            String query = "SELECT statut FROM Vehicule WHERE IDVehicule ="+ id;
            
            ResultSet rst = execQuery(query);
            while(rst.next()){
                stat = rst.getInt("statut");
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stat;
    }
    
    public int getStatAgence(){
        int stat =0;
        try {
            String query = "SELECT statut FROM Agence";
            
            ResultSet rst = execQuery(query);
            while(rst.next()){
                stat = rst.getInt("statut");
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stat;
    }
    public String getFullname(int id){
        String fullName ="";
        try {
            String query = "SELECT Nom,Prenom FROM Client WHERE IDClient ="+ id;
            
            ResultSet rst = execQuery(query);
            while(rst.next()){
                fullName = rst.getString("Nom")+" / "+rst.getString("Prenom");
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fullName;
    }
        public String getModeleVehicule(int id){
        String modele ="";
        try {
            String query = "SELECT modele FROM Vehicule WHERE IDVehicule ="+ id;
            
            ResultSet rst = execQuery(query);
            while(rst.next()){
                modele = rst.getString("modele");
            }
            rst.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modele;
    }
          public int getIDMarque(String modele){
         String qu = "select IDMarque from Marque where Constructeur = '"+modele+"'";
         int IDReturned = 0;
           ResultSet rs = execQuery(qu);
        try {
            while(rs.next()){
                IDReturned = rs.getInt("IDMarque");

                        }
            rs.close();
        } catch (SQLException ex) {
                System.out.println("erreur!!");
        }
        return IDReturned;
    }
          public void deleteLocation(int id) {
        String qu = "DELETE FROM Location WHERE IDLocation ="+id ;
            
            if (execAction(qu)) {
                
                System.out.println("Supression avec Succes");
            }else{
                System.out.println("Supression avec fail");
            }
            
    }
    /**
     *
     * @param query
     */
  public ResultSet execQuery(String query){
        ResultSet result;
        try {
            this.stm =  conn.createStatement();
            result = stm.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exeption at execQuery "+ex.getLocalizedMessage());
               return null;
        } finally { 
        }
        return result;
    } 
      public void closeConnection() {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        public boolean execAction(String qu){
        try {
            stm = conn.createStatement();
            stm.execute(qu);
            return true;
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erreur" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exeption at execAction"+ex.getLocalizedMessage());
               return false;
        } finally { 
        }
    }

    
    
}
