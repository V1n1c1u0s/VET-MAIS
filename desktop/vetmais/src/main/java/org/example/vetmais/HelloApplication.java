package org.example.vetmais;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.vetmais.Launcher.InitPreloader;
import org.example.vetmais.Launcher.LauncherPreloader;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/login/fxml/new.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/Splash/fxml/splash.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //scene.setFill(Color.TRANSPARENT);
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        //stage.setOpacity(0.5);
        stage.show();
    }

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", LauncherPreloader.class.getCanonicalName());
        launch(args);
//        launch(args);
        //HelloApplication.launch(LauncherPreloader.class, args);
    }
}