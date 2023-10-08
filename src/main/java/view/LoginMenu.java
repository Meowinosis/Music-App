package view;

import model.User;
import service.UserService;

import java.util.Scanner;

public class LoginMenu {
    private boolean loggedIn = false;
    private UserService userService;
    public LoginMenu(UserService userService){
        this.userService = userService;
    }
    static Scanner scanner = new Scanner(System.in);
    public void showMenu() {

        while (!loggedIn) {
            // Display the main menu
            System.out.println("Welcome to Music App!");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            // Capture user input
            int choice = captureUserInput(scanner);

            switch (choice) {
                case 1:
                    // Handle user login
                    System.out.println("Login selected.");
                    showMenuLogin();
                    break;
                case 2:
                    // Handle user registration
                    System.out.println("Register selected.");
                    showMenuRegister();
                    break;
                case 3:
                    // Exit the application
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void showMenuRegister(){
        System.out.println("===== Register =====");
        System.out.print("Input username: ");
        String username = scanner.nextLine();
        System.out.print("Input password: ");
        String password = scanner.nextLine();
        System.out.print("Input name: ");
        String name = scanner.nextLine();
        User u = new User(username,password,name);
        boolean check = userService.registerUser(u);
        if(!check){
            System.out.println("Username existed");
        }
        else {
            System.out.println("Register succeed");
        }
    }
    private void showMenuLogin(){
        boolean check;
        do {
            System.out.println("===== Login =====");
            System.out.print("Input username: ");
            String username = scanner.nextLine();
            System.out.print("Input password: ");
            String password = scanner.nextLine();
            check = userService.loginUser(username, password);
            if (!check) {
                System.out.println("Login fail, please check username and password again");
            } else {
                System.out.println("Login succeed");
                loggedIn = true;
            }
        }while (!check);
    }
    private int captureUserInput(Scanner scanner) {
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return choice;
    }
    public boolean isLoggedIn() {
        return loggedIn;
    }
}
