package service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import database.MongoDBConnect;
import model.Playlist;
import model.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final MongoClient mongoClient;
    private ObjectId userId;
    public UserService(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public boolean registerUser(User user) {
        try {
            MongoDatabase database = mongoClient.getDatabase("music_app");

            // Get the user collection
            MongoCollection<Document> userCollection = database.getCollection("users");
            if (userCollection.countDocuments(new Document("username", user.getUsername())) > 0) {
                return false; // Username is taken
            }

            // Hash the password
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            // Create a new user document
            Document userDocument = new Document()
                    .append("_id", new ObjectId())
                    .append("username", user.getUsername())
                    .append("password", hashedPassword)
                    .append("name", user.getName());

            // Insert the user document into the "users" collection
            userCollection.insertOne(userDocument);

            return true; // Registration successful
        } catch (Exception e) {
            e.printStackTrace();
            return false; // An error occurred during login
        }
    }


    public boolean loginUser(String username, String password) {
        try {
            // Set up MongoDB database
            MongoDatabase database = mongoClient.getDatabase("music_app");

            // Get the user collection
            MongoCollection<Document> userCollection = database.getCollection("users");

            // Find the user document by username
            Document userDocument = userCollection.find(Filters.eq("username", username)).first();

            // If the user doesn't exist, return false
            if (userDocument == null) {
                return false; // User not found
            }
            userId = userDocument.getObjectId("_id");
            // Retrieve the hashed password from the document
            String hashedPassword = userDocument.getString("password");
            // Verify the entered password against the stored hash
            return BCrypt.checkpw(password, hashedPassword); // Login successful
        } catch (Exception e) {
            e.printStackTrace();
            return false; // An error occurred during login
        }
    }

    public ObjectId getUserId() {
        return userId;
    }

    public boolean createPlaylist(Playlist playlist) {
        try (MongoClient mongoClient = MongoDBConnect.createMongoClient()) {
            // Set up MongoDB database
            MongoDatabase database = mongoClient.getDatabase("music_app");

            // Get the user collection
            MongoCollection<Document> userCollection = database.getCollection("users");

            // Find the user document by userId
            Document userDocument = userCollection.find(Filters.eq("_id", userId)).first();

            if (userDocument == null) {
                return false; // User not found
            }

            // Create a new playlist document
            Document playlistDocument = new Document();
            playlistDocument.append("_id", new ObjectId());
            playlistDocument.append("name", playlist.getName());
            playlistDocument.append("userId", userId);
            playlistDocument.append("songs", playlist.getSongIds());
            // You can add more fields or customization as needed

            // Insert the playlist document into the "playlists" collection
            MongoCollection<Document> playlistsCollection = database.getCollection("playlists");
            playlistsCollection.insertOne(playlistDocument);

            return true; // Playlist created successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false; // An error occurred during playlist creation
        }
    }
}

