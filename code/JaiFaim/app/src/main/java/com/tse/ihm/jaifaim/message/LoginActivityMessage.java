package com.tse.ihm.jaifaim.message;

import com.tse.ihm.jaifaim.model.HungryUser;

/**
 * Created by Greggy on 19/03/15.
 */
public class LoginActivityMessage
{
    private HungryUser m_User;

    public LoginActivityMessage(HungryUser _user)
    {
        m_User = _user;
    }

    public HungryUser getUser() { return m_User; }
}
