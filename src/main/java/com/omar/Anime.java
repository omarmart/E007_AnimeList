package com.omar;

import java.io.StringWriter;

public class Anime {

    private String id;
    private String name;
    private Status status;
    private int score;
    private int progress;
    private int episodes;
    private Genre genre;

    public Anime(String id, String name, Status status, int score, int progress, int episodes, Genre genre) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.score = score;
        this.progress = progress;
        this.episodes = episodes;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public int getScore() {
        return score;
    }

    public int getProgress() {
        return progress;
    }

    public int getEpisodes() {
        return episodes;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String parseAnimeToString(Anime anime) {
        StringWriter aniString = new StringWriter();
        aniString.write(this.id);
        aniString.write(this.name);
        aniString.write(this.status.toString());
        aniString.write(this.score);
        aniString.write(this.progress);
        aniString.write(this.episodes);
        aniString.write(this.genre.toString());

        return aniString.toString();
    }

}
