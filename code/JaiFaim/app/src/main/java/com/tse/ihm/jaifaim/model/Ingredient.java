package com.tse.ihm.jaifaim.model;

import com.google.gson.annotations.SerializedName;

/**
 * Description : Un modele d'ingrédient avec un nom et une quantité
 * Created by Gabriel on 14/03/15.
 */
public class Ingredient {
    @SerializedName("name")
    String m_Name;

    @SerializedName("quantity")
    String m_Quantity;

    public Ingredient(String _name, String _quantity) {
        this.m_Name = _name;
        this.m_Quantity = _quantity;
    }

    public Ingredient() {
        m_Name = new String();
        m_Quantity = new String();
    }

    public String getName() {
        return m_Name;
    }

    public void setName(String _name) {
        this.m_Name = _name;
    }

    public String getQuantity() {
        return m_Quantity;
    }

    public void setQuantity(String _quantity) {
        this.m_Quantity = _quantity;
    }
}
