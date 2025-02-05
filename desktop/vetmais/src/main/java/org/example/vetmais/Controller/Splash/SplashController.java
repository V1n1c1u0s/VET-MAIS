package org.example.vetmais.Controller.Splash;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {
    @FXML
    private ProgressBar progresso;


    public Label lblloadingg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public String checkFunctions(){

        final String[] message = {""};

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                message[0] = "First Function";
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        lblloadingg.setText(message[0]);
                    }
                });
            }
        });

        Thread t2 = new Thread(() -> {
            message[0] = "Second Function";
            Platform.runLater(() -> lblloadingg.setText(message[0]));
        });

        try{
            t1.start();
            t1.join();
            t2.start();
            t2.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return message[0];
    }
}
