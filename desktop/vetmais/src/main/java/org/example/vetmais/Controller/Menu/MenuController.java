package org.example.vetmais.Controller.Menu;


import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.example.vetmais.Controller.UserAware;
import org.example.vetmais.Domain.User;
import org.example.vetmais.Model.Session.SessionManager;
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
        new SwitchScene(content, "/org/example/vetmais/View/Animais/FXML/Animais.fxml", currentUser);
    }

    @FXML
    private void pagecons(MouseEvent event) throws IOException {
        new SwitchScene(content, "/org/example/vetmais/View/Consultas/FXML/Consultas.fxml", currentUser);
    }

    @FXML
    void pagecont(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Contato");
        alert.setHeaderText("Clicou no contato");
        alert.setContentText("Deseja fazer contato?");
        alert.showAndWait();
    }

    @FXML
    void pageest(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Estoque");
        alert.setHeaderText("Estoque");
        alert.setContentText("Veja o estoque");
        alert.showAndWait();
    }

    @FXML
    void pagerel(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if("admin".equals(currentUser.getPrivilege())) {
            alert.setTitle("Relatórios");
            alert.setHeaderText("Relatórios");
            alert.setContentText("Veja os relatórios.");
        } else {
            alert.setHeaderText("Relatórios");
            alert.setContentText("Você não tem permissão para acessar essa página.");
        }
        alert.showAndWait();
    }


        @FXML
    void logOut(ActionEvent event) throws IOException {
        SessionManager.clearSession();
        //closeWindow(event);
        javafx.stage.Stage stage = (javafx.stage.Stage) ((JFXButton) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/vetmais/View/Login/FXML/Login.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    @FXML
    void closeWindow(ActionEvent event) {
        ((javafx.stage.Stage) ((JFXButton) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void minWindow(ActionEvent event) {
        javafx.stage.Stage stage = (javafx.stage.Stage) ((JFXButton) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
