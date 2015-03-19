package com.tse.ihm.jaifaim.message;

/**
 * Created by Greggy on 19/03/15.
 */
public class NewGistTaskMessage
{
    boolean m_RecipeSuccessfullyAdded;

    public NewGistTaskMessage(boolean successfull)
    {
        m_RecipeSuccessfullyAdded = successfull;
    }

    public boolean getResult() { return m_RecipeSuccessfullyAdded; }
}
