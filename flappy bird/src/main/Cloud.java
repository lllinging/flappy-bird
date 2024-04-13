package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Cloud {
    //attributes
    private BufferedImage cloudImg;
    private int speed;
    private int x, y;

    //constructor to initialize the cloud
    public Cloud(BufferedImage cloudImg, int speed, int x, int y) {
        this.cloudImg = cloudImg;
        this.speed = speed;
        this.x = x;
        this.y = y;
    }

    //draw the cloud
    public void draw (Graphics g) {
        x -= speed;
        g.drawImage(cloudImg, x, y, null);
    }
    
    //check if the cloud is out of the frame
    public boolean isOutFrame() {
        if (x < -100) {
            return true;
        }
        return false;
    }
}