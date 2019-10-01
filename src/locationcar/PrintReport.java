/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.scene.image.Image;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import locationcar.Modele.Client;
import locationcar.dataBase.DataBase;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author computeruser
 */
public class PrintReport extends JFrame {
    Connection conn = DataBase.DBConnector();
    PreparedStatement ps = null;
    DataBase base = new DataBase();
    String nom="/",prenom="/",adress="/",numPermis="/",datPermis="/",dateNaiss="/",tlfn="/",lieuN="/";
    ResultSet rs= null;
    public void previewFacture(int IDLocation,int IDClient,int IDVehicule,String nomAgence,String numTlf,String email,String adresse,BufferedImage logo) throws SQLException {
        try{
            String queryCL2 = "select Nom,Prenom,DateNaiss,LieuNaiss,NumTel,NumPermis,DatePermis,Adresse"
                   + " from Client,Location where Location.IDLocation ="+IDLocation+" and Client.IDClient = Location.idClientS";
            ResultSet rsCl = base.execQuery(queryCL2);
            while(rsCl.next()){
                nom=rsCl.getString("Nom");
                prenom=rsCl.getString("Prenom");
                adress=rsCl.getString("Adresse");
                numPermis=rsCl.getString("NumPermis");
                datPermis=rsCl.getString("DatePermis");
                tlfn=rsCl.getString("NumTel");
                dateNaiss=rsCl.getString("DateNaiss");
                lieuN = rsCl.getString("LieuNaiss");
            }
            rsCl.close();
            HashMap param = new HashMap();
            param.put("nomAgence", nomAgence);
            param.put("telAgence", numTlf);
            param.put("adresseAgence",adresse);
            param.put("emailAgence", email);
            param.put("idLocation",IDLocation);
            param.put("idClient", IDClient);
            param.put("idVehicule",IDVehicule);
            param.put("logoLocation", logo);
            param.put("nomCl", nom);
            param.put("prenomCl", prenom);
            param.put("adressCl",adress);
            param.put("numPermisCl", numPermis);
            param.put("datePermisCl",datPermis);
            param.put("tlfn", tlfn);
            param.put("dateNaissCl", dateNaiss);
            param.put("lieuNaissCl", lieuN);
     
            JasperPrint print  = JasperFillManager.fillReport("report1.jasper", param,conn);
            
            JRViewer viewer = new JRViewer(print);
            viewer.setOpaque(true);
            viewer.setVisible(true);
            this.add(viewer);
            this.setExtendedState( this.getExtendedState()|JFrame.MAXIMIZED_BOTH );           
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        
        }catch(JRException e){
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    
    
    }
    public void previewFactureCondition(int IDLocation,int IDClient,int IDVehicule,String nomAgence,String numTlf,String email,String adresse,BufferedImage logo) throws SQLException {
        try{
           
            HashMap param = new HashMap();
            param.put("nomAgence", nomAgence);
            param.put("telAgence", numTlf);
            param.put("adresseAgence",adresse);
            param.put("emailAgence", email);
            param.put("idLocation",IDLocation);
            param.put("idClient", IDClient);
            param.put("idVehicule",IDVehicule);
            param.put("logoLocation", logo);
          
     
             JasperPrint print  = JasperFillManager.fillReport("Condition.jasper", param,conn);

            
            JRViewer viewer = new JRViewer(print);
            viewer.setOpaque(true);
            viewer.setVisible(true);
            this.add(viewer);
            this.setExtendedState( this.getExtendedState()|JFrame.MAXIMIZED_BOTH );           
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        
        }catch(JRException e){
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    
    
    }
}
