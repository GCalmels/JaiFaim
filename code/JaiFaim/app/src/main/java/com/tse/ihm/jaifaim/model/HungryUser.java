package com.tse.ihm.jaifaim.model;

import org.eclipse.egit.github.core.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Modèle d'un utilisateur
 * Created by Gabriel on 14/03/15.
 */
public class HungryUser {
    private User m_GitHubUser;
    private String m_Username;
    private String m_Password;
    private ArrayList<Recipe> m_MyRecipes;

    public HungryUser(String _username, String _password)
    {
        m_Username = _username;
        m_Password = _password;
    }

    public void setGitHubUser(User _user)
    {
        m_GitHubUser = _user;
    }

    public User getGitHubUser()
    {
        return m_GitHubUser;
    }

    public void setUsername(String _username)
    {
        m_Username = _username;
    }

    public String getUsername() { return m_Username; }

    public void setPassword(String _password)
    {
        m_Password = _password;
    }

    public String getPAssword() {return  m_Password;}

    public void setMyRecipes(ArrayList<Recipe> _recipes)
    {
        m_MyRecipes = _recipes;
    }

    public ArrayList<Recipe> getMyRecipes() { return m_MyRecipes; }
}
