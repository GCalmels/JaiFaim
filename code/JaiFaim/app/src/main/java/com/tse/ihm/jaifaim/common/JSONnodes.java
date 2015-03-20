package com.tse.ihm.jaifaim.common;

/**
 * Created by Greggy on 18/03/15.
 */
public enum JSONnodes {
    //MainGist
    mainGistFile("recipe_list_gist"),
    mainGistTitle("title"),
    mainGistOrganisation("organisation"),
    mainGistRecipeList("recipeList"),

    // Recettes
    recipeId("id"),
    recipeTitle("title"),
    recipeAuthor("author"),
    recipeCreationDate("creationDate"),
    recipePrepTime("prepTime"),
    recipeCookingTime("cookingTime"),
    recipeImageUrl("imageUrl"),
    recipeIngredientList("ingredientList"),
    recipeStepList("stepList"),
    recipeDifficulty("difficulty"),
    recipeType("type"),
    recipeFile("recette.json"),

    // Ingrégients
    ingredientName("name"),
    ingredientQuantity("quantity"),

    // Steps
    stepDescription("description");


    private String m_Name;

    JSONnodes(String _name) {
        m_Name = _name;
    }

    @Override
    public String toString() {
        return m_Name;
    }
}