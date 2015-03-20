package com.tse.ihm.jaifaim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.controller.HungryUserController;
import com.tse.ihm.jaifaim.message.LoginActivityMessage;
import com.tse.ihm.jaifaim.message.LoginTaskMessage;
import com.tse.ihm.jaifaim.model.HungryUser;

import de.greenrobot.event.EventBus;
import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class LoginActivity extends RoboActionBarActivity
{

    private static final String TAG = LoginActivity.class.getName();

    @InjectView(R.id.edittext_username) private EditText m_EditTextUsername;
    @InjectView(R.id.edittext_password) private EditText m_EditTextPassw0rd;
    @InjectView(R.id.login_activity_progress_bar) private CircularProgressBar m_ProgressBar;



    private HungryUserController m_UserController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m_UserController = new HungryUserController();


        m_ProgressBar.setIndeterminateDrawable(new CircularProgressDrawable
                .Builder(this)
                .colors(getResources().getIntArray(R.array.gplus_colors))
                .build());
        m_ProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    public void onEvent(LoginTaskMessage message)
    {
        m_ProgressBar.setVisibility(View.GONE);
        Log.d(TAG, "[onEvent] connection : " + message.getConnectionSuccessfull());

        if (message.getConnectionSuccessfull())
        {
            EventBus.getDefault().postSticky(message.getUser());
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);

        } else {
            Toast.makeText(LoginActivity.this, "Echec : les identifiants ne correspondent pas!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Class c = com.tse.ihm.jaifaim.Menu.itemSelected(item);
        if (c == MainActivity.class) {
            finish();
        }
        return true;
    }

    public void login(View v)
    {
        m_ProgressBar.setVisibility(View.VISIBLE);

        HungryUser user = new HungryUser(m_EditTextUsername.getText().toString(), m_EditTextPassw0rd.getText().toString());
        LoginActivityMessage message = new LoginActivityMessage(user);
        EventBus.getDefault().postSticky(message);
    }
}



