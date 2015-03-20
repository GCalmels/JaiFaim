package com.tse.ihm.jaifaim.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.adapter.RecipeAdapter;
import com.tse.ihm.jaifaim.controller.GistController;
import com.tse.ihm.jaifaim.helper.UserHelper;
import com.tse.ihm.jaifaim.model.MainGist;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class ViewUserActivity extends RoboActionBarActivity {

    @InjectView (R.id.user_activity_username) private TextView m_UserName;
    @InjectView (R.id.user_activity_my_recipe_list) private ListView m_RecipeList;
    @InjectView (R.id.user_activity_favorite_recipe_list) private ListView m_FavoriteRecipeList;

    @Inject
    UserHelper m_UserHelper;

    private GistController m_GistController;
    private MainGist m_MainGist;
    private RecipeAdapter m_RecipeAdapter;
    private RecipeAdapter m_FavoriteRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        // Create the adapter to convert the array to views
        m_RecipeAdapter = new RecipeAdapter(this, m_MainGist.getRecipeList());
        // Attach the adapter to a ListView
        m_RecipeList.setAdapter(m_RecipeAdapter);

        // Create the adapter to convert the array to views
        m_FavoriteRecipeAdapter = new RecipeAdapter(this, m_MainGist.getRecipeList());
        // Attach the adapter to a ListView
        m_FavoriteRecipeList.setAdapter(m_FavoriteRecipeAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
