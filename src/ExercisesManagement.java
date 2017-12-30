import models.Exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ExercisesManagement {

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztaty_2?useSSL=false", "root", "password")) {

            boolean run = true;
            while (run) {
                System.out.println("Exercises:");
                System.out.println("id title description");
                Exercise[] exercises = Exercise.loadAll(conn);
                for (Exercise u : exercises) {
                    System.out.println( u.toString() );
                }
                System.out.println("Choose one option:");
                System.out.println("  add     - adding exercise");
                System.out.println("  edit    - editing exercise");
                System.out.println("  delete  - deleting exercise");
                System.out.println("  quit    - end program");

                Scanner scanner = new Scanner(System.in);
                String choose = scanner.nextLine();
                if (choose.equals("quit")) {
                    run = false;
                } else if (choose.equals("add")) {
                    System.out.println("Type title:");
                    String title = scanner.nextLine();
                    System.out.println("Type description:");
                    String description = scanner.nextLine();

                    Exercise exercise = new Exercise(title, description);
                    try {
                        exercise.saveToDB(conn);
                        System.out.println("Exercise added to database");
                    } catch (SQLException e) {
                        System.err.println("Something went wrong. Adding exercise failed");
                    } catch (NullPointerException e) {
                        System.err.println("Adding failed");
                    }
                } else if (choose.equals("edit")) {
                    System.out.println("Type id for exercise You want to edit:");
                    while ( !scanner.hasNextInt() ) {
                        System.err.println("id must be a number. Please type a number:");
                        scanner.next();
                    }
                    int id = scanner.nextInt();
                    scanner.nextLine();  // Consume newline left-over
                    System.out.println("Type title:");
                    String title = scanner.nextLine();
                    System.out.println("Type description:");
                    String description = scanner.nextLine();

                    System.out.println("Editing exercise");
                    try {
                        Exercise exercise = Exercise.loadExerciseById(conn, id);
                        exercise.setTitle(title).setDescription(description);
                        exercise.saveToDB(conn);
                        System.out.println("Exercise edited");
                    } catch (SQLException e) {
                        System.err.println("Something went wrong. Editing exercise failed");
                    } catch (NullPointerException e) {
                        System.err.println("Editing failed. No exercise with such id in database");
                    }
                } else if (choose.equals("delete")) {
                    System.out.println("Type id for exercise You want to delete:");
                    while ( !scanner.hasNextInt() ) {
                        System.err.println("id must be a number. Please type a number:");
                        scanner.next();
                    }
                    int id = scanner.nextInt();
                    Exercise exercise = Exercise.loadExerciseById(conn, id);

                    System.out.println("Deleting exercise");
                    try {
                        exercise.delete(conn);
                        System.out.println("Exercise deleted");
                    } catch (SQLException e) {
                        System.err.println("Something went wrong. Deleting exercise failed");
                    } catch (NullPointerException e) {
                        System.err.println("Deleting failed. No exercise with such id in database");
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Connecting to database failed. Possible cause: wrong database name, wrong user or password to database or wrong port number. May be also: mysql not installed, no jdbc library in project (mysql-connector-java-5.1.45-bin or above needed)");
        }

    }

}
