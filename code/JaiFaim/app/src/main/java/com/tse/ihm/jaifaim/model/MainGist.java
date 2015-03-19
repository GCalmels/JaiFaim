package com.tse.ihm.jaifaim.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Greggy on 19/03/15.
 */
public class MainGist
{
    @SerializedName("title")
    private String m_Title;

    @SerializedName("organisation")
    private String m_Organisation;

    @SerializedName("recipeList")
    private ArrayList<Recipe> m_RecipeList;

    public MainGist()
    {
        m_Title = "Titre du Gist";
        m_Organisation = "Driftse";
        m_RecipeList = new ArrayList<>();
    }

    public String getTitle() { return m_Title; }
    public void setTitle(String _title) { m_Title = _title; }
    public String getOrganisation() { return m_Organisation; }
    public void setOrganisation(String _oragnisation) { m_Organisation = _oragnisation; }
    public ArrayList<Recipe> getRecipeList() { return m_RecipeList; }
    public void setRecipeList(ArrayList<Recipe> _list) { m_RecipeList = _list; }
}
