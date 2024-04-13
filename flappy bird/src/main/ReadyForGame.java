package main;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.*;

import javafx.scene.paint.Color;
import util.GameUtil;
import util.Constant;

public class ReadyForGame {
    //attributes
    private GameBackground gameBackground;
    private GameBarrierLayer gameBarrierLayer;
    private GameUtil soundPlayer;
    private Button menuButton;
    private Button dayModeButton;
    private Button nightModeButton;
    private Button easyModeButton;
    private Button hardModeButton;
    private Button noSoundButton;
    private boolean menuOpen = false;
    private final BufferedImage startTitle;
    private final BufferedImage startText;


    //constructor
    public ReadyForGame(GameFrame gameFrame) {
        gameBackground = gameFrame.getGameBackground();
        gameBarrierLayer = gameFrame.getGameBarrierLayer();
        // soundPlayer = gameFrame.getSoundPlayer();
        soundPlayer = gameFrame.getSoundPlayer();   

        //load images
        startTitle = GameUtil.loadBufferedImage("./img/flappy_bird.png");
        startText = GameUtil.loadBufferedImage("./img/space_start.png");

        //create buttons and add button listeners
        menuButton = new Button("Menu");
        menuButton.setBounds(10, 10, 60, 20);
        menuButton.setFocusable(false);
        dayModeButton = new Button("Day");
        dayModeButton.setBounds(10, 40, 60, 20);
        dayModeButton.setFocusable(false);
        nightModeButton = new Button("Night");
        nightModeButton.setBounds(10, 70, 60, 20);
        nightModeButton.setFocusable(false);
        easyModeButton = new Button("Easy");
        easyModeButton.setBounds(10, 100, 60, 20);
        easyModeButton.setFocusable(false);
        hardModeButton = new Button("Hard");
        hardModeButton.setBounds(10, 130, 60, 20);
        hardModeButton.setFocusable(false);
        noSoundButton = new Button("NoSound");
        noSoundButton.setBounds(10, 160, 60, 20);
        noSoundButton.setFocusable(false);         
        
        

        gameFrame.add(menuButton);
        //add menu button listeners
        menuButton.addActionListener(e -> {
            menuButton.setBackground(Constant.BK_Day_COLOR);
            if (menuOpen) {
                gameFrame.remove(dayModeButton);
                gameFrame.remove(nightModeButton);
                gameFrame.remove(easyModeButton);
                gameFrame.remove(hardModeButton);
                gameFrame.remove(noSoundButton);
                menuOpen = false;
            } else {
                menuOpen = true;
                gameFrame.add(dayModeButton);
                gameFrame.add(nightModeButton);
                gameFrame.add(easyModeButton);
                gameFrame.add(hardModeButton);
                gameFrame.add(noSoundButton);
            }
        });

        //add day mode button listeners
        dayModeButton.addActionListener(e -> {
            gameBackground.setBkColor(Constant.BK_Day_COLOR);
            dayModeButton.setBackground(Constant.BK_Day_COLOR);
        });

        //add night mode button listeners
        nightModeButton.addActionListener(e -> {
            gameBackground.setBkColor(Constant.BK_Night_COLOR);
            nightModeButton.setBackground(Constant.BK_Day_COLOR);
        });

        //add easy mode button listeners
        easyModeButton.addActionListener(e -> {
            gameBarrierLayer.setDifficultMode(false);
            easyModeButton.setBackground(Constant.BK_Day_COLOR);
        });

        //add hard mode button listeners
        hardModeButton.addActionListener(e -> {
            gameBarrierLayer.setDifficultMode(true);
            hardModeButton.setBackground(Constant.BK_Day_COLOR);
        });

        //add sound button listeners
        noSoundButton.addActionListener(e -> {
            soundPlayer.setSound(false);
           noSoundButton.setBackground(Constant.BK_Day_COLOR);
        });

    }

    //draw the game ready screen
    public void drawGameReady(Graphics g) {
        g.drawImage(startTitle, 150, 120, null);
        g.drawImage(startText, 150, 245, null);

    }

    //hide all menu buttons
    public void hideAllButtons() {
        menuButton.setVisible(false);
        dayModeButton.setVisible(false);
        nightModeButton.setVisible(false);
        easyModeButton.setVisible(false);
        hardModeButton.setVisible(false);
        noSoundButton.setVisible(false);
    }

}
