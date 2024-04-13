
package main;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import util.Constant;
import util.GameUtil;
import static util.Constant.*;

public class GameFrame extends Frame {
    // attributes
    private static int gameState;
    private static final int gameStateStart = 0;
    private static final int gameStateRunning = 1;
    private static final int gameStateOver = 2;
    private GameBackground gameBackground;
    private Bird bird;
    private GameBarrierLayer gameBarrierLayer;
    private GameFrontGround gameFrontGround;
    private ReadyForGame readyForGame;
    private GameOver gameOver;
    private GameUtil soundPlayer;
    private boolean running = true;
    private boolean flag = true;
    private String currentUser;

    public String getCurrentUser() {
        return this.currentUser;
    }

    private BufferedImage buffImg = new BufferedImage(FRAM_WIDTH, FRAM_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);;

    // constructor
    public GameFrame(String currentUser) {
        // set the frame's attributes
        System.out.println("Current User: "+currentUser);
        setSize(FRAM_WIDTH, FRAM_HEIGHT);
        setTitle(FRAM_TITLE);
        setLocation(FRAM_X, FRAM_Y);
        setResizable(false);
        setLayout(new FlowLayout());
        setVisible(true);
        this.currentUser = currentUser;

        // initialize the game
        initalizeGame();
        // start the game
        new run().start();
        // run();

        // add keyboard monitor
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                addKeypressedListener(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                addKeyReleasedListener(e);

            }
        });
        // add window listener(window close)
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    // initialize the game
    public void initalizeGame() {
        // initialize the game elements
        gameBackground = new GameBackground();       
        gameFrontGround = new GameFrontGround();
        gameBarrierLayer = new GameBarrierLayer();
        soundPlayer = new GameUtil();
        readyForGame = new ReadyForGame(this);
        bird = new Bird();
    }

    // inner class to draw the game elements
    class run extends Thread {
        @Override
        public void run() {
            while (flag) {
                repaint();
                try {
                    Thread.sleep(33);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // call and draw the game elements in the frame
    public void update(Graphics g) {
        // check if the game is over and draw the game over screen
        if (running == false) {
            flag = false;
            soundPlayer.playSound(Constant.HIT_AUDIO_PATH);
            gameOver = new GameOver(this);

            gameOver.draw(g);

            try {
            Thread.sleep(1000);
            } catch (InterruptedException e) {
            e.printStackTrace();
            }
            soundPlayer.playSound(Constant.SCORE_AUDIO_PATH);
            return;
        } else {
            // if game is not over, draw the game elements
            Graphics graphics = buffImg.getGraphics();
            gameBackground.draw(graphics);
            gameFrontGround.draw(graphics);
            // draw the game elements based on the game state
            // if the game is in the start state, draw the game ready screen
            // if the game is in the running state, draw the game elements
            if (gameState == gameStateStart) {
                bird.setAlive(false);
                bird.draw(graphics);
                readyForGame.drawGameReady(graphics);
            } else if (gameState == gameStateRunning) {
                readyForGame.hideAllButtons();
                System.out.println("running");
                bird.setAlive(true);
                bird.draw(graphics);
                gameBarrierLayer.draw(graphics, bird);
                if (bird.isAlive() == false) {
                    running = false;
                    gameState = gameStateOver;
                }
            } 
            // else if (gameState == gameStateOver) {
            //     running = false;
                // System.out.println("ssss//" + soundPlayer.getSound());
                // soundPlayer.playSound(Constant.HIT_AUDIO_PATH);
                // gameOver = new GameOver(this);

                // System.out.println("game over/////////////");
                // gameOver.draw(g);

                // try {
                //     Thread.sleep(1000);
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
                // soundPlayer.playSound(Constant.SCORE_AUDIO_PATH);
            // }

            g.drawImage(buffImg, 0, 0, null);
        }
    }

    // add keyboard monitor
    public void addKeypressedListener(KeyEvent e) {
        System.out.println("addKeypressedListener(KeyEvent e)" + gameState);
        switch (gameState) {
            case gameStateStart:
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    bird.setAlive(true);
                    gameState = gameStateRunning;
                }
                break;
            case gameStateRunning:
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    bird.fly(1);
                    soundPlayer.playSound(Constant.FLY_AUDIO_PATH);
                    System.out.println("gameStateRunning" + running);
                }
                if (running == false) {
                    gameState = gameStateOver;
                }
                break;
            case gameStateOver:
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (running == false) {
                        restartGame();
                        System.out.println("restart.................m");
                    }

                }
                break;
            default:
                break;
        }
    }

    // add keyboard monitor
    public void addKeyReleasedListener(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                bird.fly(5);
                break;
            default:
                break;
        }
    }

    // restart the game
    public void restartGame() {
        System.out.println("restart.................");
        flag = true;
        running = true;
        gameState = gameStateRunning;
        gameOver.viewButtonInvisible();
        // bird.setAlive(true);
        bird.restartDrawBird();
        gameBarrierLayer.restartBarrierPool();
    }

    // getters and setters
    public GameBackground getGameBackground() {
        return gameBackground;
    }

    public Bird getBird() {
        return bird;
    }

    public GameBarrierLayer getGameBarrierLayer() {
        return gameBarrierLayer;
    }

    public GameFrontGround getGameFrontGround() {
        return gameFrontGround;
    }

    public boolean isRunning() {
        return running;
    }

    public GameUtil getSoundPlayer() {
        return soundPlayer;
    }


}
