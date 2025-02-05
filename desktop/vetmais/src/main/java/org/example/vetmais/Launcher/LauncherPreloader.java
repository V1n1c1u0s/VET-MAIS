package org.example.vetmais.Launcher;

import javafx.application.Preloader;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.vetmais.HelloApplication;

import java.io.IOException;
import java.util.Objects;

public class LauncherPreloader extends Preloader {
    private ProgressBar progressBar;
    private Stage proloaderStage;

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        this.proloaderStage = primaryStage;
        FXMLLoader root = new FXMLLoader(getClass().getResource("/org/example/vetmais/View/Splash/FXML/splash.fxml"));
        Scene scene = new Scene(root.load());
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        if (Objects.equals(info.getType(), StateChangeNotification.Type.BEFORE_START)) {
            proloaderStage.hide();
        }
    }
}
