package view;

import model.Playlist;
import org.bson.Document;
import org.bson.types.ObjectId;
import service.PlaylistService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlaylistMenu {
    private UserService userService;
    private PlaylistService playlistService;
    private List<ObjectId> playlistIds;
    static Scanner scanner = new Scanner(System.in);
    private PlaylistSubmenu playlistSubmenu;

    public PlaylistMenu(UserService userService, PlaylistService playlistService) {
        this.userService = userService;
        this.playlistService = playlistService;
        this.playlistIds = new ArrayList<>();
        this.playlistSubmenu = new PlaylistSubmenu();
    }
    private void initializePlaylistIds() {
        // Retrieve the user's playlists and populate playlistIds
        playlistIds.clear(); // Clear the existing list
        List<Document> playlists = playlistService.getAllPlaylists(userService.getUserId());
        for (Document playlist : playlists) {
            ObjectId playlistId = playlist.getObjectId("_id");
            playlistIds.add(playlistId); // Add ObjectId to the list
        }
    }
    public void showMenu() {
        while (true) {
            System.out.println("\n--- Playlist Menu ---");
            System.out.println("1. Create Playlist");
            System.out.println("2. Show All Playlists");
            System.out.println("3. Show songs of a playlist");
            System.out.println("4. Exit");

            int choice = captureUserInput(scanner);

            switch (choice) {
                case 1:
                    createPlaylist();
                    break;
                case 2:
                    showAllPlaylists();
                    break;
                case 3:
                    showSongsOfPlaylist();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private int captureUserInput(Scanner scanner) {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return choice;
    }

    private void createPlaylist() {
        System.out.println("===== Create Playlist =====");
        System.out.print("Input playlist name: ");
        String name = scanner.nextLine();
        Playlist pl = new Playlist();
        pl.setName(name);
        boolean check = userService.createPlaylist(pl);
        if(!check){
            System.out.println("Create playlist failed");
        }
        else {
            System.out.println("Create playlist succeed");
        }
    }

    private void showAllPlaylists() {
        System.out.println("===== Playlist =====");
        List<Document> playlists = playlistService.getAllPlaylists(userService.getUserId());

        if (playlists.isEmpty()) {
            System.out.println("No playlists found.");
        } else {
            System.out.println("List of Playlists:");
            playlistIds.clear();
            for (int i = 0; i < playlists.size(); i++) {
                String playlistName = playlists.get(i).getString("name");
                ObjectId playlistId = playlists.get(i).getObjectId("_id");
                playlistIds.add(playlistId); // Add ObjectId to the list
                System.out.println((i + 1) + ". " + playlistName);
            }
        }
    }

    public void showSongsOfPlaylist(){
        initializePlaylistIds();
        System.out.print("Input playlist for viewing: ");
        int playlistIndex = captureUserInput(scanner);

        if (playlistIndex >= 1 && playlistIndex <= playlistIds.size()) {
            ObjectId selectedPlaylistId = playlistIds.get(playlistIndex - 1); // Adjust for 0-based index

            // Call the PlaylistService to retrieve and display songs in the selected playlist
            List<Document> songs = playlistService.getSongsInPlaylist(selectedPlaylistId);

            if (songs.isEmpty()) {
                System.out.println("No songs found in the selected playlist.");
            } else {
                System.out.println("Songs in Selected Playlist:");
                for (int i = 0; i < songs.size(); i++) {
                    String songTitle = songs.get(i).getString("title");
                    String artist = songs.get(i).getString("artist");
                    System.out.println((i + 1) + ". " + songTitle + " - " + artist);
                }
            }
            PlaylistSubmenu.showSubMenu(selectedPlaylistId);
        } else {
            System.out.println("Invalid playlist index. Please choose a valid playlist.");
        }
    }
}
