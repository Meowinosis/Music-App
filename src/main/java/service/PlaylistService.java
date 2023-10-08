package service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Song;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class PlaylistService {
    private final MongoClient mongoClient;

    public PlaylistService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public List<Document> getAllPlaylists(ObjectId userId) {
        try {
            MongoDatabase database = mongoClient.getDatabase("music_app");

            // Get the user collection
            MongoCollection<Document> playlistCollection = database.getCollection("playlists");

            List<Document> playlists = new ArrayList<>();

            // Query the playlists collection to find all playlists for the given userId
            FindIterable<Document> iterable = playlistCollection.find(eq("userId", userId));

            // Iterate over the results and add each playlist to the list
            for (Document document : iterable) {
                playlists.add(document);
            }

            return playlists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Document> getSongsInPlaylist(ObjectId playlistId) {
        try {
            MongoDatabase database = mongoClient.getDatabase("music_app");

            // Get the user collection
            MongoCollection<Document> playlistCollection = database.getCollection("playlists");
            Document playlist = playlistCollection.find(eq("_id", playlistId)).first();

            if (playlist != null) {
                List<ObjectId> songIds = playlist.getList("songIds", ObjectId.class);
                if (songIds != null && !songIds.isEmpty()) {
                    // You can query your songs collection to retrieve song details based on songIds
                    // Implement this part based on your actual schema and data structure
                    List<Document> songs = new ArrayList<>();
                    for (ObjectId songId : songIds) {
                        // Retrieve song details and add them to the list
                        Document song = getSongDetails(songId); // Implement this method
                        if (song != null) {
                            songs.add(song);
                        }
                    }
                    return songs;
                }
            }

            return new ArrayList<>(); // Return an empty list if no songs found
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addSongToPlaylist(ObjectId playlistId, Song song) {
        // Retrieve the playlist collection
        try {
            MongoDatabase database = mongoClient.getDatabase("music_app");

            // Get the user collection
            MongoCollection<Document> playlistCollection = database.getCollection("songs");

            // Create a document representing the song
            Document songDocument = new Document()
                    .append("title", song.getTitle())
                    .append("artist", song.getArtist())
                    .append("duration", song.getDurationInSeconds())
                    .append("link", song.getLink());

            // Add the song to the playlist
            Document update = new Document("$push", new Document("songIds", songDocument));
            playlistCollection.updateOne(eq("_id", playlistId), update);

            return true; // Return true to indicate success

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private Document getSongDetails(ObjectId songId) {
        try {
            MongoDatabase database = mongoClient.getDatabase("music_app");

            // Get the user collection
            MongoCollection<Document> songsCollection = database.getCollection("songs");
            return songsCollection.find(eq("_id", songId)).first();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
