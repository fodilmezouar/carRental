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
 * @author computeruser
 */
public class Historique {
    private   IntegerProperty IDHistorique;
    private   StringProperty NomClient;
    private   StringProperty NumChassis;
    private   StringProperty ModeleVehicule;
    private   StringProperty DateSortie;
    private   StringProperty DateEntree;
    private   StringProperty HeureSortie;
    private   StringProperty HeureEntree;
    private   StringProperty DateRestitution;
    private   StringProperty HeureRestitution;
    private   IntegerProperty MontantApaye;
    private   IntegerProperty TarifSupp;

    public Historique() {
        this.IDHistorique = null;
        this.NomClient = null;
        this.NumChassis = null;
        this.ModeleVehicule =null;
        this.DateSortie = null;
        this.DateEntree = null;
        this.HeureSortie = null;
        this.HeureEntree = null;
        this.DateRestitution = null;
        this.HeureRestitution = null;
        this.MontantApaye = null;
        this.TarifSupp =null;
    }

    public Historique(int IDHistorique, String NomClient, String NumChassis, String ModeleVehicule, String DateSortie, String DateEntree, String HeureSortie, String HeureEntree, String DateRestitution, String HeureRestitution, int MontantApaye, int TarifSupp) {
        this.IDHistorique = new SimpleIntegerProperty(IDHistorique);
        this.NomClient = new SimpleStringProperty(NomClient);
        this.NumChassis = new SimpleStringProperty(NumChassis);
        this.ModeleVehicule = new SimpleStringProperty(ModeleVehicule);
        this.DateSortie = new SimpleStringProperty(DateSortie);
        this.DateEntree = new SimpleStringProperty(DateEntree);
        this.HeureSortie = new SimpleStringProperty(HeureSortie);
        this.HeureEntree = new SimpleStringProperty(HeureEntree);
        this.DateRestitution = new SimpleStringProperty(DateRestitution);
        this.HeureRestitution = new SimpleStringProperty(HeureRestitution);
        this.MontantApaye = new SimpleIntegerProperty(MontantApaye);
        this.TarifSupp = new SimpleIntegerProperty(TarifSupp);
    }

      public final int getIDHistorique() {
        return IDHistorique.get();
    }

    public final void setIDHistorique(int value) {
        IDHistorique.set(value);
    }

    public IntegerProperty IDHistoriqueProperty() {
        return IDHistorique;
    }

//-------------------------------------------------------------------------------
    public String getNomClient() {
        return NomClient.get();
    }

    public final void setNomClient(String NomClient) {
        this.NomClient.set(NomClient);
    }
    public StringProperty nomClientProperty() {
        return NomClient;
    }

//-------------------------------------------------------------------------------
    public String getNumChassis() {
        return NumChassis.get();
    }

    public final void setNumChassis(String val) {
        this.NumChassis.set(val);
    }
    public StringProperty numChassisProperty() {
        return NumChassis;
    }

//-------------------------------------------------------------------------------
     public String getModeleVehicule() {
        return ModeleVehicule.get();
    }

    public final void setModeleVehicule(String val) {
        this.ModeleVehicule.set(val);
    }
    public StringProperty modeleVehiculeProperty() {
        return ModeleVehicule;
    }

//-------------------------------------------------------------------------------
        public String getDateSortie() {
        return DateSortie.get();
    }

    public final void setDateSortie(String val) {
        this.DateSortie.set(val);
    }
    public StringProperty dateSortieProperty() {
        return DateSortie;
    }

//-------------------------------------------------------------------------------
           public String getDateEntree() {
        return DateEntree.get();
    }

    public final void setDateEntree(String val) {
        this.DateEntree.set(val);
    }
    public StringProperty dateEntreeProperty() {
        return DateEntree;
    }

//-------------------------------------------------------------------------------
    public final String getHeureEntree() {
        return HeureEntree.get();
    }

    public final void setHeureEntree(String value) {
        HeureEntree.set(value);
    }

    public StringProperty heureEntreeProperty() {
        return HeureEntree;
    }

//-------------------------------------------------------------------------------
  public final String getHeureSortie() {
        return HeureSortie.get();
    }

    public final void setHeureSortie(String value) {
        HeureSortie.set(value);
    }

    public StringProperty heureSortieProperty() {
        return HeureSortie;
    }

//-------------------------------------------------------------------------------
     public String getDateRestitution() {
        return DateRestitution.get();
    }

    public final void setDateRestitution(String val) {
        this.DateRestitution.set(val);
    }
    public StringProperty dateRestitutionProperty() {
        return DateRestitution;
    }

//-------------------------------------------------------------------------------
     public final String getHeureRestitution() {
        return HeureRestitution.get();
    }

    public final void setHeureRestitution(String value) {
        HeureRestitution.set(value);
    }

    public StringProperty heureRestitutionProperty() {
        return HeureRestitution;
    }

//-------------------------------------------------------------------------------
        public final int getTarifSupp() {
        return TarifSupp.get();
    }

    public final void setTarifSupp(int value) {
        TarifSupp.set(value);
    }

    public IntegerProperty tarifSuppProperty() {
        return TarifSupp;
    }

//-------------------------------------------------------------------------------
            public final int getMontantApaye() {
        return MontantApaye.get();
    }

    public final void setMontantApaye(int value) {
        MontantApaye.set(value);
    }

    public IntegerProperty montantApayeProperty() {
        return MontantApaye;
    }

//-------------------------------------------------------------------------------
    
    }

 
    
