package for_testing;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class YoutubeDlExample {
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        // Set up the YouTube Data API
        YouTube youtube = new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                new JacksonFactory(),
                null
        ).setApplicationName("YouTube Info").build();

        // Specify the YouTube video URL
        String videoUrl = "https://www.youtube.com/watch?v=LR4gNyNpYtE"; // Replace VIDEO_ID with the actual video ID

        // Extract video ID from the URL
        String videoId = videoUrl.substring(videoUrl.indexOf("v=") + 2);

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
            System.out.println("Title: " + title);
            System.out.println("Artist: " + artist);
            System.out.println("Duration: " + duration.replace("PT",""));
        } else {
            System.out.println("Video not found or API quota exceeded.");
        }
    }
}
