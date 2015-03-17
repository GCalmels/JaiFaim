package com.tse.ihm.jaifaim.asynctask;

import android.os.AsyncTask;

import com.tse.ihm.jaifaim.controller.GistController;
import com.tse.ihm.jaifaim.model.Recipe;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Gabriel on 15/03/15.
 */
public class MainGistTask extends AsyncTask<Void, Void, Void> {

    private ArrayList<Recipe> m_RecipeList;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        m_RecipeList = GistController.getAllRecipe();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // Envoi de la réussite ou non à l'activité
        EventBus.getDefault().post(m_RecipeList);
    }

}
