import models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class UsersManagement {

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztaty_2?useSSL=false", "root", "password")) {

            boolean run = true;
            while (run) {
                System.out.println("Users:");
                System.out.println("id username email password user_group_id");
                User[] users = User.loadAll(conn);
                for (User u : users) {
                    System.out.println( u.toString() );
                }
                System.out.println("Choose one option:");
                System.out.println("  add     - adding user");
                System.out.println("  edit    - editing user");
                System.out.println("  delete  - deleting user");
                System.out.println("  quit    - end program");

                Scanner scanner = new Scanner(System.in);
                String choose = scanner.nextLine();
                if (choose.equals("quit")) {
                    run = false;
                } else if (choose.equals("add")) {
                    System.out.println("Type username:");
                    String username = scanner.nextLine();
                    System.out.println("Type email:");
                    String email = scanner.nextLine();
                    System.out.println("Type password:");
                    String password = scanner.nextLine();
                    System.out.println("Type user_group_id");
                    while ( !scanner.hasNextInt() ) {
                        System.err.println("user_group_id must be a number. Please type a number:");
                        scanner.next();
                    }
                    int user_group_id = scanner.nextInt();
                    scanner.nextLine();

                    User user = new User(username, email, password, user_group_id);
                    try {
                        user.saveToDB(conn);
                        System.out.println("User added to database");
                    } catch (SQLException e) {
                        System.err.println("Something went wrong. Adding user failed");
                    } catch (NullPointerException e) {
                        System.err.println("Adding failed");
                    }
                } else if (choose.equals("edit")) {
                    System.out.println("Type id for user You want to edit:");
                    while ( !scanner.hasNextInt() ) {
                        System.err.println("id must be a number. Please type a number:");
                        scanner.next();
                    }
                    int id = scanner.nextInt();
                    scanner.nextLine();  // Consume newline left-over
                    System.out.println("Type username:");
                    String username = scanner.nextLine();
                    System.out.println("Type email:");
                    String email = scanner.nextLine();
                    System.out.println("Type password:");
                    String password = scanner.nextLine();
                    System.out.println("Type user_group_id");
                    while ( !scanner.hasNextInt() ) {
                        System.err.println("user_group_id must be a number. Please type a number:");
                        scanner.next();
                    }
                    int user_group_id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Editing user");
                    try {
                        User user = User.loadUserById(conn, id);
                        user.setUsername(username).setEmail(email).setPassword(password).setUser_group_id(user_group_id);
                        user.saveToDB(conn);
                        System.out.println("User edited");
                    } catch (SQLException e) {
                        System.err.println("Something went wrong. Editing user failed");
                    } catch (NullPointerException e) {
                        System.err.println("Editing failed. No user with such id in database");
                    }
                } else if (choose.equals("delete")) {
                    System.out.println("Type id for user You want to delete:");
                    while ( !scanner.hasNextInt() ) {
                        System.err.println("id must be a number. Please type a number:");
                        scanner.next();
                    }
                    int id = scanner.nextInt();
                    User user = User.loadUserById(conn, id);

                    System.out.println("Deleting user");
                    try {
                        user.delete(conn);
                        System.out.println("User deleted");
                    } catch (SQLException e) {
                        System.err.println("Something went wrong. Deleting user failed");
                    } catch (NullPointerException e) {
                        System.err.println("Deleting failed. No user with such id in database");
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Connecting to database failed. Possible cause: wrong database name, wrong user or password to database or wrong port number. May be also: mysql not installed, no jdbc library in project (mysql-connector-java-5.1.45-bin or above needed)");
        }

    }

}
