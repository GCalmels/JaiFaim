package com.tse.ihm.jaifaim.model;

/**
 * Description : Différentes difficultées de plats : Facile, Moyen, Difficile
 * Created by Gabriel on 14/03/15.
 */
public enum Difficulty {
    EASY("Facile"),
    MEDIUM("Moyen"),
    HARD("Difficile");

    private String m_Level;

    Difficulty(String _level) {
        m_Level = _level;
    }

    @Override
    public String toString() {
        return m_Level;
    }
}
