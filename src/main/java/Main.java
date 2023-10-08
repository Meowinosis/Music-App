import com.mongodb.client.MongoClient;
import database.MongoDBConnect;
import service.PlaylistService;
import service.UserService;
import view.LoginMenu;
import view.PlaylistMenu;

public class Main {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoDBConnect.createMongoClient();
        UserService userService = new UserService(mongoClient);
        PlaylistService playlistService= new PlaylistService(mongoClient);
        LoginMenu loginMenu = new LoginMenu(userService);
        PlaylistMenu playlistMenu = new PlaylistMenu(userService,playlistService);
        while (!loginMenu.isLoggedIn()) {
            loginMenu.showMenu();
        }
        playlistMenu.showMenu();
    }
}
