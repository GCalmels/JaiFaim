package com.tse.ihm.jaifaim.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.model.Recipe;

import java.util.ArrayList;

/**
 * Created by Gabriel on 18/03/15.
 */
public class RecipeAdapter extends BaseAdapter implements Filterable
{
    private static final String TAG = Recipe.class.getName();

    private Context m_Context;

    private ArrayList<Recipe> m_RecipeList;
    private ArrayList<Recipe> m_FilteredRelicipeList;

    private RecipeFilter m_RecipeFilter;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        super();
        m_Context = context;
        m_RecipeList = recipes;
        m_FilteredRelicipeList = recipes;
    }

    @Override
    public int getCount()
    {
        return m_FilteredRelicipeList.size();
    }

    @Override
    public Recipe getItem(int i)
    {
        return m_FilteredRelicipeList.get(i);
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

    @Override
    public Filter getFilter() {
        if (m_RecipeFilter == null) {
            m_RecipeFilter = new RecipeFilter();
        }

        return m_RecipeFilter;
    }


    private static class ViewHolder
    {
        public ImageView recipeImage;
        public TextView recipeTitle;
        public TextView recipeAuthor;
    }

    /**
     * Custom filter for recipe list
     * Filter content in recipe list according to the search text
     */
    private class RecipeFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Recipe> tempList = new ArrayList<Recipe>();

                // search content in friend list
                for (Recipe recipe : m_RecipeList) {
                    if (recipe.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())
                            || recipe.getAuthor().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(recipe);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = m_RecipeList.size();
                filterResults.values = m_RecipeList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            m_FilteredRelicipeList = (ArrayList<Recipe>) results.values;
            notifyDataSetChanged();
        }
    }

}