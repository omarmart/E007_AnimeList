package com.omar.commands;

import com.omar.AniList;
import com.omar.Anime;
import com.omar.CommandFormatException;

public class ShowCommand extends GenericCommand {
    private final AniList aniList;

    public ShowCommand(final AniList aniList) {
        super("show");
        this.aniList = aniList;
    }

    @Override
    public void execute(String[] tokens) {
        if (tokens.length != 2) {
            throw new CommandFormatException("The correct use for this command is: show [animeID]");
        }

        try {
            int animeId = Integer.parseInt(tokens[1]);
            printAnime(aniList.getAniList().get(animeId - 1));
        } catch (NumberFormatException e) {
            System.out.println("Please insert only numbers after the show command");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("This is not a valid id");
        }
    }

    public static void printAnime(Anime anime) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("#%s %s (%s)\n", anime.getId(), anime.getName(), anime.getStatus().toString()));
        sb.append("  Progress: ").append(anime.getProgress()).append("/").append(anime.getEpisodes()).append("\n");
        sb.append("  Score: ").append(anime.getScore()).append("\n");
        sb.append("  Genre: ").append(anime.getGenre()).append("\n");
        System.out.println(sb.toString());
    }

}
