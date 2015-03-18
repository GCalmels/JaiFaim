package com.tse.ihm.jaifaim.common;

/**
 * Description : Différents type de plats : entrée, dessert, ...
 * Created by Greggy on 18/03/15.
 */
public enum JSONnodes {
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