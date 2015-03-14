package com.tse.ihm.jaifaim.ui;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.tse.ihm.jaifaim.R;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;
import org.kohsuke.github.GHGist;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Connexion
        Connection connection = new Connection();
        connection.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class Connection extends AsyncTask<Void, Void, Void>
    {
        GistFile file;
        GitHubClient client;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Début du traitement asynchrone", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            GitHub github = null;
            /*try {
                //github = GitHub.connectUsingPassword("driftse@gmail.com", "Skad9pEj8durj7yoow4jaL6Con4U");

                //Log.d("Test",github.getMyself().getEmail());
                //GHGist gist = github.getGist("64768ed4fc53d4c39f3f");
                //Log.d("Test", gist.getDescription());
                //Log.d("Test", "YESSSSSSSS");
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            client = new GitHubClient();
            //client.setCredentials("user", "passw0rd");
            client.setOAuth2Token("8ddca02b04545c93670515196bd45ac158e24206");

            GistService gistService = new GistService();
            try {
                Gist gist = gistService.getGist("64768ed4fc53d4c39f3f");
                Map<String, GistFile> files = gist.getFiles();
                file = files.get("recipe_list");
                Log.d("Test", files.keySet().toString());
                Log.d("Test", "Test");

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(getApplicationContext(), "Le traitement asynchrone est terminé", Toast.LENGTH_LONG).show();
            TextView txt = (TextView)findViewById(R.id.title);
            if (file != null)
                txt.setText(file.getContent());
            txt.setText(client.getUser());
        }
    }

}
