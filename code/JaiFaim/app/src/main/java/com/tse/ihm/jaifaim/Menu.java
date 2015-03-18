package com.tse.ihm.jaifaim;

import android.view.MenuItem;

import com.tse.ihm.jaifaim.ui.LoginActivity;
import com.tse.ihm.jaifaim.ui.MainActivity;
import com.tse.ihm.jaifaim.ui.NewRecipeActivity;

/**
 * Created by Gabriel on 18/03/15.
 */
public class Menu {

    public static Class itemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            // Demande de connexion
            case R.id.action_login:
                return LoginActivity.class;
            // Annulation de la connexion
            case R.id.action_login_cancel:
                return MainActivity.class;
            // Demande de nouvelle recette
            case R.id.action_new_recipe:
                return NewRecipeActivity.class;

            default:
                return null;

        }
    }
}
