package models;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Solution {

    private int id;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime updated;
    private String description;
    private int exercise_id;
    private int users_id;

    public Solution() {}

    public Solution(int exercise_id, int users_id) {
        this.exercise_id = exercise_id;
        this.users_id = users_id;
    }

    public int getId() {
        return id;
    }

    public Solution setId(int id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Solution setCreated(String str) {
        String[] split = str.split("\\.");
        String dateTime = split[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime created = LocalDateTime.parse(dateTime, formatter);
        this.created = created;
        return this;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public Solution setUpdated(String str) {
        String[] split = str.split("\\.");
        String dateTime = split[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime updated = LocalDateTime.parse(dateTime, formatter);
        this.updated = updated;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Solution setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public Solution setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
        return this;
    }

    public int getUsers_id() {
        return users_id;
    }

    public Solution setUsers_id(int user_id) {
        this.users_id = user_id;
        return this;
    }

    static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM Solution WHERE id = ?";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = (LocalDateTime) resultSet.getObject("created");
            loadedSolution.updated = (LocalDateTime) resultSet.getObject("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise_id = resultSet.getInt("exercise_id");
            loadedSolution.users_id = resultSet.getInt("users_id");
            return loadedSolution;
        }
        return null;
    }

    static public Solution[] loadAllByExerciseId(Connection conn, int exercise_id) throws SQLException {

        ArrayList<Solution> solutionsList = new ArrayList<>();
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Solution WHERE exercise_id = ? ORDER BY updated DESC;");
        preparedStatement.setInt(1, exercise_id);
        ResultSet solution = preparedStatement.executeQuery();

        while (solution.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = solution.getInt("id");
            loadedSolution.created = (LocalDateTime) solution.getObject("created");
            loadedSolution.updated = (LocalDateTime) solution.getObject("updated");
            loadedSolution.description = solution.getString("description");
            loadedSolution.exercise_id = exercise_id;
            loadedSolution.users_id = solution.getInt("users_id");

            solutionsList.add(loadedSolution);
        }

        Solution[] solutionsArray = new Solution[solutionsList.size()];
        solutionsArray = solutionsList.toArray(solutionsArray);

        return solutionsArray;

    }

    static public Solution[] loadAll(Connection conn) throws SQLException {

        ArrayList<Solution> solutionsList = new ArrayList<>();

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Solution");

        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = (LocalDateTime) resultSet.getObject("created");
            loadedSolution.updated = (LocalDateTime) resultSet.getObject("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise_id = resultSet.getInt("exercise_id");
            loadedSolution.users_id = resultSet.getInt("users_id");

            solutionsList.add(loadedSolution);
        }

        Solution[] solutionsArray = new Solution[solutionsList.size()];
        solutionsArray = solutionsList.toArray(solutionsArray);

        return solutionsArray;

    }

    public Solution saveToDB(Connection conn) throws SQLException, NullPointerException {
        if ( this.getId() == 0 ) {
            String sql = "INSERT INTO Solution (created, exercise_id, users_id) VALUES (?, ?, ?)";
            String[] generatedColumns = {"ID"};

            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, String.valueOf(this.created));
            preparedStatement.setInt(2, this.exercise_id);
            preparedStatement.setInt(3, this.users_id);

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.setId(rs.getInt(1));
            }
        } else {
            String sql = "UPDATE Solution SET created = ?, updated = ?, description = ?, exercise_id = ?, users_id = ? WHERE id = ?";

            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(this.created));
            preparedStatement.setString(2, String.valueOf(this.updated));
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.exercise_id);
            preparedStatement.setInt(5, this.users_id);
            preparedStatement.setInt(6, this.id);

            preparedStatement.executeUpdate();
        }

        return this;
    }

    public void delete(Connection conn) throws SQLException, NullPointerException {
        if (this.id != 0) {
            String sql = "DELETE FROM Solution WHERE id = ?";
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
        sb.append(this.getId()).append(" ")
                .append(this.getCreated()).append(" ")
                .append(this.getUpdated()).append(" ")
                .append(this.getDescription()).append(" ")
                .append(this.getExercise_id()).append(" ")
                .append(this.getUsers_id());
        return sb.toString();
    }

}
