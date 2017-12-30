import models.Exercise;
import models.Solution;
import models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class AssigningTasks {

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztaty_2?useSSL=false", "root", "password")) {
            Scanner scanner = new Scanner(System.in);
            boolean run = true;
            while (run) {
                System.out.println("Choose one option:");
                System.out.println("  add     - assigning task to user");
                System.out.println("  view    - browsing the solutions of a given user");
                System.out.println("  quit    - end program");

                String choose = scanner.nextLine();
                if (choose.equals("quit")) {
                    run = false;
                } else if (choose.equals("add")) {
                    try {
                        System.out.println("Users:");
                        System.out.println("id username email password user_group_id");
                        User[] users = User.loadAll(conn); // shows list of all users
                        for (User u : users) {
                            System.out.println( u.toString() );
                        }
                        System.out.println("Type users_id:"); // asks for id
                        while ( !scanner.hasNextInt() ) {
                            System.err.println("users_id must be a number. Please type a number:");
                            scanner.next();
                        }
                        int users_id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Exercises:");
                        System.out.println("id title description");
                        Exercise[] exercises = Exercise.loadAll(conn); // shows list of all exercises
                        for (Exercise e : exercises) {
                            System.out.println( e.toString() );
                        }
                        System.out.println("Type exercise_id:"); // asks for id
                        while ( !scanner.hasNextInt() ) {
                            System.err.println("exercise_id must be a number. Please type a number:");
                            scanner.next();
                        }
                        int exercise_id = scanner.nextInt();
                        scanner.nextLine();
                        Solution solution = new Solution(exercise_id, users_id); // creates a Solution type object
                        solution.saveToDB(conn); // saves Solution type object
                        System.out.println("Solution added to database");
                    } catch (SQLException e) {
                        System.err.println("Something went wrong. Adding solution failed");
                    } catch (NullPointerException e) {
                        System.err.println("Adding failed");
                    }
                } else if (choose.equals("view")) {
                    try {
                        System.out.println("Type id for user for whom You want to view the solutions:");
                        while ( !scanner.hasNextInt() ) {
                            System.err.println("id must be a number. Please type a number:");
                            scanner.next();
                        }
                        int id = scanner.nextInt();
                        scanner.nextLine();  // consume newline left-over
                        System.out.println("Exercise solutions for entered user");
                        System.out.println("Solutions:");
                        System.out.println("id created updated description exercise_id users_id");
                        Solution[] solutions = Exercise.loadAllByUserId(conn, id); // shows exercises solution list for typed user
                        for (Solution s : solutions) {
                            System.out.println(s.toString());
                        }
                    }
                    catch (SQLException e) {
                        System.err.println("Something went wrong. Solutions can't be showed");
                    }
                    catch (NullPointerException e) {
                        System.err.println("Showing solutions failed");
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Database connection failed. Possible cause: wrong database name, wrong user or password to database or wrong port number. May be also: mysql not installed, no jdbc library in project (mysql-connector-java-5.1.45-bin or above needed) or another unexpected cause");
        }

    }

}
