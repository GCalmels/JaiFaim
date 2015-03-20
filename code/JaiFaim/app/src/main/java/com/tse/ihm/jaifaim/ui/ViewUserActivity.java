package com.tse.ihm.jaifaim.ui;

import android.os.Bundle;
import android.util.Log;
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

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class ViewUserActivity extends RoboActionBarActivity implements MaterialTabListener
{
    private static final String TAG = ViewUserActivity.class.getName();


    @InjectView (R.id.tabHost) private MaterialTabHost m_TabHost;
    @InjectView (R.id.user_activity_username) private TextView m_UserName;
    @InjectView (R.id.user_activity_my_recipe_list_description) private TextView m_ListDescription;
    @InjectView (R.id.user_activity_my_recipe_list) private ListView m_RecipeList;
    //@InjectView (R.id.user_activity_favorite_recipe_list) private ListView m_FavoriteRecipeList;

    @Inject
    UserHelper m_UserHelper;

    private GistController m_GistController;
    private MainGist m_MainGist;
    private RecipeAdapter m_RecipeAdapter;
    private RecipeAdapter m_FavoriteRecipeAdapter;

    private int m_SelectedTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        initTabs();

        m_GistController = new GistController();
        m_UserHelper.getUser().setMyRecipes(m_GistController.getRecipeOfUser(m_UserHelper.getUser()));

        // Ajout du titre de l'activité
        setTitle(m_UserHelper.getUser().getUsername());

        // Create the adapter to convert the array to views
        m_RecipeAdapter = new RecipeAdapter(this, m_UserHelper.getUser().getMyRecipes());
        // Attach the adapter to a ListView
        m_RecipeList.setAdapter(m_RecipeAdapter);

        // Create the adapter to convert the array to views
        m_FavoriteRecipeAdapter = new RecipeAdapter(this, m_UserHelper.getUser().getMyRecipes());
        // Attach the adapter to a ListView
        //m_FavoriteRecipeList.setAdapter(m_FavoriteRecipeAdapter);
    }


    private void initTabs() {
        m_TabHost.addTab(m_TabHost.newTab().setIcon(getResources().getDrawable(R.drawable.ingredient_list)).setTabListener(this));
        m_TabHost.addTab(m_TabHost.newTab().setIcon(getResources().getDrawable(R.drawable.favorites)).setTabListener(this));
        //m_TabHost.addTab(m_TabHost.newTab().setIcon(getResources().getDrawable(R.drawable.ingredient_list)).setTabListener(this));
        //m_TabHost.addTab(m_TabHost.newTab().setIcon(getResources().getDrawable(R.drawable.ingredient_list)).setTabListener(this));

    }


    /////////////////////////////////////////////
    // Interface MaterialTab
    /////////////////////////////////////////////

    @Override
    public void onTabSelected(MaterialTab tab) {
        //pager.setCurrentItem(tab.getPosition());
        // Filtrer l'affichage
        m_SelectedTab = tab.getPosition();

        m_TabHost.setSelectedNavigationItem(m_SelectedTab);

        displayPostType();
    }

    public void displayPostType() {
        switch (m_SelectedTab) {
            case 0:
                Log.d(TAG, "[onTabSelected] Filter nothing");

                m_ListDescription.setText("Mes recettes");

                break;

            case 1:
                Log.d(TAG, "[onTabSelected] Filter articles");

                // TODO : afficher les recettes favorites
                m_ListDescription.setText("Mes recettes favorites");

                break;

            case 2:
                Log.d(TAG, "[onTabSelected] Filter events");

                /*

                ArrayList<PostParse> postArray = mParseController.getEventsOfMonth(mCalendarView.getCurrentMonth());
                mListNewsToView.clear();
                mListNewsToView.addAll(postArray);
                changeAdapter();

                for (PostParse p : mListNewsToView) {
                    mCalendarView.setBusyDay(DateUtil.getCalDayFromDate(p.getEventDateStart()));
                }
*/
                break;

            case 3:
                Log.d(TAG, "[onTabSelected] Filter others");

                /*

                mListNewsToView.clear();
                mListNewsToView.addAll(mParseController.getPostCategory(PostParse.TypeOfInfos.autre));


                changeAdapter();
*/
                break;
        }
    }

    /**
     * Change les éléments présents dans la listView
     */
    private void changeAdapter() {
        /*m_RecipeAdapter = new RecipeAdapter(this, mListNewsToView);

        if (mListView != null) {
            mListView.setAdapter(mAdapter);
        }
        m_RecipeAdapter.notifyDataSetChanged();*/
    }


    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

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
