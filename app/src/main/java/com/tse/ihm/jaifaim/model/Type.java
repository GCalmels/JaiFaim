package com.tse.ihm.jaifaim.model;

/**
 * Description : Différents type de plats : entrée, dessert, ...
 * Created by Gabriel on 14/03/15.
 */
public enum Type {
    STARTER("Entrée"),
    DISH("Plat"),
    DESSERT("Dessert");

    private String m_Name;

     Type(String _name) {
         m_Name = _name;
    }

    @Override
    public String toString() {
        return m_Name;
    }
}
