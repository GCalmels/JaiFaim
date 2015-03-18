package com.tse.ihm.jaifaim.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Description : Modèle d'une recette
 * Created by Gabriel on 14/03/15.
 */
public class Recipe {

    @SerializedName("id")
    private String m_Id;

    @SerializedName("title")
    private String m_Title;

    @SerializedName("author")
    private String m_Author;

    @SerializedName("creationDate")
    private Date m_CreationDate;

    @SerializedName("prepTime")
    private String m_PrepTime;

    @SerializedName("cookingTime")
    private String m_CookingTime;

    @SerializedName("imageUrl")
    private String m_ImageUrl;

    // Collections
    @SerializedName("ingredientList")
    private ArrayList<Ingredient> m_IngredientList;

    @SerializedName("stepList")
    private ArrayList<Step> m_StepList;

    // Enums
    @SerializedName("difficulty")
    private Difficulty m_Difficulty;

    @SerializedName("type")
    private Type m_Type;

    public Recipe() {}

    public Recipe(String _id, String _title, String _author) {
        m_Id = _id;
        m_Title = _title;
        m_Author = _author;

        m_IngredientList = new ArrayList<Ingredient>();
        m_StepList = new ArrayList<Step>();
    }

    public Recipe(String _id, String _title, String _author, Date _creationDate, String _prepTime,
                  String _cookingTime, String _imageUrl, ArrayList<Ingredient> _ingredientList,
                  ArrayList<Step> _stepList, Difficulty _difficulty, Type _type) {
        m_Id = _id;
        m_Title = _title;
        m_Author = _author;
        m_CreationDate = _creationDate;
        m_PrepTime = _prepTime;
        m_CookingTime = _cookingTime;
        m_ImageUrl = _imageUrl;
        m_IngredientList = _ingredientList;
        m_StepList = _stepList;
        m_Difficulty = _difficulty;
        m_Type = _type;
    }

    public String getId() {
        return m_Id;
    }

    public void setId(String _id) {
        this.m_Id = _id;
    }

    public String getTitle() {
        return m_Title;
    }

    public void setTitle(String _title) {
        m_Title = _title;
    }

    public String getAuthor() {
        return m_Author;
    }

    public void setAuthor(String _author) {
        m_Author = _author;
    }

    public Date getCreationDate() {
        return m_CreationDate;
    }

    public void setCreationDate(Date _creationDate) {
        m_CreationDate = _creationDate;
    }

    public String getPrepTime() {
        return m_PrepTime;
    }

    public void setPrepTime(String _prepTime) {
        m_PrepTime = _prepTime;
    }

    public String getCookingTime() {
        return m_CookingTime;
    }

    public void setCookingTime(String _cookingTime) {
        m_CookingTime = _cookingTime;
    }

    public String getImageUrl()
    {
        return m_ImageUrl;
    }

    public void setImageUrl(String _imageUrl)
    {
        this.m_ImageUrl = m_ImageUrl;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return m_IngredientList;
    }

    public void setIngredientList(ArrayList<Ingredient> _ingredientList) {
        m_IngredientList = _ingredientList;
    }

    public ArrayList<Step> getStepList() {
        return m_StepList;
    }

    public void setStepList(ArrayList<Step> _stepList) {
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

    @Override
    public String toString() {
        return "Recipe{" +
                "Id='" + m_Id + '\'' +
                ", Title='" + m_Title + '\'' +
                ", Author='" + m_Author + '\'' +
                '}';
    }
}
