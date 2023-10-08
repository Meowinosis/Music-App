package view;

import model.Song;
import org.bson.types.ObjectId;
import service.PlaylistService;
import utils.YouTubeUtil;

import java.util.Scanner;

public class PlaylistSubmenu {
    public static void showSubMenu(ObjectId playlistId) {
        while (true) {
            System.out.println("Playlist Options:");
            System.out.println("1. Add song");
            System.out.println("2. Play this playlist");
            System.out.println("3. Return to playlist menu");
            System.out.print("Enter your choice: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addSongToPlaylist(playlistId);
                    break;
                case 2:
                    // Implement playing the playlist
                    // Example: playPlaylist(selectedPlaylistId);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
                    break;
            }
        }
    }
    private static void addSongToPlaylist(ObjectId playlistId) {
        try {
            System.out.println("===== Add song =====");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input link youtube: ");
            String link = scanner.nextLine();
            Song s = YouTubeUtil.getDataYoutube(link);
            boolean check = PlaylistService.addSongToPlaylist(playlistId, s);
            if (!check) {
                System.out.println("Add to playlist failed");
            } else {
                System.out.println("Add to playlist succeed");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}