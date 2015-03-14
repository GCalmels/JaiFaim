package com.tse.ihm.jaifaim.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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



