package com.omar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.undo.StateEdit;

public class AniList {
    private String fileLocation;
    private List<Anime> animeList;

    public AniList(String fileLocation) throws FileNotFoundException, BadFormatException {
        animeList = new ArrayList<>();
        loadAnimeList(fileLocation);
        this.fileLocation = fileLocation;
    }

    private AniList(List<Anime> animeList) {
        this.animeList = animeList;
    }

    public List<Anime> getAniList() {
        return animeList;
    }

    public void loadAnimeList(String fileLocation) throws FileNotFoundException, BadFormatException {
        File file = new File(fileLocation);
        Scanner sc = new Scanner(new FileInputStream(file));

        int position = 0;

        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            String[] parsedLine = nextLine.split(",");

            if (position == 0) {
                position++;
                continue;
            }

            try {
                if (true) { // TODO: verifyLine(parsedLine)
                    Anime newAnime;
                    String id = parsedLine[0];
                    String name = parsedLine[1];
                    Status status = Status.valueOf(parsedLine[2]);
                    int score = Integer.parseInt(parsedLine[3]);
                    int progress = Integer.parseInt(parsedLine[4]);
                    int episodes = Integer.parseInt(parsedLine[5]);
                    Genre genre = Genre.valueOf(parsedLine[6]);

                    newAnime = new Anime(id, name, status, score, progress, episodes, genre);

                    animeList.add(newAnime);
                }
            } catch (Exception e) {
                throw new BadFormatException(position);
            }
            position++;
        }
    }

    public void updateAnimeList(Anime changedAnime) {
        for (Anime anime : animeList) {
            if (anime.getId().equals(changedAnime.getId())) {
                anime = changedAnime;
            }
        }
    }

    public void updateAnimeFile() throws IOException {
        FileWriter writer;
        writer = new FileWriter(fileLocation);

        for (Anime anime : animeList) {
            writer.write(anime.parseAnimeToString());
        }

        writer.close();
    }

    public List<Anime> searchAnime(Optional<Status> status, Optional<Genre> genre, Optional<String> name) {
        List<Anime> searchedAnime = new ArrayList<>(this.animeList);
        Iterator<Anime> it = searchedAnime.iterator();

        while (it.hasNext()) {
            Anime toCheck = it.next();

            if (status.isPresent()) {
                Status filter = status.get();
                if (toCheck.getStatus() != filter) {
                    it.remove();
                }
            }

            if (genre.isPresent()) {
                Genre filter = genre.get();
                if (toCheck.getGenre() != filter) {
                    it.remove();
                }
            }
            if (name.isPresent()) {
                String filter = name.get();
                if (!toCheck.getName().contains(filter)) {
                    it.remove();
                }
            }
        }

        return searchedAnime;
    }

}
