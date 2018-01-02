import models.Exercise;
import models.Solution;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class UserProgram {

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztaty_2?useSSL=false", "root", "password")) {
            Scanner scanner = new Scanner(System.in);
            boolean run = true;
            int users_id = Integer.parseInt(args[0]);
            while (run) {
                System.out.println("Choose one option:");
                System.out.println("  add     - adding solution");
                System.out.println("  view    - browsing my solutions");
                System.out.println("  quit    - end program");

                String choose = scanner.nextLine();
                if (choose.equals("quit")) { // in case of quit - program will stop working
                    run = false;
                } else if (choose.equals("add")) {
                    try {
                        Exercise exercise = new Exercise();
                        System.out.println("List of exercises for which You don't added solutions:");
                        System.out.println("id title description");
                        Exercise[] exercises = exercise.loadUnsolvedByUserId(conn, users_id); // shows a list of exercises to which the user has not yet added the solution
                        for (Exercise e : exercises) {
                            System.out.println(e.toString());
                        }
                        System.out.println("Type id for exercise for which You want to add solution:");
                        while ( !scanner.hasNextInt() ) {
                            System.err.println("id must be a number. Please type a number:");
                            scanner.next();
                        }
                        int exercise_id = scanner.nextInt(); // asks about the exercise id to which the solution is to be added
                        scanner.nextLine();

                        System.out.println("Please type Your solution below. Note that Your solution cannot be deleted or edit, so be sure that it is correct. If You want not to enter solution now, type: quit, to stop the program:");
                        String description = scanner.nextLine(); // user is being asked for solution for the exercise
                        if (description.equals("quit")) { // in case of quit - program will stop working
                            run = false;
                        } else {
                            Solution solution = new Solution(exercise_id, users_id);
                            solution.setDescription(description);
                            solution.saveSolutionToDB(conn);

                        }
                    } catch (SQLException e) {
                        System.err.println("Something went wrong. Adding solution failed");
                    } catch (NumberFormatException e) {
                        System.err.println("Adding failed");
                    }
                } else if (choose.equals("view")) {
                    try {
                        Exercise exercise = new Exercise();
                        System.out.println("Your solutions:");
                        System.out.println("id created updated description exercise_id users_id");
                        Solution[] solutions = exercise.loadAllByUserId(conn, users_id);
                        for (Solution s : solutions) {
                            System.out.println(s.toString());
                        }
                    } catch (SQLException e) {
                        System.err.println("Something went wrong. Showing solution failed");
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Database connection failed. Possible cause: wrong database name, wrong user or password to database or wrong port number. May be also: mysql not installed, no jdbc library in project (mysql-connector-java-5.1.45-bin or above needed) or another unexpected cause.");
        }

    }

}
