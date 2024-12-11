package org.example.vetmais.Controller.Login;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.example.vetmais.Domain.User;
import org.example.vetmais.Model.DAO.DAOUser;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;
import org.example.vetmais.Model.SwitchScene;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton cadastroBtn;

    @FXML
    private TextField email_cadastro;

    @FXML
    private TextField email_login;

    @FXML
    private JFXButton entrarBtn;

    @FXML
    private Label login_incorreto;

    @FXML
    private Label label_cadastro;

    @FXML
    private Label cadastro_correto;

    @FXML
    private Label cadastro_incorreto;

    @FXML
    private Label label_login;

    @FXML
    private AnchorPane panelSlide;

    @FXML
    private JFXButton ps_cadastroBtn;

    @FXML
    private JFXButton ps_loginBtn;

    @FXML
    private PasswordField senha_cadastro;

    @FXML
    private PasswordField senha_login;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ps_loginBtn.setVisible(false);
        label_login.setVisible(false);
        login_incorreto.setVisible(false);
        cadastro_correto.setVisible(false);
        cadastro_incorreto.setVisible(false);
    }

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection;

    {
        assert database != null;
        connection = database.getConnection();
    }

    DAOUser daoUser = new DAOUser();

    @FXML
    private void cadastrar(ActionEvent event) {
        try{
            User user = new User(email_cadastro.getText(), senha_cadastro.getText());
            user.setEncryptedPassword(user.getPassword());
            daoUser.setConnection(connection);
            if(daoUser.cadastrar(user)){
                cadastro_correto.setVisible(true);
                cadastro_incorreto.setVisible(false);
                email_cadastro.clear();
                senha_cadastro.clear();
            } else {
                cadastro_correto.setVisible(false);
                cadastro_incorreto.setVisible(true);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void login(ActionEvent event) {
        try{
            User user = new User(email_login.getText(), senha_login.getText());
            daoUser.setConnection(connection);
            if(daoUser.buscar(user))
                new SwitchScene(mainPane, "View/menu/fxml/tst.fxml");
            else
                login_incorreto.setVisible(true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void psCadastro(ActionEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.35));
        slide.setNode(panelSlide);

        slide.setToX(-400);
        slide.play();

        panelSlide.setTranslateX(0);

        slide.setOnFinished((ActionEvent e) -> {
            label_cadastro.setVisible(false);
            ps_cadastroBtn.setVisible(false);
            ps_loginBtn.setVisible(true);
            label_login.setVisible(true);
            login_incorreto.setVisible(false);
            cadastro_correto.setVisible(false);
            cadastro_incorreto.setVisible(false);
            email_login.clear();
            senha_login.clear();
        });
    }

    @FXML
    void psLogin(ActionEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.35));
        slide.setNode(panelSlide);

        slide.setToX(0);
        slide.play();

        panelSlide.setTranslateX(-400);

        slide.setOnFinished((ActionEvent e) -> {
            ps_loginBtn.setVisible(false);
            label_login.setVisible(false);
            label_cadastro.setVisible(true);
            ps_cadastroBtn.setVisible(true);
            login_incorreto.setVisible(false);
            cadastro_correto.setVisible(false);
            cadastro_incorreto.setVisible(false);
            senha_cadastro.clear();
            email_cadastro.clear();
        });
    }

}
