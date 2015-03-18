package com.tse.ihm.jaifaim.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.controller.GistController;
import com.tse.ihm.jaifaim.model.Recipe;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class ViewRecipeActivity extends ActionBarActivity
{
    private static final String TAG = ViewRecipeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        // Event
        EventBus.getDefault().register(this);




        /*Recipe recipe = new Recipe("zearzeafsdfqsdf",
                "Pates Carbonara",
                "Greg",
                new Date(),
                15,
                30,
                "https://avatars0.githubusercontent.com/u/5655900?s=140",
                new ArrayList<Ingredient>(),
                new ArrayList<Step>(),
                Difficulty.HARD,
                Type.DISH);

        Gson gson = new Gson();
        Log.d(TAG, "[onCreate] recette bidon : " + gson.toJson(recipe));*/

        GistController controller = new GistController();
        controller.getAllRecipesInBackground();
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

    public void onEvent(ArrayList<Recipe> _recipeList)
    {
        Log.d(TAG, "[onEvent] recette 0 : " + _recipeList.get(0));
    }
}
