package main;


import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import util.GameUtil;

public class GameOver {
    //attributes
    private GameBarrierLayer gameBarrierLayer;
    private Scores scores;
    private int gameScore;
    private final BufferedImage gameOverText;
    private final BufferedImage restartGame;
    private final BufferedImage gameScoreContainer;

    private Button viewTop10Scores;
    //constructor
    public GameOver(GameFrame gameFrame) {
        scores = new Scores(gameFrame.getCurrentUser());
        gameBarrierLayer = gameFrame.getGameBarrierLayer();
        gameScore = gameBarrierLayer.getGameScore();
   

        System.out.println("gameScore" + gameScore);
        saveScore(gameScore);
        
        //load images
        gameOverText = GameUtil.loadBufferedImage("./img/game_over.png");
        gameScoreContainer = GameUtil.loadBufferedImage("./img/scores_container.png");
        restartGame = GameUtil.loadBufferedImage("./img/continue.png");

       //create view top10 scores button and add buton listener
       viewTop10Scores = new Button("View Top10 Scores");
       viewTop10Scores.setBounds(220, 230, 130, 30);
       viewTop10Scores.setVisible(true);
       viewTop10Scores.setFocusable(false);
       viewTop10Scores.addActionListener(e -> {
           JOptionPane.showMessageDialog(null, scores.displayScores());
           System.out.println(scores.displayScores());
       });
       gameFrame.add(viewTop10Scores);   
    }

    //draw the game over screen
    public void draw(Graphics g) {
        
        g.drawImage(gameScoreContainer, 92, 80, null);
        g.drawImage(gameOverText, 220, 90, null);
        // g.drawImage(restartGame, 160, 300, null);

        g.setColor(new Color(72,134,49 ));
        g.setFont(new Font("Arial", 1, 22));
        
        g.drawString("" + gameBarrierLayer.getGameScore() + "s", 355, 162);
        g.drawString(scores.displayHighestScore() + "s", 310, 200);
        
         
    }

    //save the score
    public void saveScore(int gameScore) {
        scores.updateScores(gameScore);
    }

    public void viewButtonInvisible() {
        viewTop10Scores.setVisible(false);
    }

}
