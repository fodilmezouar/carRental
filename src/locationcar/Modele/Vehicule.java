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
 * @author MohammedYassineCherifi
 */

public class Vehicule {
    private final IntegerProperty IDVehicule;
    private final IntegerProperty IDMarque;
    private final StringProperty nameMarque;
    private final StringProperty numChassis;
    private final StringProperty matricule;
    private final StringProperty lastAssur;
    private final StringProperty nextAssur;
    private final StringProperty lastScanner;
    private final StringProperty nextScanner;
    private final IntegerProperty kilometrage;
    private final IntegerProperty prochVidange;
    private final StringProperty carburantActuel;
    private final StringProperty modele;
    private final StringProperty remarques;
    private final IntegerProperty lastChaine;
    private final IntegerProperty prochChaine;
    private final IntegerProperty tarifJour;
    private final StringProperty statut;


    public Vehicule() {
          this.IDVehicule=null;
          this.IDMarque=null;
          this.numChassis=null;
          this.matricule=null;
          this.lastAssur=null;
          this.nextAssur=null;
          this.lastScanner=null;
          this.nextScanner=null;
          this.kilometrage=null;
          this.prochVidange=null;
          this.carburantActuel=null;
          this.modele=null;
          this.remarques=null;
          this.nameMarque=null;
          this.lastChaine=null;
          this.prochChaine=null;
          this.tarifJour=null;
          this.statut=null;
       
    }

    public Vehicule(int IDVehicule, int IDMarque,String nameMarque, String numChassis,String matricule, String lastAssur, String nextAssur, String lastScanner, 
        String nextScanner, int kilometrage,int prochVidange,String carburantActuel,String modele,String remarques,int lastChaine,int prochChaine,int tarifJour,String statut) {
          this.IDVehicule=new SimpleIntegerProperty(IDVehicule);
          this.IDMarque=new SimpleIntegerProperty(IDMarque);
          this.nameMarque=new SimpleStringProperty(nameMarque);
          this.numChassis=new SimpleStringProperty(numChassis);
          this.matricule=new SimpleStringProperty(matricule);
          this.lastAssur=new SimpleStringProperty(lastAssur);
          this.nextAssur=new SimpleStringProperty(nextAssur);;
          this.lastScanner=new SimpleStringProperty(lastScanner);
          this.nextScanner=new SimpleStringProperty(nextScanner);
          this.kilometrage=new SimpleIntegerProperty(kilometrage);
          this.prochVidange=new SimpleIntegerProperty(prochVidange);
          this.carburantActuel=new SimpleStringProperty(carburantActuel);
          this.modele=new SimpleStringProperty(modele);
          this.remarques=new SimpleStringProperty(remarques);
           this.lastChaine=new SimpleIntegerProperty(lastChaine);
          this.prochChaine=new SimpleIntegerProperty(prochChaine);
          this.tarifJour=new SimpleIntegerProperty(tarifJour);
          this.statut=new SimpleStringProperty(statut);
       
    }

    public final int getIDVehicule() {
        return IDVehicule.get();
    }
    public final void setIDVehicule(int value) {
        IDVehicule.set(value);
    }
    public IntegerProperty IDVehiculeProperty() {
        return IDVehicule;
    }

//-----------------------------------------------------------------------------------
    public final int getIDMarque() {
        return IDMarque.get();
    }
    public final void setIDMarque(int value) {
        IDMarque.set(value);
    }
    public IntegerProperty IDMarqueProperty() {
        return IDMarque;
    }
//-----------------------------------------------------------------------------------
 public final String getNameMarque() {
        return nameMarque.get();
    }
    public final void setNameMarque(String value) {
        nameMarque.set(value);
    }
    public StringProperty nameMarqueProperty() {
        return nameMarque;
    }
//-----------------------------------------------------------------------------------
    
    public final String getNumChassis() {
        return numChassis.get();
    }
    public final void setNumChassis(String value) {
        numChassis.set(value);
    }
    public StringProperty numChassisProperty() {
        return numChassis;
    }

  //-----------------------------------------------------------------------------------
  

    public final String getMatricule() {
        return matricule.get();
    }
    public final void setMatricule(String value) {
        matricule.set(value);
    }
    public StringProperty matriculeProperty() {
        return matricule;
    }
//-----------------------------------------------------------------------------------

    public final String getLastAssur() {
        return lastAssur.get();
    }
    public final void setLastAssur(String value) {
        lastAssur.set(value);
    }
    public StringProperty lastAssurProperty() {
        return lastAssur;
    }
//-----------------------------------------------------------------------------------

    public final String getNextAssur() {
        return nextAssur.get();
    }
    public final void setNextAssur(String value) {
        nextAssur.set(value);
    }
    public StringProperty nextAssurProperty() {
        return nextAssur;
    }
//-----------------------------------------------------------------------------------

    public final String getLastScanner() {
        return lastScanner.get();
    }
    public final void setLastScanner(String value) {
        lastScanner.set(value);
    }
    public StringProperty lastScannerProperty() {
        return lastScanner;
    }
//-----------------------------------------------------------------------------------


     public final String getNextScanner() {
        return nextScanner.get();
    }
    public final void setNextScanner(String value) {
        nextScanner.set(value);
    }
    public StringProperty nextScannerProperty() {
        return nextScanner;
    }
//-----------------------------------------------------------------------------------

     public final int getKilometrage() {
        return kilometrage.get();
    }
    public final void setKilometrage(int value) {
        kilometrage.set(value);
    }
    public IntegerProperty KilometrageProperty() {
        return kilometrage;
    }
//-----------------------------------------------------------------------------------

     public final int getProchVidange() {
        return prochVidange.get();
    }
    public final void setProchVidange(int value) {
        prochVidange.set(value);
    }
    public IntegerProperty prochVidangeProperty() {
        return prochVidange;
    }
//-----------------------------------------------------------------------------------
     public final String getCarburantActuel() {
        return carburantActuel.get();
    }
    public final void setCarburantActuel(String value) {
        carburantActuel.set(value);
    }
    public StringProperty carburantActuelProperty() {
        return carburantActuel;
    }
//-----------------------------------------------------------------------------------

    public final String getModele() {
        return modele.get();
    }
    public final void setModele(String value) {
        modele.set(value);
    }
    public StringProperty modeleProperty() {
        return modele;
    }
//-----------------------------------------------------------------------------------

     public final String getRemarques() {
        return remarques.get();
    }
    public final void setRemarques(String value) {
        remarques.set(value);
    }
    public StringProperty remarquesProperty() {
        return remarques;
    }
    //-----------------------------------------------------------------------------------

     public final int getLastChaine() {
        return lastChaine.get();
    }
    public final void setLastChaine(int value) {
        lastChaine.set(value);
    }
    public IntegerProperty lastChaineProperty() {
        return lastChaine;
    }
    //-----------------------------------------------------------------------------------

     public final int getProchChaine() {
        return prochChaine.get();
    }
    public final void setProchChaine(int value) {
        prochChaine.set(value);
    }
    public IntegerProperty prochChaineProperty() {
        return prochChaine;
    }
    //-----------------------------------------------------------------------------------

     public final int getTarifJour() {
        return tarifJour.get();
    }
    public final void setTarifJour(int value) {
        tarifJour.set(value);
    }
    public IntegerProperty tarifJourProperty() {
        return tarifJour;
    }

    //-----------------------------------------------------------------------------------

     public final String getStatut() {
        return statut.get();
    }
    public final void setStatut(String value) {
        statut.set(value);
    }
    public StringProperty statutProperty() {
        return statut;
    }
}

