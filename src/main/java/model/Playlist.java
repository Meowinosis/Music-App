package model;

import org.bson.types.ObjectId;

import java.util.List;

public class Playlist {
    private ObjectId id;
    private String name;
    private ObjectId userId; // Reference to the user who owns the playlist
    private List<ObjectId> songIds; // References to the songs in the playlist

    public Playlist() {
    }

    public Playlist(String name, ObjectId userId, List<ObjectId> songIds) {
        this.name = name;
        this.userId = userId;
        this.songIds = songIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public List<ObjectId> getSongIds() {
        return songIds;
    }

    public void setSongIds(List<ObjectId> songIds) {
        this.songIds = songIds;
    }
}