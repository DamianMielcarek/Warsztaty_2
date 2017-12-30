package models;

import java.sql.*;
import java.util.ArrayList;

public class Exercise {

    private int id;
    private String title;
    private String description;

    public Exercise() {}

    public Exercise(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    private Exercise setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Exercise setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Exercise setDescription(String description) {
        this.description = description;
        return this;
    }

    static public Exercise loadExerciseById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM Exercise WHERE id = ?";
        PreparedStatement preparedStatement;
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Exercise loadedExercise = new Exercise();
            loadedExercise.id = resultSet.getInt("id");
            loadedExercise.title = resultSet.getString("title");
            loadedExercise.description = resultSet.getString("description");
            return loadedExercise;
        }
        return null;
    }

    static public Solution[] loadAllByUserId(Connection conn, int users_id) throws SQLException {

        ArrayList<Solution> solutionsList = new ArrayList<>();
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Solution WHERE users_id = ?;");
        preparedStatement.setInt(1, users_id);
        ResultSet solution = preparedStatement.executeQuery();

        while (solution.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.setId(solution.getInt("id"));
            loadedSolution.setCreated(solution.getDate("created"));
            loadedSolution.setUpdated(solution.getDate("updated"));
            loadedSolution.setDescription(solution.getString("description"));
            loadedSolution.setExercise_id(solution.getInt("exercise_id"));
            loadedSolution.setUser_id(users_id);

            solutionsList.add(loadedSolution);
        }

        Solution[] solutionsArray = new Solution[solutionsList.size()];
        solutionsArray = solutionsList.toArray(solutionsArray);

        return solutionsArray;

    }

    static public Exercise[] loadAll(Connection conn) throws SQLException {

        ArrayList<Exercise> exercisesList = new ArrayList<>();

        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Exercise");

        while (resultSet.next()) {
            Exercise loadedExercise = new Exercise();
            loadedExercise.id = resultSet.getInt("id");
            loadedExercise.title = resultSet.getString("title");
            loadedExercise.description = resultSet.getString("description");

            exercisesList.add(loadedExercise);
        }

        Exercise[] exercisesArray = new Exercise[exercisesList.size()];
        exercisesArray = exercisesList.toArray(exercisesArray);

        return exercisesArray;

    }

    public Exercise saveToDB(Connection conn) throws SQLException, NullPointerException {
        if ( this.getId() == 0 ) {
            String sql = "INSERT INTO Exercise (title, description) VALUES (?, ?)";
            String[] generatedColumns = {"ID"};

            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.title);
            preparedStatement.setString(2, this.description);

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.setId(rs.getInt(1));
            }
        } else {
            String sql = "UPDATE Exercise SET title = ?, description = ? WHERE id = ?";

            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, this.title);
            preparedStatement.setString(2, this.description);
            preparedStatement.setInt(3, this.id);

            preparedStatement.executeUpdate();
        }
        return this;
    }

    public void delete(Connection conn) throws SQLException, NullPointerException {
        if (this.id != 0) {
            String sql = "DELETE FROM Exercise WHERE id = ?";
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
                .append(this.getTitle()).append(" ")
                .append(this.getDescription());
        return sb.toString();
    }

}
