package com.omar.commands;

import java.io.IOException;

import com.omar.AniList;
import com.omar.Anime;
import com.omar.CommandFormatException;
import com.omar.Status;

public class ChangeCommand extends GenericCommand {
    private final AniList aniList;

    public ChangeCommand(final AniList aniList) {
        super("change");
        this.aniList = aniList;
    }

    @Override
    public void execute(String[] tokens) {
        try {
            int animeId = Integer.parseInt(tokens[1]);
            Anime toChange = aniList.getAniList().get(animeId - 1);

            for (int i = 0; i < tokens.length; i++) {
                switch (tokens[i]) {
                    case "-status":
                        Status newStatus = Status.valueOf(tokens[i + 1]);
                        toChange.setStatus(newStatus);
                        break;
                    case "-score":
                        int newScore = Integer.parseInt(tokens[i + 1]);
                        if (newScore >= 0 && newScore <= 10) {
                            toChange.setScore(newScore);
                        } else {
                            throw new CommandFormatException("Score must be between 0 an 10");
                        }
                        break;

                    case "-progress":
                        int newProgress = Integer.parseInt(tokens[i + 1]);
                        if (newProgress >= 0 && newProgress <= toChange.getEpisodes()) {
                            toChange.setProgress(newProgress);
                        } else {
                            throw new CommandFormatException(
                                    "Progress must be between 0 and " + toChange.getEpisodes());
                        }

                        break;
                }
            }

            aniList.updateAnimeList(toChange);
            aniList.updateAnimeFile();
            System.out.println("Change succesfull!");

        } catch (NumberFormatException numE) {
            System.out.println("Please insert only numbers after change/progress/score");
        } catch (IndexOutOfBoundsException indE) {
            System.out.println(tokens[1] + " is not a valid id");
        } catch (IllegalArgumentException illE) {
            System.out.println("Status is not a valid");
        } catch (IOException e) {
            System.out.println("Anime file not found");
        }
    }
}
