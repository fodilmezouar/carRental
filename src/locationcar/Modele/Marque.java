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
    public class Marque {
    private final IntegerProperty IDMarque;
    private final StringProperty Constructeur;
    

    public Marque() {
        this.IDMarque =null;
        this.Constructeur = null;
     
    }
    public Marque(int IDMarque, String Constructeur) {
        this.IDMarque = new SimpleIntegerProperty(IDMarque);
        this.Constructeur = new SimpleStringProperty(Constructeur);
        
    }
    public final int getIDMarque() {
        return IDMarque.get();
    }

    public final void setIDMarque(int value) {
        IDMarque.set(value);
    }

    public IntegerProperty IDMarqueProperty() {
        return IDMarque;
    }

    public final String getConstructeur() {
        return Constructeur.get();
    }

    public final void setConstructeur(String value) {
        Constructeur.set(value);
    }

    public StringProperty constructeurProperty() {
        return Constructeur;
    }
    
}