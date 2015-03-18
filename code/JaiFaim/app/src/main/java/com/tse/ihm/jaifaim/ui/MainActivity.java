package com.tse.ihm.jaifaim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.adapter.RecipeAdapter;
import com.tse.ihm.jaifaim.controller.GistController;
import com.tse.ihm.jaifaim.model.Recipe;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getName();
    private ArrayList<Recipe> m_RecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        // TODO: Test de connexion
        //Intent i = new Intent(this, LoginActivity.class);
        //startActivity(i);

        GistController controller = new GistController();
        controller.getAllRecipesInBackground();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Class c = com.tse.ihm.jaifaim.Menu.itemSelected(item);
        if (c != null) {
            Intent i = new Intent(this, c);
            startActivity(i);
        }
        return true;
    }

    public void onEvent(ArrayList<Recipe> _recipeList)
    {
        m_RecipeList = _recipeList;

        Log.d(TAG, "[onEvent] recette 0 : " + _recipeList.get(0));
        // Create the adapter to convert the array to views
        RecipeAdapter adapter = new RecipeAdapter(this, m_RecipeList);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.recipe_list);
        listView.setAdapter(adapter);
    }

}
