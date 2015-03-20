package com.tse.ihm.jaifaim.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.tse.ihm.jaifaim.common.JSONnodes;
import com.tse.ihm.jaifaim.helper.UserHelper;
import com.tse.ihm.jaifaim.message.NewGistTaskMessage;
import com.tse.ihm.jaifaim.model.Difficulty;
import com.tse.ihm.jaifaim.model.HungryUser;
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
import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;

/**
 * Created by Gabriel on 14/03/15.
 */
public class GistController {
    public static final String TAG = GistController.class.getName();

    // ID du gist référence pour toutes les recettes
    private final static String ID_MAIN_GIST = "58d7dde389d53b4cef9c";

    private static MainGist m_MainGist;
    private MainGistTask m_MainGistTask;
    private NewGistTask m_NewGistTask;

    public GistController()
    {
        if (m_MainGist == null)
            m_MainGist = new MainGist();
        m_MainGistTask = new MainGistTask(this);
        m_NewGistTask = new NewGistTask(this);

//        RoboGuice.getInjector(this).injectMembers(this);
    }

    public MainGist getMainGist()
    {
        return m_MainGist;
    }

    /**
     * Récupère toutes les recettes dans une asynctask
     * pour ne pas bloquer l'UI
     */
    public void getAllRecipesInBackground() {
        m_MainGistTask.execute();
    }

    public void createNewRecipeInBackground(HungryUser _user, Recipe _recipe) {
        // TODO: Voir s'il ne faut pas passer en paramètres l'id de l'auteur des recettes?
        m_NewGistTask.setRecipe(_recipe);
        m_NewGistTask.setUser(_user);
        m_NewGistTask.execute();
    }

    public void addNewRecipe(Recipe recipe)
    {
        m_MainGist.addRecipe(recipe);
    }


    public String getGistFileOfGist(String _gistId, String recipeName) throws IOException
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
    public MainGist getAllRecipe() throws JSONException, IOException {


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

    public Recipe populateRecipe(String recipeId) throws JSONException, IOException {
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

        String difficulty  = jsonObj.getString(JSONnodes.recipeDifficulty.toString());
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
        recipe.setDifficulty(Difficulty.valueOf(difficulty));
        recipe.setType(Type.valueOf(type));

        return recipe;
    }

    public Ingredient populateIngredient(JSONObject object) throws JSONException {
        Ingredient ingredient = new Ingredient();

        String name = object.getString(JSONnodes.ingredientName.toString());
        String quantity = object.getString(JSONnodes.ingredientQuantity.toString());

        ingredient.setName(name);
        ingredient.setQuantity(quantity);

        return ingredient;
    }

    public Step populateStep(JSONObject object) throws JSONException {
        Step step = new Step();

        String description = object.getString(JSONnodes.stepDescription.toString());
        step.setDescription(description);

        return step;
    }

    public ArrayList<Recipe> getRecipeOfUser(HungryUser _user)
    {
        ArrayList<Recipe> list = new ArrayList<>();
        for (Recipe r : m_MainGist.getRecipeList())
        {
            if (r.getAuthor().equals(_user.getUsername()))
            {
                list.add(r);
            }
        }

        return list;
    }

    public void createNewRecipe(HungryUser _user, Recipe _recipe) throws IOException
    {
        GistFile file = new GistFile();

        Gson gson = new Gson();
        file.setContent(gson.toJson(_recipe));

        // Create new Gist
        Gist gist = new Gist();
        gist.setDescription(_recipe.getTitle());
        gist.setFiles(Collections.singletonMap(JSONnodes.recipeFile.toString(), file));
        GistService service = new GistService();


        service.getClient().setCredentials(_user.getUsername(), _user.getPAssword());
        gist = service.createGist(gist); //returns the created gist

        _recipe.setId(gist.getId());
        addNewRecipe(_recipe);

        // Update main Gist
        updateMainGist();
    }

    public void updateMainGist() throws IOException
    {
        GistService service = new GistService();
        service.getClient().setOAuth2Token("a466ad6961d1ecf87ced4d9d8fd9860c41193d19");

        Log.d(TAG, "[getGistFileOfGist] + " + ID_MAIN_GIST);
        Gist gist = service.getGist(ID_MAIN_GIST);

        // Récupération du fichier contenant le nom de la recette, l'auteur et l'id
        GistFile file = new GistFile();
        file.setContent(m_MainGist.toString());

        gist.setFiles(Collections.singletonMap(JSONnodes.mainGistFile.toString(), file));

        service.updateGist(gist);
    }

    private class MainGistTask extends AsyncTask<Void, Void, Void>
    {
        private final String TAG = MainGistTask.class.getName();

        private GistController m_GistController;

        public MainGistTask(GistController controller)
        {
            m_GistController = controller;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.d(TAG, "[doInBackground] Retrieving recipes");
            try
            {
                m_MainGist = m_GistController.getAllRecipe();
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

        private GistController m_GistController;
        private Recipe m_Recipe;
        private HungryUser m_User;

        public NewGistTask(GistController controller)
        {
            m_GistController = controller;
        }

        public void setRecipe(Recipe recipe)
        {
            m_Recipe = recipe;
        }

        public void setUser(HungryUser _user) { m_User = _user;}

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
                m_GistController.createNewRecipe(m_User, m_Recipe);
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
            EventBus.getDefault().post(new NewGistTaskMessage(true));
        }

    }
}
