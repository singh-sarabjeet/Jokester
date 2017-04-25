package com.joker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Joker {

    private List<String> jokeList = new ArrayList<>();

    public Joker() {
        jokeList.add("Can a kangaroo jump higher than a house? Of course, a house doesn’t jump at all.");
        jokeList.add("Doctor: \"I'm sorry but you suffer from a terminal illness and have only 10 to live.\"\n" +
                "\n" +
                "Patient: \"What do you mean, 10? 10 what? Months? Weeks?!\"\n" +
                "\n" +
                "Doctor: \"Nine.");
        jokeList.add("Anton, do you think I’m a bad mother?\n" +
                "\n" +
                "My name is Paul.\n");
        jokeList.add("What is the difference between a snowman and a snowwoman?\n" +
                "-\n" +
                "Snowballs.\n");
        jokeList.add("My dog used to chase people on a bike a lot. It got so bad, finally I had to take his bike away.\n");
        jokeList.add("Police officer: \"Can you identify yourself, sir?\"\n" +
                " \n" +
                "Driver pulls out his mirror and says: \"Yes, it's me.\"\n");
        jokeList.add("Dentist: \"You need a crown.\"\n" +
                "-\n" +
                "Patient: \"Finally someone who understands me\"\n");
        jokeList.add("I heard women love a man in uniform. Can’t wait to start working at McDonalds.\n");
        jokeList.add("It is so cold outside I saw a politician with his hands in his own pockets.\n");
        jokeList.add("A naked women robbed a bank. Nobody could remember her face.\n");
    }

    public String getJoke() {
        Random random = new Random();
        int n = jokeList.size();
        int randomNum = Math.abs(random.nextInt()) % n;
        return jokeList.get(randomNum);
    }
}
