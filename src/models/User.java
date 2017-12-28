package models;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;

public class User {

    private int id = 0;
    private String username;
    private String email;
    private String password;
    private int user_group_id;

    public User() {}

    public User(String username, String password, String email) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getUser_group_id() {
        return user_group_id;
    }

    private User setId(int id) {
        this.id = id;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = BCrypt.hashpw( password, BCrypt.gensalt() );
        return this;
    }

    public User setUser_group_id(int user_group_id) {
        this.user_group_id = user_group_id;
        return this;
    }

    static public User loadUserById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM Users WHERE id=?";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("id");
            loadedUser.username = resultSet.getString("username");
            loadedUser.email = resultSet.getString("email");
            loadedUser.password = resultSet.getString("password");
            loadedUser.user_group_id = resultSet.getInt("user_group_id");
            return loadedUser;
        }
        return null;
    }

    static public User[] loadAllByGroupId(Connection conn, int user_group_id) throws SQLException {

        ArrayList<User> usersList = new ArrayList<>();
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Users WHERE user_group_id = ?;");
        preparedStatement.setInt(1, user_group_id);
        ResultSet user = preparedStatement.executeQuery();

        while (user.next()) {
            User loadedUser = new User();
            loadedUser.id = user.getInt("id");
            loadedUser.username = user.getString("username");
            loadedUser.email = user.getString("email");
            loadedUser.password = user.getString("password");
            loadedUser.user_group_id = user_group_id;

            usersList.add(loadedUser);
        }

        User[] usersArray = new User[usersList.size()];
        usersArray = usersList.toArray(usersArray);

        return usersArray;

    }

    static public User[] loadAll(Connection conn) throws SQLException {

        ArrayList<User> usersList = new ArrayList<>();

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");

        while ( resultSet.next() ) {
            User loadedUser = new User();
            loadedUser.id = resultSet.getInt("id");
            loadedUser.username = resultSet.getString("username");
            loadedUser.email = resultSet.getString("email");
            loadedUser.password = resultSet.getString("password");
            loadedUser.user_group_id = resultSet.getInt("user_group_id");

            usersList.add(loadedUser);
        }

        User[] usersArray = new User[usersList.size()];
        usersArray = usersList.toArray(usersArray);

        return usersArray;

    }

    public User saveToDB(Connection conn) throws SQLException, NullPointerException {
        if ( this.getId() == 0 ) {
            String sql = "INSERT INTO Users (username, email, password) VALUES (?, ?, ?)";
            String[] generatedColumns = {"ID"};

            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.password);

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.setId(rs.getInt(1));
            }
        } else {
            String sql = "UPDATE Users SET username = ?, email = ?, password = ? WHERE id = ?";

            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.username);
            preparedStatement.setString(2, this.email);
            preparedStatement.setString(3, this.password);
            preparedStatement.setInt(4, this.id);

            preparedStatement.executeUpdate();
        }
        return this;
    }

    public void delete(Connection conn) throws SQLException, NullPointerException {
        if (this.id != 0) {
            String sql = "DELETE FROM Users WHERE id = ?";
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id=0;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
//        sb.append(this.getUsername()).append(" ").append( this.getEmail() );
//        sb.append(this.getId()).append(" ").append(this.getUsername()).append(" ").append( this.getEmail() );
        sb.append(this.getId()).append(" ")
                .append(this.getUsername()).append(" ")
                .append(this.getEmail()).append(" ")
                .append(this.getPassword()).append(" ")
                .append(this.getUser_group_id());
        return sb.toString();
    }

}
