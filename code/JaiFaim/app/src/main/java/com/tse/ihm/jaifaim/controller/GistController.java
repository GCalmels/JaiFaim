package com.tse.ihm.jaifaim.controller;

import android.util.JsonReader;
import android.util.Log;

import com.tse.ihm.jaifaim.model.Recipe;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Gabriel on 14/03/15.
 */
public class GistController {
    // ID du gist référence pour toutes les recettes
    private final static String ID_MAIN_GIST = "2aaf8f7c3436440bad82";

    // JSON Node names
    private static final String TAG_LIST = "list";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_AUTHOR = "author";

    // Récupération partielle de toutes les recettes
    public static Collection<Recipe> getAllRecipe() {
        GistService service = new GistService();
        Collection<Recipe> recipeList = new ArrayList<>();
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

                    JSONObject rObject;

                    // Boucle pour récuperer toutes les recettes
                    for (int i = 0; i < recipes.length(); i++) {
                        rObject = recipes.getJSONObject(i);

                        // Création d'une recette
                        String id = rObject.getString(TAG_ID);
                        String title = rObject.getString(TAG_TITLE);
                        String author = rObject.getString(TAG_AUTHOR);

                        Recipe recipe = new Recipe(id, title, author);

                        // Ajout à la liste des recettes
                        recipeList.add(recipe);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recipeList;
    }
}
