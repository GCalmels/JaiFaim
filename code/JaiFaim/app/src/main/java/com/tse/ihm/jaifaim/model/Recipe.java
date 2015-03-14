package com.tse.ihm.jaifaim.model;

import java.util.Collection;
import java.util.Date;

/**
 * Description : Modèle d'une recette
 * Created by Gabriel on 14/03/15.
 */
public class Recipe {
    private String m_Name;
    private HungryUser m_Author;
    private Date m_CreationDate;
    private int m_PrepTime;
    private int m_CookingTime;

    // Collections
    private Collection<Ingredient> m_IngredientList;
    private Collection<Step> m_StepList;

    // Enums
    private Difficulty m_Difficulty;
    private Type m_Type;

    public Recipe(String _name, HungryUser _author, Date _creationDate, int _prepTime, int _cookingTime,
                  Collection<Ingredient> _ingredientList, Collection<Step> _stepList,
                  Difficulty _difficulty, Type _type) {
        m_Name = _name;
        m_Author = _author;
        m_CreationDate = _creationDate;
        m_PrepTime = _prepTime;
        m_CookingTime = _cookingTime;
        m_IngredientList = _ingredientList;
        m_StepList = _stepList;
        m_Difficulty = _difficulty;
        m_Type = _type;
    }

    public String getName() {
        return m_Name;
    }

    public void setName(String _name) {
        m_Name = _name;
    }

    public HungryUser getAuthor() {
        return m_Author;
    }

    public void setAuthor(HungryUser _author) {
        m_Author = _author;
    }

    public Date getCreationDate() {
        return m_CreationDate;
    }

    public void setCreationDate(Date _creationDate) {
        m_CreationDate = _creationDate;
    }

    public int getPrepTime() {
        return m_PrepTime;
    }

    public void setPrepTime(int _prepTime) {
        m_PrepTime = _prepTime;
    }

    public int getCookingTime() {
        return m_CookingTime;
    }

    public void setCookingTime(int _cookingTime) {
        m_CookingTime = _cookingTime;
    }

    public Collection<Ingredient> getIngredientList() {
        return m_IngredientList;
    }

    public void setIngredientList(Collection<Ingredient> _ingredientList) {
        m_IngredientList = _ingredientList;
    }

    public Collection<Step> getStepList() {
        return m_StepList;
    }

    public void setStepList(Collection<Step> _stepList) {
        m_StepList = _stepList;
    }

    public Difficulty getDifficulty() {
        return m_Difficulty;
    }

    public void setDifficulty(Difficulty _difficulty) {
        m_Difficulty = _difficulty;
    }

    public Type getType() {
        return m_Type;
    }

    public void setType(Type _type) {
        this.m_Type = _type;
    }
}
