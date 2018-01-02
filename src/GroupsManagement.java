import models.Group;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class GroupsManagement {

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztaty_2?useSSL=false", "root", "password")) {
            Scanner scanner = new Scanner(System.in);
            boolean run = true;
            while (run) {
                System.out.println("Groups:");
                System.out.println("id name");
                Group[] groups = Group.loadAll(conn);
                for (Group g : groups) {
                    System.out.println( g.toString() );
                }
                System.out.println("Choose one option:");
                System.out.println("  add     - adding group");
                System.out.println("  edit    - editing group");
                System.out.println("  delete  - deleting group");
                System.out.println("  quit    - end program");

                String choose = scanner.nextLine();
                if (choose.equals("quit")) {
                    run = false;
                } else if (choose.equals("add")) {
                    System.out.println("Type name:");
                    String name = scanner.nextLine();

                    Group group = new Group(name);
                    try {
                        group.saveToDB(conn);
                        System.out.println("Group added to database");
                    } catch (SQLException e) {
                        System.err.println("Something went wrong. Adding group failed");
                    } catch (NullPointerException e) {
                        System.err.println("Adding failed");
                    }
                } else if (choose.equals("edit")) {
                    System.out.println("Type id for group You want to edit:");
                    while ( !scanner.hasNextInt() ) {
                        System.err.println("id must be a number. Please type a number:");
                        scanner.next();
                    }
                    int id = scanner.nextInt();
                    scanner.nextLine();  // Consume newline left-over
                    System.out.println("Type name:");
                    String name = scanner.nextLine();

                    System.out.println("Editing group");
                    try {
                        Group group = Group.loadGroupById(conn, id);
                        group.setName(name);
                        group.saveToDB(conn);
                        System.out.println("Group edited");
                    } catch (SQLException e) {
                        System.err.println("Something went wrong. Editing group failed");
                    } catch (NullPointerException e) {
                        System.err.println("Editing failed. No group with such id in database");
                    }
                } else if (choose.equals("delete")) {
                    System.out.println("Type id for group You want to delete:");
                    while ( !scanner.hasNextInt() ) {
                        System.err.println("id must be a number. Please type a number:");
                        scanner.next();
                    }
                    int id = scanner.nextInt();
                    Group group = Group.loadGroupById(conn, id);

                    System.out.println("Deleting group");
                    try {
                        group.delete(conn);
                        System.out.println("Group deleted");
                    } catch (SQLException e) {
                        System.err.println("Something went wrong. Deleting group failed");
                    } catch (NullPointerException e) {
                        System.err.println("Deleting failed. No group with such id in database");
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Database connection failed. Possible cause: wrong database name, wrong user or password to database or wrong port number. May be also: mysql not installed, no jdbc library in project (mysql-connector-java-5.1.45-bin or above needed) or another unexpected cause.");
        }

    }

}
