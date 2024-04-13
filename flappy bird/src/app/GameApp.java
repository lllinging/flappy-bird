package app;

import main.Login;

public class GameApp {
    public static void main(String[] args) {
        
        
        com.sun.javafx.application.PlatformImpl.startup(()->{});
        new Login();
    }
}
