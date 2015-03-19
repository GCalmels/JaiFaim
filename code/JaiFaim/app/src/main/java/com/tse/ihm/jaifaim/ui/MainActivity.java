package com.tse.ihm.jaifaim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.adapter.RecipeAdapter;
import com.tse.ihm.jaifaim.controller.GistController;
import com.tse.ihm.jaifaim.message.NewGistTaskMessage;
import com.tse.ihm.jaifaim.model.MainGist;
import com.tse.ihm.jaifaim.model.Recipe;

import de.greenrobot.event.EventBus;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;


public class MainActivity extends RoboActionBarActivity implements OnRefreshListener
{

    private static final String TAG = MainActivity.class.getName();

    @InjectView(R.id.swipe_container)      private SwipeRefreshLayout m_SwipeContainer;
    @InjectView(R.id.recipe_list)          private ListView m_ListView;

    private GistController m_GistController;
    private MainGist m_MainGist;

    public MainActivity()
    {
        m_GistController = new GistController();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        m_SwipeContainer.setOnRefreshListener(this);
        m_SwipeContainer.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // TODO: Test de connexion
        //Intent i = new Intent(this, LoginActivity.class);
        //startActivity(i);


        m_ListView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l)
            {
                Recipe recipe = (Recipe)adapterView.getItemAtPosition(pos);
                EventBus.getDefault().postSticky(recipe);
                Intent intent = new Intent(MainActivity.this, ViewRecipeActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        // Workaround d'un bug dans le swipelayout
        // see : https://code.google.com/p/android/issues/detail?id=77712
        TypedValue typed_value = new TypedValue();
        getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
        m_SwipeContainer.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));

        showProgress();
        GistController controller = new GistController();
        controller.getAllRecipesInBackground();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        showProgress();
    }

    @Override
    public void onRefresh()
    {
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

    public void onEvent(MainGist _mainGist)
    {
        hideProgress();
        m_MainGist = _mainGist;

        Log.d(TAG, "[onEvent] recette 0 : " + _mainGist.getRecipeList().get(0));
        refreshListView();
    }

    public void onEvent(NewGistTaskMessage message)
    {
        if (message.getResult())
        {
            m_MainGist = m_GistController.getMainGist();
            refreshListView();
            hideProgress();
        } else {
            Log.d(TAG, "[onEvent] recipe not created");
        }
    }

    public void refreshListView()
    {
        // Create the adapter to convert the array to views
        RecipeAdapter adapter = new RecipeAdapter(this, m_MainGist.getRecipeList());
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.recipe_list);
        listView.setAdapter(adapter);
    }

    public void showProgress()
    {
        m_SwipeContainer.setRefreshing(true);
    }

    public void hideProgress()
    {
        m_SwipeContainer.setRefreshing(false);
    }

}
