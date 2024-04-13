package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import util.Constant;
import util.GameUtil;

public class Bird {
    // attributes
    private Rectangle birdRect;
    private int acceleration;
    private boolean alive = false;
    private BufferedImage[] birdImages;
    private static int state;
    private int x = 200, y = 200;
    private static boolean up = false;

    // constructor to initialize the bird images
    public Bird() {
        birdImages = new BufferedImage[Constant.BIRD_IMG.length];
        for (int i = 0; i < Constant.BIRD_IMG.length; i++) {
            birdImages[i] = GameUtil.loadBufferedImage(Constant.BIRD_IMG[i]);
        }

        int width = birdImages[0].getWidth();
        int height = birdImages[0].getHeight();
        birdRect = new Rectangle(width, height);
    }

    // paint the bird
    public void draw(Graphics g) {
        birdRect.x = this.x;
        birdRect.y = this.y;

        if (alive) {
            flyLogic();
            g.drawImage(birdImages[state], x, y, null);
        } else {
            g.drawImage(birdImages[0], x, y, null);
        }
        // g.drawRect(x, y, (int) birdRect.getWidth(), birdRect.height);

    }

    // control bird's flying direction
    public void flyLogic() {
        if (up) {
            acceleration--;
            y += acceleration;
            if (acceleration < -5) {
                acceleration = -5;
            }
            if (y < 20) {
                y = 20;
                acceleration = 0;
            }
        }
        if (!up) {
            acceleration++;
            y += acceleration;
            if (acceleration > 5) {
                acceleration = 5;
            }
            if (y > 475) {
                y = 475;
                acceleration = 0;

            }
        }
    }

    // control bird's flying state
    public void fly(int fly) {
        switch (fly) {
            case 1:
                state = 1;
                up = true;
                acceleration = -5;
                break;
            case 5:
                state = 2;
                up = false;
                break;
        }
    }

    // restart the bird
    public void restartDrawBird() {
        alive = true;
        x = 200;
        y = 200;
    }

    // getters and setters
    public Rectangle getBirdRect() {
        return birdRect;
    }

    public void setBirdRect(Rectangle birdRect) {
        this.birdRect = birdRect;
    }
    
    public BufferedImage[] getBirdImages() {
        return birdImages;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

}