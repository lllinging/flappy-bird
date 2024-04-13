package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import util.Constant;
import util.GameUtil;

public class Barrier {
    // attributes
    private Rectangle barrierRect;// rectangle of the pipe
    private boolean mode = true;// mode of the pipe
    private int speed = 2;// speed of the pipe
    private static BufferedImage[] pipeImg;// images of the pipe
    private boolean visible;// visibility of the pipe
    private int barrierType;// type of barrier
    private int count = 0;// count of the pipe to fill the frame
    private int ySpeed = 1;// speed of the pipe
    private int yMovementCount = 0;// count of the pipe y axis movement    
    public static int randomGap;// gap between the top and bottom pipes

    // attributes: barrier position and size
    private int x, y;
    private int width, height;

    //attributes: barrier type
    private final int typeTopNormal = 0;
    private final int typeBottomNormal = 1;
    private final int typeHoverNormal = 2;
    private final int typeTopHard = 3;
    private final int typeBottomHard = 4;

    // attributes: get barrier weight and height
    private final int barrierWidth = pipeImg[0].getWidth();
    private final int barrierHeight = pipeImg[0].getHeight();
    private final int barrierTopHeadWidth = pipeImg[1].getWidth();
    private final int barrierTopHeadHeight = pipeImg[1].getHeight();

    // constructor to initialize the pipe
    public Barrier() {
        barrierRect = new Rectangle();
        randomGap = randomGap();
    }

    // constructor to initialize the pipe
    public Barrier(int x, int y, int height, int barrierType) {
        this.x = x;
        this.y = y;
        this.width = barrierWidth;
        this.height = height;
        this.barrierType = barrierType;

    }

    // load pipe images when the class is loaded
    static {
        final int pipeCount = 3;
        pipeImg = new BufferedImage[pipeCount];
        for (int i = 0; i < pipeCount; i++) {
            pipeImg[i] = GameUtil.loadBufferedImage(Constant.BARRIER_IMG_PATH[i]);
        }
    }

    // draw the pipe based barrier type
    public void draw(Graphics g) {
        switch (barrierType) {
            case typeTopNormal:
                drawTopNormal(g);
                break;
            case typeBottomNormal:
                drawBottomNormal(g);
                break;
            case typeHoverNormal:
                drawHoverNormal(g);
                break;
            case typeTopHard:
                drawTopHard(g);
                break;
            case typeBottomHard:
                drawBottomHard(g);
                break;
        }

    }

    // draw barrier type from top to bottom
    public void drawTopNormal(Graphics g) {
        // get the number of pipes needed to fill the frame
        count = (height - barrierTopHeadHeight) / barrierHeight + 1;
        // draw the top pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(pipeImg[0], x, y + i * barrierHeight, null);
        }

        // draw the top pipe head
        int y = height - barrierTopHeadHeight;
        g.drawImage(pipeImg[1], x - (barrierTopHeadWidth - barrierWidth) / 2, y, null);
        movementOfXAxis();
        rect(g);

    }

    // draw barrier type of hovering
    private void drawHoverNormal(Graphics g) {
        // get the number of pipes needed to fill the frame
        count = (height - 2 * barrierTopHeadHeight) / barrierHeight + 1;
        // draw the top pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(pipeImg[0], x, y + barrierTopHeadHeight + i * barrierHeight, null);
        }

        // draw the top pipe head
        g.drawImage(pipeImg[2], x - (barrierTopHeadWidth - barrierWidth) / 2, y, null);
        rect(g);

        // draw the bottom pipe head
        g.drawImage(pipeImg[1], x - (barrierTopHeadWidth - barrierWidth) / 2, y + height - barrierTopHeadHeight,
                null);
        movementOfXAxis();

    }

    // draw barrier type from bottom to top
    private void drawBottomNormal(Graphics g) {
        // get the number of pipes needed to fill the frame
        GameBackground gameBackground = new GameBackground();
        int groundHeight = gameBackground.getBkImg().getHeight();

        int count = (height - barrierTopHeadHeight) / barrierHeight + 1;
        // delete soon

        // draw the bottom pipe
        for (int i = 0; i < count; i++) {

            g.drawImage(pipeImg[0], x, Constant.FRAM_HEIGHT - barrierHeight - groundHeight - i * barrierHeight, null);
        }

        // draw the bottom pipe head
        g.drawImage(pipeImg[2], x - (barrierTopHeadWidth - barrierWidth) / 2,
                Constant.FRAM_HEIGHT - groundHeight - height, null);

        movementOfXAxis();
        rect(g);

    }

    // draw hard barrier type from top to bottom
    public void drawTopHard(Graphics g) {
        int countTop = (height - barrierTopHeadHeight) / barrierHeight + 1;
        // draw the top pipe
        for (int i = 0; i < countTop; i++) {
            g.drawImage(pipeImg[0], x, y + i * barrierHeight, null);
        }

        // draw the top pipe head
        int y = height - barrierTopHeadHeight;
        g.drawImage(pipeImg[1], x - (barrierTopHeadWidth - barrierWidth) / 2, y, null);

        movementOfXAxis();

        rect(g);

        if (mode) {
            if (yMovementCount == 30) {
                ySpeed = -1;
            }
            if (yMovementCount == 0) {
                ySpeed = 1;
            }
            y += ySpeed;
            height += ySpeed;
            yMovementCount += ySpeed;

        }

    }

    // draw hard barrier type from bottom to top
    private void drawBottomHard(Graphics g) {

        // draw the bottom pipe
        count = ((Constant.FRAM_HEIGHT - y - barrierTopHeadHeight) / barrierHeight + 1);
        for (int i = 0; i < count; i++) {
            g.drawImage(pipeImg[0], x, y + height - barrierHeight - i * barrierHeight, null);
        }

        // draw the bottom pipe head
        g.drawImage(pipeImg[2], x - (barrierTopHeadWidth - barrierWidth) / 2, y, null);

        movementOfXAxis();

        rect(g);

        if (mode) {

            if (yMovementCount == 30) {
                ySpeed = -1;
            }
            if (yMovementCount == 0) {
                ySpeed = 1;
            }
            y += ySpeed;
            height -= ySpeed;
            yMovementCount += ySpeed;

        }

    }

    // draw the rectangle of the pipe
    public void rect(Graphics g) {
        int x1 = x;
        int y1 = y;
        int w1 = pipeImg[2].getWidth();

        setRect(x1, y1, w1, height);
    }

    // rectangle parameter of barrier collision detection
    public void setRect(int x, int y, int width, int height) {
        barrierRect.x = x;
        barrierRect.y = y;
        barrierRect.width = width;
        barrierRect.height = height;

    }

    // method to make pipe move along X-axis movement
    public void movementOfXAxis() {
        x -= speed;
        if (x < -50) {
            visible = false;
        }
    }

    // method to check if the pipe is in the frame
    public boolean isInFrame() {
        return x < -1 * barrierTopHeadWidth + 400;
    }

    // method to generate a random gap between the top and bottom pipes
    public int randomGap() {
        return (int) (80 + Math.random() * 50);
    }

    // getters and setters
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBarrierType() {
        return barrierType;
    }

    public void setBarrierType(int barrierType) {
        this.barrierType = barrierType;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBarrierRect() {
        return barrierRect;
    }


    public int getRandomGap() {
        return randomGap;
    }

    public void setRandomGap(int randomGap) {
        this.randomGap = randomGap;
    }

}
