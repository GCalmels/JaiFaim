package com.tse.ihm.jaifaim.model;

import com.google.gson.annotations.SerializedName;

/**
 * Description : Modele d'une étape pour la création d'une recette
 * Created by Gabriel on 14/03/15.
 */
public class Step {
    @SerializedName("description")
    private String m_Description;

    public Step(String _description) {
        m_Description = _description;
    }

    public Step() {
       m_Description = new String();
    }

    public String getDescription() {
        return m_Description;
    }

    public void setDescription(String _description) {
        m_Description = _description;
    }
}
