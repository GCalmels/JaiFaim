package com.tse.ihm.jaifaim.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.tse.ihm.jaifaim.controller.HungryUserController;

import de.greenrobot.event.EventBus;

/**
 * Created by Gabriel on 14/03/15.
 */
public class LoginTask extends AsyncTask<Void, Void, Void>{

    private String m_Username;
    private String m_Passw0rd;
    private boolean m_isSuccessfull;

    public LoginTask(String _username, String _passw0rd) {
        m_Username = _username;
        m_Passw0rd = _passw0rd;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        m_isSuccessfull = HungryUserController.login(m_Username, m_Passw0rd);


        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        /*Toast.makeText(getApplicationContext(), "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
        TextView txt = (TextView)findViewById(R.id.title);
        if (file != null)
            txt.setText(file.getContent());
        txt.setText(client.getUser());*/
        EventBus.getDefault().post(m_isSuccessfull);
    }

}
