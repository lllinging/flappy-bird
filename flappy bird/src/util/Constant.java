package util;

import java.awt.Color;

public class Constant {
    //size of frame
    public static final int FRAM_WIDTH = 600;
    // public static final int FRAM_HEIGHT = 500;
    public static final int FRAM_HEIGHT = 500;

    //title of frame
    public static final String FRAM_TITLE = "Flappy Bird";

    //inital location of frame
    public static final int FRAM_X=200;
    public static final int FRAM_Y=200;

    //path of background picture
    public static final String BK_IMG_PATH = "./img/ground.png";

    public static final Color BK_Day_COLOR = new Color(0x4B4CF);
    public static final Color BK_Night_COLOR = new Color(0x00688B);

    public static final String[] CLOUD_IMG = {
        "./img/cloud_0.png",
        "./img/cloud_1.png",
    };
    public static final String[] BIRD_IMG = {
        "./img/3.png",
        "./img/1.png",
        "./img/2.png",
        
    };

    

    public static final String [] BARRIER_IMG_PATH = {
       "./img/pipe.png",
      
        "./img/pipe_top.png",

        "./img/pipe_bottom.png",
        
    };

    public static final String FLY_AUDIO_PATH = "music/fly.wav";

    public static final String HIT_AUDIO_PATH = "music/hit.wav";

    public static final String SCORE_AUDIO_PATH = "music/score.wav";
     
}
