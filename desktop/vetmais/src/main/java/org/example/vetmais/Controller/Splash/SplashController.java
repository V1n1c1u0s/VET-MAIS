package org.example.vetmais.Controller.Splash;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {
    @FXML
    private Label lblLoading;

    @FXML
    private ProgressBar progresso;

    public static Label loading;
    private Stage proloaderStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loading=lblLoading;
        progresso.setProgress(0);
    }

    public String checkFunctions() throws InterruptedException {

        final String[] message = {""};

        final double[] progress = {0};

        Thread t1 = new Thread(() -> {
            try {
                // Realiza a primeira função com incrementos na barra de progresso
                for (int i = 0; i < 5; i++) {  // Divida em 5 incrementos
                    progress[0] += 0.1;  // Incrementa 10% a cada passo
                    message[0] = (i + 1) + "/10)";
                    Platform.runLater(() -> {
                        if (this.lblLoading != null) {
                            this.lblLoading.setText(message[0]);
                            progresso.setProgress(progress[0]);
                        }
                    });
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                // Realiza a segunda função com incrementos na barra de progresso
                for (int i = 5; i < 10; i++) {  // Divida em 5 incrementos
                    progress[0] += 0.1;  // Incrementa mais 10%
                    message[0] = (i + 1) + "/10)";
                    Platform.runLater(() -> {
                        if (this.lblLoading != null) {
                            this.lblLoading.setText(message[0]);
                            progresso.setProgress(progress[0]);
                        }
                    });
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return message[0];
    }
}
