package com.udacity.gradle;

/**
 * Created by sarabjeet on 27/4/17.
 */


import com.udacity.gradle.builditbigger.RetrieveJokeTask;

import org.junit.Test;

public class JokeTest {

    @Test
    public void testJoke() {
        RetrieveJokeTask task = new RetrieveJokeTask();
        task.execute();
        try {
            String joke = task.get();
            assert !joke.equals(null);
            assert joke.length() > 0;
        } catch (Exception e) {

        }
    }
}
