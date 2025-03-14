package org.example.vetmais;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.vetmais.Controller.Menu.MenuController;
import org.example.vetmais.Domain.User;
import org.example.vetmais.Model.DAO.DAOUser;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;
import org.example.vetmais.Model.Session.SessionManager;
import org.example.vetmais.Model.SwitchScene;

import java.io.IOException;
import java.sql.Connection;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        DAOUser daoUser = new DAOUser();
        //FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("View/login/fxml/new.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/vetmais/View/Login/FXML/new.fxml"));
        Database db = DatabaseFactory.getDatabase("mysql");
        assert db != null;
        Connection c = db.getConnection();
        daoUser.setConnection(c);
        String token = SessionManager.loadSession();
        if (token != null) {
            User currentUser = daoUser.isTokenValid(token);
            if(currentUser != null){
                //fxmlLoader = new FXMLLoader(App.class.getResource("View/menu/fxml/menu.fxml"));
                fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/vetmais/View/Menu/FXML/menu.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                MenuController menuController = fxmlLoader.getController();
                menuController.setCurrentUser(currentUser);
                //SwitchScene switchScene = new SwitchScene(new AnchorPane(),"View/menu/fxml/menu.fxml", currentUser);
                new SwitchScene(new AnchorPane(), "/org/example/vetmais/View/Menu/FXML/menu.fxml", currentUser);
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
        launch(args);
    }
}