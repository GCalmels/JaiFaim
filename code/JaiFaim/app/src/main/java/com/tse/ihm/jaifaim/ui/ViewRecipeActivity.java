package com.tse.ihm.jaifaim.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.model.Recipe;

import de.greenrobot.event.EventBus;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class ViewRecipeActivity extends RoboActionBarActivity
{
    private static final String TAG = ViewRecipeActivity.class.getName();

    @InjectView(R.id.view_recipe_title)      private TextView m_Title;
    @InjectView(R.id.view_recipe_button_like)      private ImageButton m_ButtonLike;
    @InjectView(R.id.view_recipe_button_2)      private ImageButton m_Button2;
    @InjectView(R.id.view_recipe_button_3)      private ImageButton m_Button3;
    @InjectView(R.id.view_recipe_button_4)      private ImageButton m_Button4;
    @InjectView(R.id.view_recipe_button_5)      private ImageButton m_Button5;
    @InjectView(R.id.view_recipe_image)      private ImageView m_Image;
    @InjectView(R.id.view_recipe_cooking_time)      private TextView m_CookingTime;
    @InjectView(R.id.view_recipe_prep_time)      private TextView m_PrepTime;
    @InjectView(R.id.view_recipe_difficulty)      private TextView m_Difficulty;
    @InjectView(R.id.view_recipe_likes)      private TextView m_Likes;
    @InjectView(R.id.view_recipe_number_people)      private TextView m_NumberOfPeople;
    @InjectView(R.id.view_recipe_ingredients)      private TextView m_Ingredients;
    @InjectView(R.id.view_recipe_button_start)      private Button m_StartRecipe;




    private Recipe m_Recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
    }

    /**
     * Rempli les champs de la vue
     */
    public void fillRecipeFields()
    {
        m_Title.setText(m_Recipe.getTitle());
        Ion.with(m_Image)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error_image)
                .load(m_Recipe.getImageUrl());
        m_CookingTime.setText(m_Recipe.getCookingTime());
        m_PrepTime.setText(m_Recipe.getPrepTime());
        m_Difficulty.setText(m_Recipe.getDifficulty().toString());
        //TODO: get likes
        //m_Likes.setText(m_Recipe.getLikes());
        m_Ingredients.setText(m_Recipe.getIngredientList().get(0).getName());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }

    public void onEvent(Recipe _recipe)
    {
        m_Recipe = _recipe;
        fillRecipeFields();

    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
