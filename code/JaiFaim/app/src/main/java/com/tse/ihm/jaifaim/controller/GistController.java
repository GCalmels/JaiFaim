package com.tse.ihm.jaifaim.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.tse.ihm.jaifaim.common.JSONnodes;
import com.tse.ihm.jaifaim.model.Difficulty;
import com.tse.ihm.jaifaim.model.Ingredient;
import com.tse.ihm.jaifaim.model.MainGist;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import de.greenrobot.event.EventBus;

/**
 * Created by Gabriel on 14/03/15.
 */
public class GistController {
    public static final String TAG = GistController.class.getName();

    // ID du gist référence pour toutes les recettes
    private final static String ID_MAIN_GIST = "58d7dde389d53b4cef9c";

    /**
     * Récupère toutes les recettes dans une asynctask
     * pour ne pas bloquer l'UI
     */
    public void getAllRecipesInBackground() {
        // TODO: Voir s'il ne faut pas passer en paramètres l'id de l'auteur des recettes?
        MainGistTask mainGistTask = new MainGistTask();
        mainGistTask.execute();
    }

    public void createNewRecipeInBackground(Recipe _recipe) {
        // TODO: Voir s'il ne faut pas passer en paramètres l'id de l'auteur des recettes?
        NewGistTask newGistTask = new NewGistTask(_recipe);
        newGistTask.execute();
    }


    public static String getGistFileOfGist(String _gistId, String recipeName) throws IOException
    {
        GistService service = new GistService();
        service.getClient().setOAuth2Token("a466ad6961d1ecf87ced4d9d8fd9860c41193d19");

        Log.d(TAG, "[getGistFileOfGist] + " + _gistId);
        Gist gist = service.getGist(_gistId);

        // Récupération du fichier contenant le nom de la recette, l'auteur et l'id
        GistFile file = gist.getFiles().get(recipeName);

        // Parse le string
        return file.getContent();
    }


    // Get all Recipes from mainGist
    public static MainGist getAllRecipe() throws JSONException, IOException {


        String content = getGistFileOfGist(ID_MAIN_GIST, JSONnodes.mainGistFile.toString());

        MainGist mainGist = new MainGist();

        if (content != null && !content.isEmpty())
        {
            JSONObject jsonObj = new JSONObject(content);

            mainGist = new MainGist();
            mainGist.setTitle(jsonObj.getString(JSONnodes.mainGistTitle.toString()));
            mainGist.setOrganisation(jsonObj.getString(JSONnodes.mainGistOrganisation.toString()));


            // Get recipes
            ArrayList<Recipe> recipeList = new ArrayList<>();
            JSONArray recipes;
            recipes = jsonObj.getJSONArray(JSONnodes.mainGistRecipeList.toString());

            Log.d(TAG, "[getAllRecipes] number of recipes : " + recipes.length());

            // Boucle pour récuperer toutes les recettes
            for (int i = 0; i < recipes.length(); i++)
            {
                String recipeId = recipes.getString(i);

                Log.d(TAG, "[getAllRecipes] About to retrieve recipe : " + i);
                Recipe recipe = populateRecipe(recipeId);
                Log.d(TAG, "[getAllRecipes] Retrieved : " + i);

                // Ajout à la liste des recettes
                recipeList.add(recipe);
            }

            Log.d(TAG, "[getAllRecipes] recipes retrieved : " + recipeList);

            mainGist.setRecipeList(recipeList);
        }

        return mainGist;
    }

    public static Recipe populateRecipe(String recipeId) throws JSONException, IOException {
        String recipeContent = getGistFileOfGist(recipeId, JSONnodes.recipeFile.toString());
        JSONObject jsonObj = new JSONObject(recipeContent);

        Recipe recipe = new Recipe();

        // Création d'une recette
        String id = recipeId;
        String title = jsonObj.getString(JSONnodes.recipeTitle.toString());
        String author = jsonObj.getString(JSONnodes.recipeAuthor.toString());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String creationDate = jsonObj.getString(JSONnodes.recipeCreationDate.toString());
        String prepTime = jsonObj.getString(JSONnodes.recipePrepTime.toString());
        String cookingTime = jsonObj.getString(JSONnodes.recipeCookingTime.toString());
        String imageUrl = jsonObj.getString(JSONnodes.recipeImageUrl.toString());
        Log.d(TAG, "[populateRecipe] Image URL : " + imageUrl);


        // Récupération des ingrédoents
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        JSONArray ingredientsList = jsonObj.getJSONArray(JSONnodes.recipeIngredientList.toString());
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
        JSONArray stepsList = jsonObj.getJSONArray(JSONnodes.recipeStepList.toString());
        JSONObject rStep;

        // Boucle pour récuperer toutes les étapes
        for (int j = 0; j < stepsList.length(); j++)
        {
            rStep = stepsList.getJSONObject(j);
            Step step = populateStep(rStep);
            stepList.add(step);
        }

        String difficulty = jsonObj.getString(JSONnodes.recipeDifficulty.toString());
        String type = jsonObj.getString(JSONnodes.recipeType.toString());


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

    public static void createNewRecipe(Recipe _recipe) throws IOException
    {
        GistFile file = new GistFile();

        Gson gson = new Gson();
        file.setContent(gson.toJson(_recipe));

        // Create new Gist
        Gist gist = new Gist();
        gist.setDescription(_recipe.getTitle());
        gist.setFiles(Collections.singletonMap(JSONnodes.recipeFile.toString(), file));
        GistService service = new GistService();
        //TODO : Changer par les identifiants de l'utilisateur
        service.getClient().setCredentials("driftse@gmail.com", "Skad9pEj8durj7yoow4jaL6Con4U");
        gist = service.createGist(gist); //returns the created gist


        // Update main Gist


    }

    private class MainGistTask extends AsyncTask<Void, Void, Void>
    {
        private final String TAG = MainGistTask.class.getName();

        private MainGist m_MainGist;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.d(TAG, "[doInBackground] Retrieving recipes");
            try
            {
                m_MainGist = GistController.getAllRecipe();
            } catch (JSONException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Envoi de la réussite ou non à l'activité
            Log.d(TAG, "[onPostExecute] All recipes retrived");
            EventBus.getDefault().post(m_MainGist);
        }

    }


    private class NewGistTask extends AsyncTask<Void, Void, Void>
    {
        private final String TAG = MainGistTask.class.getName();

        private Recipe m_Recipe;

        public NewGistTask(Recipe _recipe)
        {
            m_Recipe = _recipe;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.d(TAG, "[doInBackground] creating recipe");
            try
            {
                GistController.createNewRecipe(m_Recipe);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Envoi de la réussite ou non à l'activité
            Log.d(TAG, "[onPostExecute] recipe created");
            EventBus.getDefault().post(m_Recipe);
        }

    }
}
