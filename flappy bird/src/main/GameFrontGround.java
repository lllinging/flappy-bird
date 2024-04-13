package main;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import util.Constant;
import util.GameUtil;

public class GameFrontGround {
    //attributes
    private static final int cloudCount = Constant.CLOUD_IMG.length;
    private ArrayList<Cloud> clouds;
    private static final int CLOUD_SPEED = 3;
    private BufferedImage[] img;
    private Random random;

    //constructor to initialize the clouds
    public GameFrontGround() {
        clouds = new ArrayList<>();
        img = new BufferedImage[cloudCount];
        
        //load the cloud images
        for (int i = 0; i < cloudCount; i++) {
            img[i] = GameUtil.loadBufferedImage(Constant.CLOUD_IMG[i]);
        }
        random = new Random();

        }
    
    //draw the clouds
    public void draw (Graphics g) {
        logic();
        for (int i = 0; i < clouds.size()
        ; i++) { 
            
            clouds.get(i).draw(g);
        }

    }

    //control the clouds' movement
    public void logic() {
        if ((int) (150*Math.random()) < 3) {
            Cloud cloud = new Cloud(img[random.nextInt(cloudCount)], CLOUD_SPEED, 600, 100+random.nextInt(50));
            clouds.add(cloud);
        }
        for (int i = 0; i < clouds.size(); i++) {
            Cloud cloud = clouds.get(i);
            // cloud.draw(null);
            if (cloud.isOutFrame()) {
                clouds.remove(i);
                i--;
            }
           
        }
    }
}
    

