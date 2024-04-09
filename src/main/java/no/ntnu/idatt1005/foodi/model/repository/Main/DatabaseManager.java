package no.ntnu.idatt1005.foodi.model.repository.Main;

import java.util.Scanner;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:h2:~/main";
    private static final String USER = "main";
    private static final String PASS = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Please enter a command:");
            System.out.println("1: Populate the database");
            System.out.println("2: Remove all data from the database");
            System.out.println("3: Stop the application");

            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    populateDatabase();
                    break;
                case "2":
                    removeAllData();
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid command. Please enter a number between 1 and 3.");
                    break;
            }
        }

        scanner.close();
    }

    private static void populateDatabase() {
        // Your code for populating the database goes here
        System.out.println("Database populated successfully.");


    }

    private static void removeAllData() {
        // Your code for removing all data from the database goes here
        System.out.println("All data removed from the database.");


    }
}