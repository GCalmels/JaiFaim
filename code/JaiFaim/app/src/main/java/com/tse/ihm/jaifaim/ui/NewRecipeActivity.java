package com.tse.ihm.jaifaim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.controller.GistController;
import com.tse.ihm.jaifaim.model.Difficulty;
import com.tse.ihm.jaifaim.model.Ingredient;
import com.tse.ihm.jaifaim.model.Recipe;
import com.tse.ihm.jaifaim.model.Step;
import com.tse.ihm.jaifaim.model.Type;

import java.util.ArrayList;
import java.util.Date;

public class NewRecipeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Class c = com.tse.ihm.jaifaim.Menu.itemSelected(item);
        if (c != null) {
            Intent i = new Intent(this, c);
            startActivity(i);
        }
        return true;
    }

    /**
     * Créer un gist contenant la recette
     * @param v
     */
    public void createRecipe(View v)
    {
        GistController gistController = new GistController();


        // TODO: Remplacer par la vrai recette
        Recipe recipe = new Recipe("zearzeafsdfqsdf",
                "Pates Carbonara",
                "Greg",
                new Date(),
                "15min",
                "30min",
                "https://avatars0.githubusercontent.com/u/5655900?s=140",
                new ArrayList<Ingredient>(),
                new ArrayList<Step>(),
                Difficulty.HARD,
                Type.DISH);
        gistController.createNewRecipeInBackground(recipe);
    }
}
