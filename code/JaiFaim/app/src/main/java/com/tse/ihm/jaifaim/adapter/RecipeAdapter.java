package com.tse.ihm.jaifaim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.model.Recipe;

import java.util.ArrayList;

/**
 * Created by Gabriel on 18/03/15.
 */
public class RecipeAdapter extends ArrayAdapter<Recipe> {

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        super(context, 0, recipes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Recipe recipe = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_list_item, parent, false);
        }
        // Lookup view for data population
        TextView recipe_title = (TextView) convertView.findViewById(R.id.recipe_list_item_title);
        TextView recipe_author = (TextView) convertView.findViewById(R.id.recipe_list_item_author);

        // Populate the data into the template view using the data object
        recipe_title.setText(recipe.getTitle());
        recipe_author.setText(recipe.getAuthor());

        // Return the completed view to render on screen
        return convertView;
    }
}