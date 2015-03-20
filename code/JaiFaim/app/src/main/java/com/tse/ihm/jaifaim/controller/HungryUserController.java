package com.tse.ihm.jaifaim.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.tse.ihm.jaifaim.message.LoginActivityMessage;
import com.tse.ihm.jaifaim.message.LoginTaskMessage;
import com.tse.ihm.jaifaim.model.HungryUser;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.UserService;

import java.io.IOException;

import de.greenrobot.event.EventBus;

/**
 * Created by Gabriel on 14/03/15.
 */
public class HungryUserController {
    private static final String TAG = HungryUserController.class.getName();


    private LoginTask m_LoginTask;
    private HungryUser m_User;

    public HungryUserController()
    {
        EventBus.getDefault().register(this);
    }

    public void onEvent(LoginActivityMessage message)
    {
        m_User = message.getUser();
        m_LoginTask = new LoginTask(m_User);
        m_LoginTask.setUserController(this);
        m_LoginTask.execute();
    }


    public User login(String _username, String _passw0rd)
    {
        GitHubClient client = new GitHubClient();
        client.setCredentials(_username, _passw0rd);

        User user;
        try {
            user = new UserService(client).getUser();
            //m_User.setGitHubUser(user);
            Log.d(TAG, "[login] user : " + user);
            return user;
        } catch (IOException e)
        {
            //m_User.setGitHubUser(null);
            e.printStackTrace();
            return null;
        }
    }


    private class LoginTask extends AsyncTask<Void, Void, Void>
    {
        private HungryUserController m_Controller;
        private HungryUser m_User;
        private boolean m_isSuccessfull;

        public LoginTask(HungryUser _user) {
            m_User = _user;
        }

        public void setUserController(HungryUserController _controller)
        {
            m_Controller = _controller;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            m_User.setGitHubUser(m_Controller.login(m_User.getUsername(), m_User.getPAssword()));
            if (m_User.getGitHubUser() != null)
            {
                m_isSuccessfull = true;
                EventBus.getDefault().unregister(HungryUserController.this);
            }
            else
                m_isSuccessfull = false;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Envoi de la réussite ou non à l'activité
            EventBus.getDefault().post(new LoginTaskMessage(m_User, m_isSuccessfull));
        }

    }
}
