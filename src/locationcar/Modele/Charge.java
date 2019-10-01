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
public class Charge {
    private   IntegerProperty IDCharge;
    private   IntegerProperty IDVehicle;
    private   StringProperty NumChassis;
    private   StringProperty Matricule;
    private   StringProperty ModeleVehicule;
    private   StringProperty DateCharge;
    private   StringProperty TypeCharge;
    private   StringProperty Description;
    private   IntegerProperty Depense;

    public Charge() {
        this.IDCharge = null;
        this.IDVehicle =null;
        this.NumChassis =null;
        this.Matricule =null;
        this.ModeleVehicule = null;
        this.DateCharge = null;
        this.TypeCharge = null;
        this.Description = null;
        this.Depense = null;
    }

    public Charge(int IDCharge, int IDVehicle, String NumChassis, String Matricule, String ModeleVehicule, String DateCharge, String TypeCharge, String Description, int Depense) {
        this.IDCharge = new SimpleIntegerProperty(IDCharge);;
        this.IDVehicle = new SimpleIntegerProperty(IDVehicle);;
        this.NumChassis = new SimpleStringProperty(NumChassis);
        this.Matricule = new SimpleStringProperty(Matricule);
        this.ModeleVehicule = new SimpleStringProperty(ModeleVehicule);
        this.DateCharge = new SimpleStringProperty(DateCharge);
        this.TypeCharge = new SimpleStringProperty(TypeCharge);
        this.Description = new SimpleStringProperty(Description);
        this.Depense = new SimpleIntegerProperty(Depense);;
    }
    public final int getIDCharge() {
        return IDCharge.get();
    }

    public final void setIDCharge(int value) {
        IDCharge.set(value);
    }

    public IntegerProperty IDChargeProperty() {
        return IDCharge;
    }

//-------------------------------------------------------------------------------
     public final int getIDVehicle() {
        return IDVehicle.get();
    }

    public final void setIDVehicle(int value) {
        IDVehicle.set(value);
    }

    public IntegerProperty IDVehicleProperty() {
        return IDVehicle;
    }

//-------------------------------------------------------------------------------
     public final String getNumChassis() {
        return NumChassis.get();
    }

    public final void setNumChassis(String value) {
        NumChassis.set(value);
    }

    public StringProperty NumChassisProperty() {
        return NumChassis;
    }

//-------------------------------------------------------------------------------
     public final String getMatricule() {
        return Matricule.get();
    }

    public final void setMatricule(String value) {
        Matricule.set(value);
    }

    public StringProperty matriculeProperty() {
        return Matricule;
    }

//-------------------------------------------------------------------------------
     public final String getModeleVehicule() {
        return ModeleVehicule.get();
    }

    public final void setModeleVehicule(String value) {
        ModeleVehicule.set(value);
    }

    public StringProperty ModeleVehiculeProperty() {
        return ModeleVehicule;
    }

//-------------------------------------------------------------------------------
         public final String getDateCharge() {
        return DateCharge.get();
    }

    public final void setDateCharge(String value) {
        DateCharge.set(value);
    }

    public StringProperty dateChargeProperty() {
        return DateCharge;
    }

//-------------------------------------------------------------------------------
      public final String getTypeCharge() {
        return TypeCharge.get();
    }

    public final void setTypeCharge(String value) {
        TypeCharge.set(value);
    }

    public StringProperty typeChargeProperty() {
        return TypeCharge;
    }

//-------------------------------------------------------------------------------
          public final String getDescription() {
        return Description.get();
    }

    public final void setDescription(String value) {
        Description.set(value);
    }

    public StringProperty descriptionProperty() {
        return Description;
    }

//-------------------------------------------------------------------------------
    public final int getDepense() {
        return Depense.get();
    }

    public final void setDepense(int value) {
        Depense.set(value);
    }

    public IntegerProperty depenseProperty() {
        return Depense;
    }
}
