/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package locationcar.Modele;


/**
 *
 * @author Fodil
 */

public class VehiculeCmb {
     private int ID;
    private String name;
    private int tarif;

    public VehiculeCmb(int id, String name,int tariff) {
        this.ID = id;
        this.name = name;
        this.tarif = tariff;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTarif() {
        return tarif;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
    }
    
    
}
