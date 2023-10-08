package database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoDBConnect {

    public static MongoClient createMongoClient() {
        String connectionString = "mongodb+srv://beo3b123:bjnbeou123@musicapp.mzrgo1w.mongodb.net/?retryWrites=true&w=majority&appName=AtlasApp";
        ConnectionString connString = new ConnectionString(connectionString);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .build();
        // Create and return a MongoClient with the configured settings
        return MongoClients.create(settings);
    }
    public static void closeMongoClient(MongoClient client) {
        if (client != null) {
            client.close();
        }
    }


}

