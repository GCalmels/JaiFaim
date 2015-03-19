package com.tse.ihm.jaifaim.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

        // Link to sign up
        TextView signUpTxtView = (TextView)findViewById(R.id.login_activity_sign_up);
        String linkText = "Vous n'avez pas de compte? Quel honte!" +
                "Par <a href='https://github.com/join'>ici</a> pour en créer un!";
        signUpTxtView.setText(Html.fromHtml(linkText));
        signUpTxtView.setMovementMethod(LinkMovementMethod.getInstance());

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



