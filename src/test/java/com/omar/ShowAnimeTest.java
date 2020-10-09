package com.omar;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class ShowAnimeTest {
    AniList anime;

    @Before
    public void loadAnilist() throws FileNotFoundException, BadFormatException {
        this.anime = new AniList("AnimeList.csv");

    }

    @Test(expected = CommandFormatException.class)
    public void shouldThrowCommandFormatException() {
        String[] input = new String[] { "show", "1", "pinga" };
        App.showAnime(this.anime, input);
    }
}
