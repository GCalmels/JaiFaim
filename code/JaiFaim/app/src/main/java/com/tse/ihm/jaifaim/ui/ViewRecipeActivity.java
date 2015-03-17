package com.tse.ihm.jaifaim.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.asynctask.MainGistTask;
import com.tse.ihm.jaifaim.model.Difficulty;
import com.tse.ihm.jaifaim.model.Ingredient;
import com.tse.ihm.jaifaim.model.Recipe;
import com.tse.ihm.jaifaim.model.Step;
import com.tse.ihm.jaifaim.model.Type;

import java.util.ArrayList;
import java.util.Date;

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

        MainGistTask gistTask = new MainGistTask();



        //gistTask.execute();


        Recipe recipe = new Recipe("zearzeafsdfqsdf",
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
        Log.d(TAG, "[onCreate] recette bidon : " + gson.toJson(recipe));


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
