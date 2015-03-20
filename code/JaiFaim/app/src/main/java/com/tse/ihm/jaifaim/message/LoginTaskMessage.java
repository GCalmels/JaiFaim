package com.tse.ihm.jaifaim.message;

import com.tse.ihm.jaifaim.model.HungryUser;

/**
 * Created by Greggy on 19/03/15.
 */
public class LoginTaskMessage
{
    private boolean m_ConnectionSuccessfull;
    private HungryUser m_User;

    public LoginTaskMessage(HungryUser _user, boolean _connectionSuccess)
    {
        m_User = _user;
        m_ConnectionSuccessfull = _connectionSuccess;
    }

    public boolean getConnectionSuccessfull()
    {
        return m_ConnectionSuccessfull;
    }

    public HungryUser getUser() { return m_User; }

}
