package main;

import javax.swing.*;
import java.sql.*;
import java.util.Arrays;

public class Scores {
    // attributes
    private Connection connection;
    private String result;
    private String highestScore;
    private StringBuilder sb;
    private String currentUser;

    //constructor
    public Scores(String currentUser) {
        // set up the database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/applyjobs", "root", "mysql");
            this.currentUser = currentUser;
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // method to display the top 10 scores
    public String displayScores() {
        result = "";
        sb = new StringBuilder();
        try {
            // get the top 10 scores from the database
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT scores FROM user WHERE email_address = ?");
            statement.setString(1, currentUser);
            ResultSet resultSet = statement.executeQuery();

            // diaplay the top 10 scores
            while (resultSet.next()) {
                String scores = resultSet.getString("scores");
                String[] scoreArray = scores.split(",");
                // split the string into an array and sort by score
                int[] scoreInts = new int[scoreArray.length];
                for (int i = 0; i < scoreArray.length; i++) {
                    scoreInts[i] = Integer.parseInt(scoreArray[i]);
                }
                // sort the scores
                Arrays.sort(scoreInts);
                for (int i = scoreInts.length - 1; i >= 1; i--) {
                    sb.append(scoreInts[i] + ",");
                }
                sb.append(scoreInts[0]);
            }
            result = sb.toString();

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // method to display the highest score
    public String displayHighestScore() {
        displayScores();
        highestScore = displayScores().split(",")[0];
        return highestScore;
    }

    // method to update and save the score when the game is over
    public void updateScores(int playerScore) {
        try {
            String scores = "";
            // get the scores from database
            // System.out.println("currentUser: " + currentUser);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT scores FROM user WHERE email_address = ?");
            statement.setString(1, currentUser);
            ResultSet resultSet = statement.executeQuery();

            //check if the scores column is empty
            if (resultSet.next()) {
                scores = resultSet.getString("scores");
                System.out.println("scores: " + scores);
            }
            
            // split the string into an array and sort by score
            String[] scoreArray = scores.split(",");
            int[] scoreInts = new int[scoreArray.length];

            // if the scores column is empty, add the new score
            // if the scores column is not empty and less than 10, add the new score and sort the scores
            // if the scores column is full, sort the scores and update the scores column
            if (scores == "null" || scores.isEmpty()){
                scores = String.valueOf(playerScore);
            }else if (scoreArray.length > 0 && scoreArray.length < 10 ) {
                scores += "," + playerScore;
            }else {
                for (int i = 0; i < scoreArray.length; i++) {
                    if (scoreArray[i] != null && !scoreArray[i].isEmpty()) {
                        try {
                            scoreInts[i] = Integer.parseInt(scoreArray[i]);
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                // sort the scores and update the scores column
                Arrays.sort(scoreInts);
                scores = "";
                if (playerScore > scoreInts[0]) {
                    scores +=  playerScore;
                    for (int i = 1; i < scoreInts.length; i++) {
                        scores +=  "," + scoreInts[i];
                    }
                
                }
            }
            // update the scores column
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE user SET scores = ? WHERE email_address = ?");
            updateStatement.setString(1, scores);
            updateStatement.setString(2, currentUser);
            updateStatement.executeUpdate();
            updateStatement.close();

            resultSet.close();
            statement.close();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public String getHighestScore() {
        return highestScore;
    }

}
