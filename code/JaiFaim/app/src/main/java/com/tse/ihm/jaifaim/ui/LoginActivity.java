package com.tse.ihm.jaifaim.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.tse.ihm.jaifaim.R;
import com.tse.ihm.jaifaim.asynctask.LoginTask;

import de.greenrobot.event.EventBus;

public class LoginActivity extends ActionBarActivity {

    private static final String TAG = LoginActivity.class.getName();

    private EditText m_EditTextUsername;
    private EditText m_EditTextPassw0rd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m_EditTextUsername = (EditText)findViewById(R.id.edittext_username);
        m_EditTextPassw0rd = (EditText)findViewById(R.id.edittext_password);

        // Event
        EventBus.getDefault().register(this);
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
        LoginTask loginTask = new LoginTask(m_EditTextUsername.getText().toString(),
                                            m_EditTextPassw0rd.getText().toString());
        loginTask.execute();
    }

    public void onEvent(Boolean _isSuccessfull)
    {
        if (_isSuccessfull)
        {
            Log.d(TAG, "Login successfull");
        }
        else
        {
            Log.d(TAG, "Login unsuccessfull");
        }
    }
}



