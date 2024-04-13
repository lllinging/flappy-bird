package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.GameUtil;
import util.Constant;

public class GameBackground {
    //attributes
    private BufferedImage bkImg;
    private Color bkColor;

    //constructor
    public GameBackground() {
        bkImg = GameUtil.loadBufferedImage(Constant.BK_IMG_PATH);
        bkColor = Constant.BK_Day_COLOR;
    }

    //draw background pictures
    public void draw (Graphics g) {
        //fill the background with color
        g.setColor(bkColor);
        g.fillRect(0, 0, Constant.FRAM_WIDTH, Constant.FRAM_HEIGHT);
        g.setColor(Color.black);
        
        //get the size of the background picture
        int height = bkImg.getHeight();
        int width = bkImg.getWidth();
        //number of pictures needed to fill the frame
        int count = Constant.FRAM_WIDTH / width + 1;
        for (int i = 0; i < count; i++) {
            g.drawImage(bkImg, i * width, Constant.FRAM_HEIGHT-height, null);
        }
    }

    //getters and setters
    public BufferedImage getBkImg() {
        return bkImg;
    }

    public Color getBkColor() {
        return bkColor;
    }

    public void setBkColor(Color bkColor) {
        this.bkColor = bkColor;
    }


}
