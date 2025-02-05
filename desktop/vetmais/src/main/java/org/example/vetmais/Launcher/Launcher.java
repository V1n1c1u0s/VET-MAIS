package org.example.vetmais.Launcher;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.vetmais.HelloApplication;

public class Launcher extends Application {

    public static Stage primaryStage = null;
    public static Scene primaryScene = null;

    @Override
    public void init() throws Exception {
        InitPreloader initL = new InitPreloader();
        initL.checkFunctions();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Launcher.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        //launch();
        //Launcher.launch();
        //System.setProperty("javafx.preloader", LauncherPreloader.class.getCanonicalName());
        //launch(args);
        //launch(LauncherPreloader.class, args);
        //LauncherImpl.launchApplication(Launcher.class, LauncherPreloader.class, args);
    }
}