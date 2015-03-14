package com.tse.ihm.jaifaim.controller;

import com.tse.ihm.jaifaim.model.HungryUser;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.UserService;

import java.io.IOException;

/**
 * Created by Gabriel on 14/03/15.
 */
public class HungryUserController {

    private HungryUser m_User;

    public static boolean login(String _username, String _passw0rd)
    {
        GitHubClient client = new GitHubClient();
        client.setCredentials(_username, _passw0rd);

        User user = null;
        try {
            user = new UserService(client).getUser();
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
