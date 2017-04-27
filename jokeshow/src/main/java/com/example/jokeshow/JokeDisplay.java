package com.example.jokeshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        Intent intent = this.getIntent();
        String joke = intent.getStringExtra("Joke");
        TextView jokeText = (TextView) findViewById(R.id.joke_text_view);
        jokeText.setText(joke);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}