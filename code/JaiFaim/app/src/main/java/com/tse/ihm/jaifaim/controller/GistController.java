package com.tse.ihm.jaifaim.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.tse.ihm.jaifaim.common.JSONnodes;
import com.tse.ihm.jaifaim.model.Difficulty;
import com.tse.ihm.jaifaim.model.Ingredient;
import com.tse.ihm.jaifaim.model.Recipe;
import com.tse.ihm.jaifaim.model.Step;
import com.tse.ihm.jaifaim.model.Type;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * Created by Gabriel on 14/03/15.
 */
public class GistController {
    public static final String TAG = GistController.class.getName();

    // ID du gist référence pour toutes les recettes
    private final static String ID_MAIN_GIST = "24e9ce70e2785931f5ae";

    // JSON Node names
    private static final String TAG_LIST = "list";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_AUTHOR = "author";

    /**
     * Récupère toutes les recettes dans une asynctask
     * pour ne pas bloquer l'UI
     */
    public void getAllRecipesInBackground() {
        // TODO: Voir s'il ne faut pas passer en paramètres l'id de l'auteur des recettes?
        MainGistTask mainGistTask = new MainGistTask();
        mainGistTask.execute();
    }


    // Récupération partielle de toutes les recettes
    public static ArrayList<Recipe> getAllRecipe() {
        GistService service = new GistService();
        ArrayList<Recipe> recipeList = new ArrayList<>();
        try {
            Gist gist = service.getGist(ID_MAIN_GIST);

            // Récupération du fichier contenant le nom de la recette, l'auteur et l'id
            GistFile recipe_list = gist.getFiles().get("recipe_list");

            // Parse le string
            String content = recipe_list.getContent();

            JSONArray recipes;

            if (content != null && !content.isEmpty()) {
                try {
                    JSONObject jsonObj = new JSONObject(content);
                    recipes = jsonObj.getJSONArray(TAG_LIST);

                    Log.d(TAG, "[getAllRecipes] number of recipes : " + recipes.length());

                    JSONObject rObject;

                    // Boucle pour récuperer toutes les recettes
                    for (int i = 0; i < recipes.length(); i++) {
                        rObject = recipes.getJSONObject(i);

                        Log.d(TAG, "[getAllRecipes] About to retrieve recipe : " + i);
                        Recipe recipe = populateRecipe(rObject);
                        Log.d(TAG, "[getAllRecipes] Retrieved : " + i);

                        // Ajout à la liste des recettes
                        recipeList.add(recipe);
                    }

                    Log.d(TAG, "[getAllRecipes] recipes retrieved : " + recipeList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipeList;
    }

    public static Recipe populateRecipe(JSONObject object) throws JSONException {
        Recipe recipe = new Recipe();

        // Création d'une recette
        String id = object.getString(JSONnodes.recipeId.toString());
        String title = object.getString(JSONnodes.recipeTitle.toString());
        String author = object.getString(JSONnodes.recipeAuthor.toString());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String creationDateString = object.getString(JSONnodes.recipeCreationDate.toString());
        Date creationDate = new Date();
        try {
            creationDate = formatter.parse(creationDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String prepTime = object.getString(JSONnodes.recipePrepTime.toString());
        String cookingTime = object.getString(JSONnodes.recipeCookingTime.toString());
        String imageUrl = object.getString(JSONnodes.recipeImageUrl.toString());

        // Récupération des ingrédoents
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        JSONArray ingredientsList = object.getJSONArray(JSONnodes.recipeIngredientList.toString());
        JSONObject rIngredient;

        // Boucle pour récuperer tous les ingrédients
        for (int j = 0; j < ingredientsList.length(); j++)
        {
            rIngredient = ingredientsList.getJSONObject(j);
            Ingredient ingredient = populateIngredient(rIngredient);
            ingredientList.add(ingredient);
        }

        // Récupération des étapes
        ArrayList<Step> stepList = new ArrayList<>();
        JSONArray stepsList = object.getJSONArray(JSONnodes.recipeStepList.toString());
        JSONObject rStep;

        // Boucle pour récuperer toutes les étapes
        for (int j = 0; j < stepsList.length(); j++)
        {
            rStep = stepsList.getJSONObject(j);
            Step step = populateStep(rStep);
            stepList.add(step);
        }

        String difficulty = object.getString(JSONnodes.recipeDifficulty.toString());
        String type = object.getString(JSONnodes.recipeType.toString());


        recipe.setId(id);
        recipe.setTitle(title);
        recipe.setAuthor(author);
        recipe.setCreationDate(creationDate);
        recipe.setPrepTime(prepTime);
        recipe.setCookingTime(cookingTime);
        recipe.setImageUrl(imageUrl);
        recipe.setIngredientList(ingredientList);
        recipe.setStepList(stepList);
        recipe.setDifficulty(Difficulty.HARD);
        recipe.setType(Type.DISH);

        return recipe;
    }

    public static Ingredient populateIngredient(JSONObject object) throws JSONException {
        Ingredient ingredient = new Ingredient();

        String name = object.getString(JSONnodes.ingredientName.toString());
        String quantity = object.getString(JSONnodes.ingredientQuantity.toString());

        ingredient.setName(name);
        ingredient.setQuantity(quantity);

        return ingredient;
    }

    public static Step populateStep(JSONObject object) throws JSONException {
        Step step = new Step();

        //String description = object.getString(JSONnodes.stepDescription.toString());
        String description = "A changer ... TODO";


        step.setDescription(description);

        return step;
    }

    private class MainGistTask extends AsyncTask<Void, Void, Void>
    {
        private final String TAG = MainGistTask.class.getName();

        private ArrayList<Recipe> m_RecipeList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.d(TAG, "[doInBackground] Retrieving recipes");
            m_RecipeList = GistController.getAllRecipe();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Envoi de la réussite ou non à l'activité
            Log.d(TAG, "[onPostExecute] All recipes retrived");
            EventBus.getDefault().post(m_RecipeList);
        }

    }

}
