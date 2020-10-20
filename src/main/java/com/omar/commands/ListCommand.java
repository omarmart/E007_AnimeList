package com.omar.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.omar.AniList;
import com.omar.Anime;
import com.omar.CommandFormatException;
import com.omar.Genre;
import com.omar.Status;

public class ListCommand extends GenericCommand {
    private final AniList aniList;

    public ListCommand(final AniList aniList) {
        super("list");
        this.aniList = aniList;
    }

    @Override
    public void execute(String[] tokens) {
        Predicate<Anime> filters = (anime) -> true;

        Map<String, Function<String, Predicate<Anime>>> searchCommands = new HashMap<>();
        searchCommands.put("-anime", (name) -> (anime) -> anime.getName().toLowerCase().contains(name.toLowerCase()));
        searchCommands.put("-genre", (genre) -> (anime) -> anime.getGenre() == Genre.valueOf(genre));
        searchCommands.put("-status", (status) -> (anime) -> anime.getStatus() == Status.valueOf(status));

        try {
            for (int i = 0; i < tokens.length; i++) {
                Function<String, Predicate<Anime>> filterFactory = searchCommands.get(tokens[i]);
                if (filterFactory != null) {
                    if (i + 1 >= tokens.length) {
                        throw new CommandFormatException("Missing parameter for argument " + tokens[i]);
                    }
                    filters = filters.and(filterFactory.apply(tokens[i + 1]));
                }
            }

            printAnilist(aniList.searchAnime(filters));

        } catch (IllegalArgumentException illE) {
            System.out.println("Status/Genre is not a valid");
        }
    }

    public static void printAnilist(Collection<Anime> animeList) {
        if (animeList.size() == 0) {
            System.out.println("No anime with these parameters was found.");
            return;
        }

        for (Anime anime : animeList) {
            System.out.println(String.format("#%s %s", anime.getId(), anime.getName()));
        }
    }
}
