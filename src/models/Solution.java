package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Solution {

    private int id;
    private Date created;
    private Date updated;
    private String description;
    private int exercise_id;
    private int user_id;

    public Solution() {}

    public Solution(Date created, Date updated, String description) {
        this.created = created;
        this.updated = updated;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    private Solution setId(int id) {
        this.id = id;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public Solution setCreated(Date created) {
        this.created = created;
        return this;
    }

    public Date getUpdated() {
        return updated;
    }

    public Solution setUpdated(Date updated) {
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

    private Solution setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
        return this;
    }

    public int getUser_id() {
        return user_id;
    }

    private Solution setUser_id(int user_id) {
        this.user_id = user_id;
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
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise_id = resultSet.getInt("exercise_id");
            loadedSolution.user_id = resultSet.getInt("user_id");
            return loadedSolution;
        }
        return null;
    }

    static public Solution[] loadAll(Connection conn) throws SQLException {

        ArrayList<Solution> solutionsList = new ArrayList<>();

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Solution");

        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise_id = resultSet.getInt("exercise_id");
            loadedSolution.user_id = resultSet.getInt("user_id");

            solutionsList.add(loadedSolution);
        }

        Solution[] solutionsArray = new Solution[solutionsList.size()];
        solutionsArray = solutionsList.toArray(solutionsArray);

        return solutionsArray;

    }

    public Solution saveToDB(Connection conn) throws SQLException, NullPointerException {
        if ( this.getId() == 0 ) {
            String sql = "INSERT INTO Solution (created, updated, description) VALUES (?, ?, ?)";
            String[] generatedColumns = {"ID"};

            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql, generatedColumns);
            Object param1 = new Timestamp(this.created.getTime());
            preparedStatement.setObject(1, param1);
            Object param2 = new Timestamp(this.updated.getTime());
            preparedStatement.setObject(2, param2);
            preparedStatement.setString(3, this.description);

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.setId(rs.getInt(1));
            }
        } else {
            String sql = "UPDATE Solution SET created = ?, updated = ?, description = ? WHERE id = ?";

            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            Object param1 = new Timestamp(this.created.getTime());
            preparedStatement.setObject(1, param1);
            Object param2 = new Timestamp(this.updated.getTime());
            preparedStatement.setObject(2, param2);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.id);

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
//        sb.append(this.getCreated()).append(" ").append(this.getUpdated());
//        sb.append(this.getCreated()).append(" ").append(this.getUpdated()).append(" ").append(this.getDescription());
//        sb.append(this.getId()).append(" ").append(this.getCreated()).append(" ").append(this.getUpdated()).append(" ").append(this.getDescription());
        sb.append(this.getId()).append(" ")
                .append(this.getCreated()).append(" ")
                .append(this.getUpdated()).append(" ")
                .append(this.getDescription()).append(" ")
                .append(this.getExercise_id()).append(" ")
                .append(this.getUser_id());
        return sb.toString();
    }

}
