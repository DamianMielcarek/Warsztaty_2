import models.Exercise;
import models.Solution;
import models.User;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztaty_2?useSSL=false", "root", "password")) {

            System.out.println("Users:");
            System.out.println("id username email password user_group_id");
            User[] users = User.loadAll(conn);
            for (User u : users) {
                System.out.println( u.toString() );
            }

            System.out.println("\nDownloading all solutions of a given user:");
            System.out.println("id created updated description exercise_id users_id");
            Solution[] solutions1 = Exercise.loadAllByUserId(conn, 1);
            for (Solution s : solutions1) {
                System.out.println(s.toString());
            }

            System.out.println("\nDownloading all solutions of a given exercise sorted from the latest to the oldest:");
            System.out.println("id created updated description exercise_id users_id");
            Solution[] solutions2 = Solution.loadAllByExerciseId(conn, 1);
            for (Solution s : solutions2) {
                System.out.println(s.toString());
            }

            System.out.println("\nDownloading all members of a given group:");
            System.out.println("id username email password user_group_id");
            User[] users1 = User.loadAllByGroupId(conn, 1);
            for (User u : users1) {
                System.out.println(u.toString());
            }

            System.out.println("\nLoading user:");
            System.out.println("id username email password user_group_id");
            User user = User.loadUserById(conn, 2);
            System.out.println(user);
            System.out.println();

            /*
            // edit single user
            System.out.println("Editing user");
            try {
                user.setUsername("test56").setEmail("mail56@mail.pl").setPassword("123456");
                user.saveToDB(conn);
                System.out.println("User edited");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.err.println("Editing failed. No user with such id in database");
            }
            */

            /*
            // delete single user
            System.out.println("Deleting user");
            try {
                user.delete(conn);
                System.out.println("User deleted");
//                System.out.println("Deleted user id = " + user.getId());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.err.println("Deleting failed. No user with such id in database");
            }
            */

            // adding users to database // test 1
//            User user1 = new User("test1", "12345", "mail1@mail.pl");
//            user1.saveToDB(conn);
//            User user2 = new User("test2", "12345", "mail2@mail.pl");
//            user2.saveToDB(conn);
//            User user3 = new User("test3", "12345", "mail3@mail.pl");
//            user3.saveToDB(conn);
//            User user4 = new User("test4", "12345", "mail4@mail.pl");
//            user4.saveToDB(conn);
//            User user5 = new User("test5", "12345", "mail5@mail.pl");
//            user5.saveToDB(conn);

            // more for testing
            /*
            // test 2
            User user1 = new User("test6", "12345", "mail6@mail.pl");
            user1.saveToDB(conn);
            User user2 = new User("test7", "12345", "mail7@mail.pl");
            user2.saveToDB(conn);
            User user3 = new User("test8", "12345", "mail8@mail.pl");
            user3.saveToDB(conn);
            User user4 = new User("test9", "12345", "mail9@mail.pl");
            user4.saveToDB(conn);
            User user5 = new User("test10", "12345", "mail10@mail.pl");
            user5.saveToDB(conn);
            */

            /*
            // test 3
            User user1 = new User("test11", "12345", "mail11@mail.pl");
            user1.saveToDB(conn);
            User user2 = new User("test12", "12345", "mail12@mail.pl");
            user2.saveToDB(conn);
            User user3 = new User("test13", "12345", "mail13@mail.pl");
            user3.saveToDB(conn);
            */

//            System.out.println(user1.toString());
//            System.out.println(user2.toString());
//            System.out.println(user3.toString());
//            System.out.println(user4.toString());
//            System.out.println(user5.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
