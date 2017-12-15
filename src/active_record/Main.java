package active_record;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/aml_ar?useSSL=false", "root", "pswd")) {

            User[] users = User.loadAll(conn);
            for (User u : users) {
                System.out.println( u.toString() );
            }

//            users[0].setEmail("nowy@mail.com");
//            users[0].saveToDB(conn);

            User user1 = new User("test1", "12345", "mail1@mail.pl");
            user1.saveToDB(conn);
            User user2 = new User("test2", "12345", "mail2@mail.pl");
            user2.saveToDB(conn);
            User user3 = new User("test3", "12345", "mail3@mail.pl");
            user3.saveToDB(conn);
            User user4 = new User("test4", "12345", "mail4@mail.pl");
            user4.saveToDB(conn);
            User user5 = new User("test5", "12345", "mail5@mail.pl");
            user5.saveToDB(conn);


            System.out.println(user1.toString());
            System.out.println(user2.toString());
            System.out.println(user3.toString());
            System.out.println(user4.toString());
            System.out.println(user5.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
