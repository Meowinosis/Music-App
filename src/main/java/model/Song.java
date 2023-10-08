package model;

import org.bson.types.ObjectId;

public class Song {
    private ObjectId id;
    private String title;
    private String artist;
    private String durationInSeconds;
    private String link;
    public Song() {
    }

    public Song(String title, String artist, String durationInSeconds,String link) {
        this.title = title;
        this.artist = artist;
        this.durationInSeconds = durationInSeconds;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(String durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
