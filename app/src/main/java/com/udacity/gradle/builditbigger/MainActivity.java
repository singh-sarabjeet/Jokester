package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jokeshow.JokeDisplay;
import com.example.sarabjeet.jokester.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private Context mContext = this;

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

    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void tellJoke(View view) {
        if (haveNetworkConnection()) {
            mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
            mProgressBar.setVisibility(View.VISIBLE);
            new RetrieveJokeTask().execute();
        } else {
            showErrorToast("No Network");
        }
    }

    public void showErrorToast(String error) {
        switch (error) {
            case "No Network":
                Toast.makeText(this, "Hey There!! It seems you aren't connected to the internet", Toast.LENGTH_SHORT).show();
                break;
            case "No Joke":
                Toast.makeText(this, "Error Retrieving a joke that matches your standards.Try Again!!", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Hmm Something smells fishy!! Let's come back again in sometime", Toast.LENGTH_SHORT).show();
                break;
        }

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
            mProgressBar.setVisibility(View.GONE);
            if (result != null) {
                Intent intent = new Intent(mContext, JokeDisplay.class);
                intent.putExtra("Joke", result);
                startActivity(intent);
            } else
                showErrorToast("No Joke");
        }
    }
}
