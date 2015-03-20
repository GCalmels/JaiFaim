package com.tse.ihm.jaifaim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.model.Recipe;
import com.tse.ihm.jaifaim.model.Step;

import java.util.ArrayList;
import java.util.LinkedList;

import de.greenrobot.event.EventBus;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class StepActivity extends RoboActionBarActivity
{
    private static final String TAG = StepActivity.class.getName();

    @InjectView(R.id.step_flingView)      private SwipeFlingAdapterView m_FlingCard;

    private Recipe m_Recipe;
    private LinkedList<String> m_StepList;
    private LinkedList<String> m_StepsDone;
    private ArrayAdapter<String> m_ArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        m_StepsDone = new LinkedList<>();

        //choose your favorite adapter
        m_ArrayAdapter = new ArrayAdapter<String>(this, R.layout.step_item, R.id.step_description, m_StepList);

        //set the listener and the adapter
        m_FlingCard.setAdapter(m_ArrayAdapter);
        m_FlingCard.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");

                //m_ArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                if (m_StepList.size() > 0) {
                    m_StepsDone.addLast(m_StepList.remove(0));
                    if (m_StepList.size() == 0) {
                        finish();
                        Toast.makeText(StepActivity.this, "Bravo vous avez terminé la recette !", Toast.LENGTH_LONG).show();
                    }
                } else {

                }

                m_ArrayAdapter.notifyDataSetChanged();

                Log.d(TAG, "[onLeftCardExit] steps done : " + m_StepsDone);
                Log.d(TAG, "[onLeftCardExit] steps to do : " + m_StepList);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                if (m_StepsDone.size() > 0) {
                    m_StepList.addFirst(m_StepsDone.remove(0));
                } else {
                    Toast.makeText(StepActivity.this, "Vous êtes à la première étape!", Toast.LENGTH_LONG).show();
                }


                m_ArrayAdapter.notifyDataSetChanged();
                Toast.makeText(StepActivity.this, "Right!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "[onRightCardExit] steps done : " + m_StepsDone);
                Log.d(TAG, "[onRightCardExit] steps to do : " + m_StepList);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                //m_StepList.add("XML ".concat(String.valueOf(itemsInAdapter)));
                m_ArrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                itemsInAdapter++;
            }

            @Override
            public void onScroll(float v)
            {

            }
        });

        // Optionally add an OnItemClickListener
        /*flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(MyActivity.this, "Clicked!");
            }
        });*/
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }

    public void onEvent(Recipe _recipe)
    {
        m_Recipe = _recipe;

        m_StepList = new LinkedList<>();
        for (Step step: m_Recipe.getStepList())
        {
            m_StepList.add(step.getDescription());
        }

        //choose your favorite adapter
        m_ArrayAdapter = new ArrayAdapter<String>(this, R.layout.step_item, R.id.step_description, m_StepList);

        //set the listener and the adapter
        m_FlingCard.setAdapter(m_ArrayAdapter);
        m_ArrayAdapter.notifyDataSetChanged();
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
        getMenuInflater().inflate(R.menu.menu_step, menu);
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
