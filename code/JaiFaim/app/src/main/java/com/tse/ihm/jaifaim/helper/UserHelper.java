package com.tse.ihm.jaifaim.helper;

import android.content.Context;


import com.tse.ihm.jaifaim.model.HungryUser;

import javax.inject.Inject;
import javax.inject.Singleton;

import roboguice.activity.RoboActivity;

/**
 * Created by ghml9265 on 20/03/2015.
 */

@Singleton
public class UserHelper extends RoboActivity {
    HungryUser m_User;

    public UserHelper() {
    }

    public HungryUser getUser()
    {
        return m_User;
    }

    public void setUser(HungryUser _user)
    {
        m_User = _user;
    }
}
