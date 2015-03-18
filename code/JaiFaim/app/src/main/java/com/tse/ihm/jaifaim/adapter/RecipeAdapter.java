package com.tse.ihm.jaifaim.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.model.Recipe;

import java.util.ArrayList;

/**
 * Created by Gabriel on 18/03/15.
 */
public class RecipeAdapter extends BaseAdapter
{
    private static final String TAG = Recipe.class.getName();

    private Context m_Context;
    private ArrayList<Recipe> m_RecipeList;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        super();
        m_Context = context;
        m_RecipeList = recipes;
    }

    @Override
    public int getCount()
    {
        return m_RecipeList.size();
    }

    @Override
    public Recipe getItem(int i)
    {
        return m_RecipeList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(m_Context).inflate(R.layout.recipe_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.recipeTitle = (TextView) convertView.findViewById(R.id.recipe_list_item_title);
            viewHolder.recipeAuthor = (TextView) convertView.findViewById(R.id.recipe_list_item_author);
            viewHolder.recipeImage = (ImageView) convertView.findViewById(R.id.recipe_list_item_image);

            // store the holder with the view.
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        Recipe recipe = getItem(position);

        if(recipe != null) {
            viewHolder.recipeTitle.setText(recipe.getTitle());
            viewHolder.recipeAuthor.setText(recipe.getAuthor());
            Ion.with(viewHolder.recipeImage)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.error_image)
                    .load(recipe.getImageUrl());
        }

        Log.d(TAG, "[getItem] url of image " + recipe.getImageUrl());

        // Return the completed view to render on screen
        return convertView;
    }




    private static class ViewHolder
    {
        public ImageView recipeImage;
        public TextView recipeTitle;
        public TextView recipeAuthor;
    }
}