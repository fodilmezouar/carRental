
package locationcar.Modele;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author MohammedYassineCherifi
 */

public class Location {
    private final IntegerProperty IDLocation;
    private final StringProperty NomClient;
    private final StringProperty NomVehicule;
    private final StringProperty dateSortie;
    private final IntegerProperty nbrJours;
    private final StringProperty dateEntree;
    private final StringProperty heureSortie;
    private final StringProperty heureEntree;
    private final IntegerProperty montantApayer;
    private final IntegerProperty tarifSupp;
    private final IntegerProperty tva;
    private final IntegerProperty remise;

  


    public Location() {
        this.IDLocation = null;
        this.NomClient = null;
        this.NomVehicule = null;
        this.dateSortie = null;
        this.nbrJours = null;
        this.dateEntree = null;
        this.heureSortie = null;
        this.heureEntree = null;
        this.montantApayer = null;
        this.tarifSupp = null;
        this.tva = null;
        this.remise = null;


    }


    

    public Location(int IDLocation, String NomClient, String nomVehicul,String dateSortie, int nbrJours, String dateEntree, String heureSortie, 
        String heureEntree,int montantApayer,int tarifSupp,int tva,int remise) {
        this.IDLocation = new SimpleIntegerProperty(IDLocation);
        this.NomClient = new SimpleStringProperty(NomClient);
        this.NomVehicule = new SimpleStringProperty(nomVehicul);
        this.dateSortie = new SimpleStringProperty(dateSortie);
        this.nbrJours = new SimpleIntegerProperty(nbrJours);
        this.dateEntree = new SimpleStringProperty(dateEntree);
        this.heureSortie = new SimpleStringProperty(heureSortie);
        this.heureEntree = new SimpleStringProperty(heureEntree);
        this.montantApayer = new SimpleIntegerProperty(montantApayer);
        this.tarifSupp = new SimpleIntegerProperty(tarifSupp);
        this.tva = new SimpleIntegerProperty(tva);
        this.remise = new SimpleIntegerProperty(remise);
    }

    public final int getIDLocation() {
        return IDLocation.get();
    }

    public final void setIDLocation(int value) {
        IDLocation.set(value);
    }

    public IntegerProperty IDLocationProperty() {
        return IDLocation;
    }

    public final String getNomClient() {
        return NomClient.get();
    }

    public final void setNomClient(String value) {
        NomClient.set(value);
    }

    public StringProperty NomClientProperty() {
        return NomClient;
    }

    public final String getNomVehicule() {
        return NomVehicule.get();
    }

    public final void setNomVehicule(String value) {
        NomVehicule.set(value);
    }

    public StringProperty NomVehiculeProperty() {
        return NomVehicule;
    }

    public final String getDateSortie() {
        return dateSortie.get();
    }

    public final void setDateSortie(String value) {
        dateSortie.set(value);
    }

    public StringProperty dateSortieProperty() {
        return dateSortie;
    }

    public final int getNbrJours() {
        return nbrJours.get();
    }

    public final void setNbrJours(int value) {
        nbrJours.set(value);
    }

    public IntegerProperty nbrJoursProperty() {
        return nbrJours;
    }

    public final String getDateEntree() {
        return dateEntree.get();
    }

    public final void setDateEntree(String value) {
        dateEntree.set(value);
    }

    public StringProperty dateEntreeProperty() {
        return dateEntree;
    }

    public final String getHeureSortie() {
        return heureSortie.get();
    }

    public final void setHeureSortie(String value) {
        heureSortie.set(value);
    }

    public StringProperty heureSortieProperty() {
        return heureSortie;
    }

    public final String getHeureEntree() {
        return heureEntree.get();
    }

    public final void setHeureEntree(String value) {
        heureEntree.set(value);
    }

    public StringProperty heureEntreeProperty() {
        return heureEntree;
    }

    public final int getMontantApayer() {
        return montantApayer.get();
    }

    public final void setMontantApayer(int value) {
        montantApayer.set(value);
    }

    public IntegerProperty montantApayerProperty() {
        return montantApayer;
    }

    public final int getTarifSupp() {
        return tarifSupp.get();
    }

    public final void setTarifSupp(int value) {
        tarifSupp.set(value);
    }

    public IntegerProperty tarifSuppProperty() {
        return tarifSupp;
    }
    public final int getTva() {
        return tva.get();
    }

    public final void setTva(int value) {
        tva.set(value);
    }

    public IntegerProperty tvaProperty() {
        return tva;
    }
    public final int getRemise() {
        return remise.get();
    }

    public final void setRemise(int value) {
        remise.set(value);
    }

    public IntegerProperty remiseProperty() {
        return remise;
    }

   




}