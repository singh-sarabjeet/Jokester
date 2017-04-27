package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.sarabjeet.jokester.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by sarabjeet on 27/4/17.
 */

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
        if (result != null) {
            MainActivity.showJoke(result);
        } else {
            MainActivity.showErrorToast("No Joke");
        }
    }
}