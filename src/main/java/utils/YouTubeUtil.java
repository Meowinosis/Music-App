package utils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import model.Song;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class YouTubeUtil {
    public static Song getDataYoutube(String link)  throws GeneralSecurityException, IOException {
        // Set up the YouTube Data API
        YouTube youtube = new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                new JacksonFactory(),
                null
        ).setApplicationName("YouTube Info").build();

        // Specify the YouTube video URL

        // Extract video ID from the URL
        String videoId = link.substring(link.indexOf("v=") + 2);

        // Replace "YOUR_API_KEY" with your actual API key
        String apiKey = "AIzaSyACZw85BspJUNOPwZ7lbGibPBFjMW6Iq3M";

        // Call the YouTube API to retrieve video details
        YouTube.Videos.List listVideosRequest = youtube.videos().list("snippet,contentDetails");
        listVideosRequest.setId(videoId);
        listVideosRequest.setKey(apiKey); // Set the API key
        VideoListResponse listResponse = listVideosRequest.execute();
        List<Video> videoList = listResponse.getItems();

        if (videoList != null && !videoList.isEmpty()) {
            Video video = videoList.get(0);

            // Extract video details
            String title = video.getSnippet().getTitle();
            String artist = video.getSnippet().getChannelTitle();
            String duration = video.getContentDetails().getDuration();

            // Print the extracted information
            return new Song(title,artist,duration,link);
        } else {
            System.out.println("Video not found or API quota exceeded.");
            return null;
        }
    }
}
