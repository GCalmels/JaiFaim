package com.tse.ihm.jaifaim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.controller.GistController;
import com.tse.ihm.jaifaim.model.Difficulty;
import com.tse.ihm.jaifaim.model.Ingredient;
import com.tse.ihm.jaifaim.model.Recipe;
import com.tse.ihm.jaifaim.model.Step;
import com.tse.ihm.jaifaim.model.Type;

import java.util.ArrayList;
import java.util.Date;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class NewRecipeActivity extends RoboActionBarActivity
{
    private static final String TAG = NewRecipeActivity.class.getName();

    @InjectView(R.id.new_recipe_container)      private LinearLayout m_Container;
    @InjectView(R.id.new_recipe_image)          private ImageView m_RecipeImage;
    @InjectView(R.id.new_recipe_title)          private EditText m_RecipeTitle;
    @InjectView(R.id.new_recipe_prep_time)      private EditText m_RecipePrepTime;
    @InjectView(R.id.new_recipe_cooking_time)   private EditText m_RecipeCookingTime;
    @InjectView(R.id.new_recipe_ingredient)     private EditText m_RecipeIngredient;
    @InjectView(R.id.new_recipe_step)           private EditText m_RecipeStep;

    private ArrayList<EditText> m_RecipeIngredients;
    private ArrayList<EditText> m_RecipeSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        m_RecipeIngredients = new ArrayList<>();
        m_RecipeSteps = new ArrayList<>();

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

    public void addIngredientEditText(View v)
    {
        Log.d(TAG, "[addIngredientEditText]");
        m_Container.addView(new EditText(NewRecipeActivity.this));
    }


    /**
     * Créer un gist contenant la recette
     * @param v
     */
    public void createRecipe(View v)
    {
        GistController gistController = new GistController();

        // TODO: finir de remplir recette
        Recipe recipe = new Recipe();

        // TODO :: changer
        recipe.setImageUrl("http://www.ot-cayeuxsurmer.fr/sites/otcsm/files/news/2014/11/08/1807-826.jpg");

        recipe.setTitle(m_RecipeTitle.getText().toString());
        // TODO : changer avec le vrai auteur connecté
        recipe.setAuthor("Greggy");
        recipe.setCreationDate(String.valueOf(new Date().getTime()));
        recipe.setPrepTime(m_RecipePrepTime.getText().toString());
        recipe.setCookingTime(m_RecipeCookingTime.getText().toString());
        // TODO: changer
        recipe.setDifficulty(Difficulty.HARD);
        // TODO: changer
        recipe.setType(Type.DISH);
        // TODO : compléter
        Ingredient ing = new Ingredient();
        ing.setName(m_RecipeIngredient.getText().toString());
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ing);
        recipe.setIngredientList(ingredientList);

        Step step = new Step();
        step.setDescription(m_RecipeStep.getText().toString());
        ArrayList<Step> stepList = new ArrayList<>();
        stepList.add(step);
        recipe.setStepList(stepList);


        gistController.createNewRecipeInBackground(recipe);
    }


}
