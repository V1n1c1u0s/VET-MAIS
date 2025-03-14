package org.example.vetmais.Controller.Menu;


import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.example.vetmais.Controller.UserAware;
import org.example.vetmais.Domain.User;
import org.example.vetmais.Model.SwitchScene;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable, UserAware {
    private User currentUser;

    @FXML
    private JFXButton bar2;

    @FXML
    private JFXButton bar1;

    @FXML
    private AnchorPane paneslide;

    public MenuController()  {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneslide.setTranslateX(-165);
        bar2.setVisible(false);
        bar1.setVisible(true);
    }

    @FXML
    private void run2(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.35));
        slide.setNode(paneslide);

        slide.setToX(-165);
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

        paneslide.setTranslateX(-165);

        slide.setOnFinished((ActionEvent e) -> {
            bar2.setVisible(true);
            bar1.setVisible(false);
        });
    }

    @FXML
    private AnchorPane content;

    @FXML
    private void pagecli(MouseEvent event) throws IOException {
        new SwitchScene(content, "/org/example/vetmais/View/Clientes/FXML/Clientes.fxml", currentUser);
    }

    @FXML
    private void pageani(MouseEvent event) throws IOException {
        System.out.println(currentUser.getPrivilege());
        new SwitchScene(content, "/org/example/vetmais/View/Animais/FXML/Animais.fxml", currentUser);
    }

    @FXML
    private void pagecons(MouseEvent event) throws IOException {
        new SwitchScene(content, "/org/example/vetmais/View/Consultas/FXML/Consultas.fxml", currentUser);
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
