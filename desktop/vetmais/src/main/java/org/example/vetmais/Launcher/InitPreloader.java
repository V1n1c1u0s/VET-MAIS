package org.example.vetmais.Launcher;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InitPreloader implements Initializable {
    @FXML
    private Label lblLoading;

    @FXML
    private ProgressBar progresso;

    public static Label loading;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loading=lblLoading;
        progresso.setProgress(0.05);
    }

    /*public String checkFunctions() throws InterruptedException {

        final String[] message = {""};

        Thread t1 = new Thread(() -> {
            message[0] = "First Function";
            Platform.runLater(() -> {
                if (this.lblLoading != null) {
                    this.lblLoading.setText(message[0]);
                } else {
                    System.out.println("Label is still null in runLater.");
                }
            });
        });

        Thread t2 = new Thread(() -> {
            message[0] = "Second Function";
            Platform.runLater(() -> {
                if (this.lblLoading != null) {
                    this.lblLoading.setText(message[0]);
                } else {
                    System.out.println("Label is still null in runLater.");
                }
            });
        });

        try{
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }


        return message[0];
    }*/
    public void handleProgressNotification(Preloader.ProgressNotification info) {
        if (progresso != null) {
            progresso.setProgress(info.getProgress());
        } else {
            System.out.println("ProgressBar is null!");
        }
    }
}

