package org.example.vetmais.Controller.Menu;


import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.example.vetmais.Model.SwitchScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private JFXButton bar2;

    @FXML
    private JFXButton bar1;

    @FXML
    private AnchorPane paneslide;

    public MenuController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneslide.setTranslateX(-200);
        bar2.setVisible(false);
        bar1.setVisible(true);
    }

    @FXML
    private void run2(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.35));
        slide.setNode(paneslide);

        slide.setToX(-200);
        slide.play();

        paneslide.setTranslateX(0);

        slide.setOnFinished((ActionEvent e) -> {
            bar2.setVisible(false);
            bar1.setVisible(true);
        });
    }

    @FXML
    private void run1(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.35));
        slide.setNode(paneslide);

        slide.setToX(0);
        slide.play();

        paneslide.setTranslateX(-200);

        slide.setOnFinished((ActionEvent e) -> {
            bar2.setVisible(true);
            bar1.setVisible(false);
        });
    }

    @FXML
    private AnchorPane content;

    @FXML
    private void pagecli(MouseEvent event) throws IOException {
        new SwitchScene(content, "View/Clientes/FXML/Clientes.fxml");
        System.out.println("MUDOUUUUU 1");
    }

    @FXML
    private void pageani(MouseEvent event) throws IOException {
        new SwitchScene(content, "View/Animais/FXML/Animais.fxml");
        System.out.println("MUDOUUUUU 2");
    }
}
