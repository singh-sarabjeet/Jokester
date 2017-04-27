package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jokeshow.JokeDisplay;

public class MainActivity extends AppCompatActivity {

    private static ProgressBar mProgressBar;

    public static void showErrorToast(String error) {
        try {
            Context mContext = mProgressBar.getContext();
            switch (error) {
                case "No Network":
                    Toast.makeText(mContext, "Hey There!! It seems you aren't connected to the internet", Toast.LENGTH_SHORT).show();
                    break;
                case "No Joke":
                    Toast.makeText(mContext, "Error Retrieving a joke that matches your standards.Try Again!!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(mContext, "Hmm Something smells fishy!! Let's come back again in sometime", Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public static void showJoke(String joke) {
        try {
            Context mContext = mProgressBar.getContext();
            mProgressBar.setVisibility(View.GONE);
            Intent intent = new Intent(mContext, JokeDisplay.class);
            intent.putExtra("Joke", joke);
            mContext.startActivity(intent);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
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
            mProgressBar.setVisibility(View.VISIBLE);
            new RetrieveJokeTask().execute();
        } else {
            showErrorToast("No Network");
        }
    }
}
