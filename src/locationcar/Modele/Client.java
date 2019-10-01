/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.Modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Fodil
 */
public class Client {
    private  IntegerProperty IdClient;
    private  StringProperty TypeClient;
    private  StringProperty NomSociete;
    private  StringProperty NomClient;
    private  StringProperty DateNaissanceClient;
    private  StringProperty TelephoneClient;
    private  StringProperty AdressCLient;
    private  String prenom;
    private  String datePermis;
    private  String numPermis;

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDatePermis() {
        return datePermis;
    }

    public void setDatePermis(String datePermis) {
        this.datePermis = datePermis;
    }

    public String getNumPermis() {
        return numPermis;
    }

    public void setNumPermis(String numPermis) {
        this.numPermis = numPermis;
    }
public Client() {
        this.IdClient =null;
        this.TypeClient = null;
        this.NomSociete = null;
        this.NomClient = null;
        this.DateNaissanceClient = null;
        this.TelephoneClient = null;
        this.AdressCLient = null;
        this.prenom = null;
        this.datePermis = null;
        this.numPermis = null;
    }
    public Client(int IdClient, String TypeClient,String NomSociete, String NomClient, String DateNaissanceClient, String TelephoneClient, String AdressCLient) {
        this.IdClient = new SimpleIntegerProperty(IdClient);
        this.TypeClient = new SimpleStringProperty(TypeClient);
        this.NomSociete = new SimpleStringProperty(NomSociete);
        this.NomClient = new SimpleStringProperty(NomClient);
        this.DateNaissanceClient = new SimpleStringProperty(DateNaissanceClient);
        this.TelephoneClient = new SimpleStringProperty(TelephoneClient);
        this.AdressCLient = new SimpleStringProperty(AdressCLient);
    }

   public final String getNomSociete() {
        return NomSociete.get();
    }

    public final void setNomSociete(String value) {
        NomSociete.set(value);
    }

    public StringProperty nomSocieteProperty() {
        return NomSociete;
    }

    public final int getIdClient() {
        return IdClient.get();
    }

    public final void setIdClient(int value) {
        IdClient.set(value);
    }

    public IntegerProperty IdClientProperty() {
        return IdClient;
    }

    public final String getTypeClient() {
        return TypeClient.get();
    }

    public final void setTypeClient(String value) {
        TypeClient.set(value);
    }

    public StringProperty TypeClientProperty() {
        return TypeClient;
    }

    public final String getNomClient() {
        return NomClient.get();
    }

    public  void setNomClient(String value) {
        NomClient.set(value);
    }

    public StringProperty NomClientProperty() {
        return NomClient;
    }

    public final String getDateNaissanceClient() {
        return DateNaissanceClient.get();
    }

    public  void setDateNaissanceClient(String value) {
        DateNaissanceClient.set(value);
    }

    public StringProperty DateNaissanceClientProperty() {
        return DateNaissanceClient;
    }

    public final String getTelephoneClient() {
        return TelephoneClient.get();
    }

    public  void setTelephoneClient(String value) {
        TelephoneClient.set(value);
    }

    public StringProperty TelephoneClientProperty() {
        return TelephoneClient;
    }

    public final String getAdressCLient() {
        return AdressCLient.get();
    }

    public  void setAdressCLient(String value) {
        AdressCLient.set(value);
    }

    public StringProperty AdressCLientProperty() {
        return AdressCLient;
    }
    


    
}
