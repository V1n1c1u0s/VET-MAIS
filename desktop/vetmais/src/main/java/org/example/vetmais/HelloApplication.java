package org.example.vetmais;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.vetmais.Controller.Menu.MenuController;
import org.example.vetmais.Domain.User;
import org.example.vetmais.Launcher.InitPreloader;
import org.example.vetmais.Launcher.LauncherPreloader;
import org.example.vetmais.Model.DAO.DAOUser;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;
import org.example.vetmais.Model.Session.SessionManager;
import org.example.vetmais.Model.SwitchScene;

import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        DAOUser daoUser = new DAOUser();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/login/fxml/new.fxml"));
        Database db = DatabaseFactory.getDatabase("mysql");
        assert db != null;
        Connection c = db.getConnection();
        daoUser.setConnection(c);
        String token = SessionManager.loadSession();
        if (token != null) {
            User currentUser = daoUser.isTokenValid(token);
            if(currentUser != null){
                fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/menu/fxml/menu.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                MenuController menuController = fxmlLoader.getController();
                menuController.setCurrentUser(currentUser);
                SwitchScene switchScene = new SwitchScene(new AnchorPane(),"View/menu/fxml/menu.fxml", currentUser);
                stage.setScene(scene);
                stage.show();
            } else{
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }
        } else {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        //System.setProperty("javafx.preloader", LauncherPreloader.class.getCanonicalName());
        launch(args);
        //HelloApplication.launch(LauncherPreloader.class, args);
    }
}