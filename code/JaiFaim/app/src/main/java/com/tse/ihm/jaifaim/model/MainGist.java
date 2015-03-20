package com.tse.ihm.jaifaim.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Greggy on 19/03/15.
 */
public class MainGist
{
    private static final String TAG = MainGist.class.getName();

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

    public void addRecipe(Recipe recipe)
    {
        m_RecipeList.add(recipe);
    }

    public String getTitle() { return m_Title; }
    public void setTitle(String _title) { m_Title = _title; }
    public String getOrganisation() { return m_Organisation; }
    public void setOrganisation(String _oragnisation) { m_Organisation = _oragnisation; }
    public ArrayList<Recipe> getRecipeList() { return m_RecipeList; }
    public void setRecipeList(ArrayList<Recipe> _list) { m_RecipeList = _list; }

    @Override
    public String toString()
    {
        String string = "{\"title\": " + "\"" + m_Title + "\",";
        string += "\"organisation\": " + "\"" + m_Organisation + "\",";
        string += "\"recipeList\":[";
        for (int i=0 ; i<m_RecipeList.size() ; i++)
        {
            string += "\"" + m_RecipeList.get(i).getId() + "\"";
            if (i != m_RecipeList.size()-1)
                string += ",";
        }
        string += "]}";

        Log.d(TAG, "[toString]" + string);
        return string;
    }
}
