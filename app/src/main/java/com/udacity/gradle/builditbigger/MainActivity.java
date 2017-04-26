package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jokeshow.JokeDisplay;
import com.example.sarabjeet.jokester.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String joke = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void tellJoke(View view) {
        new RetrieveJokeTask().execute();
        if (joke != null) {
            Intent intent = new Intent(this, JokeDisplay.class);
            intent.putExtra("Joke", joke);
            startActivity(intent);

            Toast.makeText(this, "Joke Retrieved", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "derp", Toast.LENGTH_SHORT).show();
    }

    public class RetrieveJokeTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://jokester-165816.appspot.com/_ah/api/");

            MyApi myApiService = builder.build();

            String joke = null;

            try {
                joke = myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return joke;
        }

        @Override
        protected void onPostExecute(String result) {
            joke = result;
        }
    }


}
