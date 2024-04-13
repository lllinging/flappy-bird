package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
      

import javax.imageio.ImageIO;

public class GameUtil extends Thread{

    private String audioPath;
    private boolean sound = true;
    ;
    

    public GameUtil() {
    }


    public static BufferedImage loadBufferedImage(String imgPath) {
        try {
            return ImageIO.read(new FileInputStream(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {
        playSound(this.audioPath);
    }

    public void playSound(String audioPath) {
        if (sound == true) {
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File(audioPath).toURI().toString()));
            mediaPlayer.play();
        }
        return;
        

    }

    public String getAudioPath() {
        return audioPath;
    }

    public boolean isSound() {
        return sound;
    }

    public boolean getSound() {
        return this.sound; 
    }
    public void setSound(boolean sound) {
        this.sound = sound;
    }


}
