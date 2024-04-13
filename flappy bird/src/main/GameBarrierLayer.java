package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

import util.Constant;


public class GameBarrierLayer {
    //attributes
    private GameTime gameTime;
    GameBackground gameBackground;
    private Random random = new Random();
    private int heightOfTopBarrier;
    private int heightOfBottomBarrier;
    private int randomNumber;
    private int gameScore;
    private int birdHeight;
    private int groundHeight;
    private boolean difficultMode;
    private ArrayList<Barrier> barriers;

    //constructor to initialize the barriers
    public GameBarrierLayer() {
        barriers = new ArrayList<>();       
        gameTime = new GameTime();
        gameBackground = new GameBackground();
        groundHeight = gameBackground.getBkImg().getHeight();

    }

    //draw the barriers
    public void draw(Graphics g, Bird bird) {
        System.out.println("barriers.size():" + barriers.size());
        
        for (int i = 0; i < barriers.size(); i++) {
            System.out.println("barriers.size():" + barriers.size());
            Barrier barrier = barriers.get(i);
            if (barrier.isVisible()) {
                barrier.draw(g);
            } else {
                // return the object to the pool when it is not visible in the frame
                Barrier removeBarrier = barriers.remove(i);
                BarrierPool.setPool(removeBarrier);
                i--;
            }
        }

        hitBird(bird);
        logic(g);

    }

    //control the barriers' movement
    public void logic(Graphics g) {
        //if there is no barrier in the frame, insert a new barrier
        if (barriers.size() == 0) {
            ran();
            gameTime.begin();
            if (difficultMode) {//difficult mode                
                insert(600, 0, 180, 3);
                insert(600, 180+Barrier.randomGap, Constant.FRAM_HEIGHT-180-Barrier.randomGap, 4);
            }else{//easy mode
                // insert(600, 0, heightOfTopBarrier, 0);
                insert(600, 0, heightOfTopBarrier, 0);
                insert(600, 500 - heightOfBottomBarrier-groundHeight, heightOfBottomBarrier, 1);
            }
            
        //if there is a barrier in the frame, check if the last barrier is in the frame
        }else {
            //display the run time game score
            long differ = gameTime.differ();
                g.setFont(new Font("Arial", 1, 20));
                g.setColor(Color.white);
                g.drawString("insisted:" + differ + "s", 260, 50);
            
            //check if the last barrier is in the frame
            Barrier last = barriers.get(barriers.size() - 1);
            if (last.isInFrame()) {//if the last barrier is in the frame, insert a new barrier                
                ran();
                if (difficultMode) {//difficult mode
                    if (randomNumber > 200){
                        insert(600, 0, 180, 3);
                        insert(600, 180+Barrier.randomGap, Constant.FRAM_HEIGHT-180-Barrier.randomGap, 4);
                    }
                }else{//easy mode
                    if (randomNumber < 200){
                        insert(600, 80, 150, 2);
                    }else if (randomNumber > 200){
                    insert(600, 0, heightOfTopBarrier, 0);
                    insert(600, 500 - heightOfBottomBarrier-groundHeight, heightOfBottomBarrier, 1);
                    }
                }
            }
        }

    }

    //get game score
    File scoreFile = new File("./game.txt");
    public int getScore() {
        try{
            BufferedReader score = new BufferedReader(new FileReader(scoreFile));
        int scoreInt = Integer.parseInt(score.readLine());
        score.close();
        return scoreInt;
        }catch (Exception e) {
            return 0;
        }
        
    }

    //save game score
    public void setScore(String score) {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(scoreFile));
            fileWriter.write(score);
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get the object in the pool
    public void insert(int x, int y, int height, int type) {
        Barrier top = BarrierPool.getPool();
        top.setX(x);
        top.setY(y);
        top.setHeight(height);
        top.setBarrierType(type);
        top.setVisible(true);
        barriers.add(top);
    }

    public void ran() {
        heightOfTopBarrier = random.nextInt(400) + 100;
        heightOfBottomBarrier = random.nextInt(400) + 100;
        randomNumber = random.nextInt(500);
        if (heightOfTopBarrier + heightOfBottomBarrier > 300) {//Screen flickering problem
            ran();
        }
    }

    // check if the barrier collides with the bird
    public void hitBird(Bird bird) {
        birdHeight = bird.getBirdImages()[0].getHeight();       
        for (Barrier barrier : barriers) {
            if (difficultMode) {
                if (barrier.getBarrierRect().intersects(bird.getBirdRect()) || bird.getY()+birdHeight >= 500 || bird.getY()-birdHeight <= 0) {
                    bird.setAlive(false);
                    setGameScore((int)gameTime.differ());
                }
            }else if (barrier.getBarrierRect().intersects(bird.getBirdRect()) || bird.getY()+birdHeight+groundHeight >= 500 || bird.getY()-birdHeight <= 0){
                bird.setAlive(false);
                setGameScore((int)gameTime.differ()); 
            }   
            

        }

        
    }

    // clear the barrier pool
    public void restartBarrierPool() {
        barriers.clear();
    }

    public boolean isDifficultMode() {
        return difficultMode;
    }

    public void setDifficultMode(boolean difficultMode) {
        this.difficultMode = difficultMode;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }


}
