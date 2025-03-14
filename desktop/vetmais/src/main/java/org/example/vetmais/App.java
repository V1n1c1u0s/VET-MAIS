package org.example.vetmais;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.vetmais.Controller.Splash.SplashController;
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
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("/org/example/vetmais/View/Splash/FXML/splash.fxml"));
        Scene scene = new Scene(root.load());
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        SplashController controller = root.getController();
        Thread t1 = new Thread(() -> {
            try {
                controller.checkFunctions();

                Platform.runLater(() -> {
                    DAOUser daoUser = new DAOUser();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/vetmais/View/Login/FXML/new.fxml"));
                    Database db = DatabaseFactory.getDatabase("mysql");
                    assert db != null;
                    Connection c = db.getConnection();
                    daoUser.setConnection(c);
                    String token = null;
                    try {
                        token = SessionManager.loadSession();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (token != null) {
                        User currentUser = daoUser.isTokenValid(token);
                        if(currentUser != null){
                            fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/vetmais/View/Menu/FXML/menu.fxml"));
                            try {
                                Scene sceneA = new Scene(fxmlLoader.load());
                                MenuController menuController = fxmlLoader.getController();
                                menuController.setCurrentUser(currentUser);
                                new SwitchScene(new AnchorPane(), "/org/example/vetmais/View/Menu/FXML/menu.fxml", currentUser);
                                primaryStage.setScene(sceneA);
                                primaryStage.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else{
                            Scene sceneB = null;
                            try {
                                sceneB = new Scene(fxmlLoader.load());
                                primaryStage.setScene(sceneB);
                                primaryStage.show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        Scene sceneC = null;
                        try {
                            sceneC = new Scene(fxmlLoader.load());
                            primaryStage.setScene(sceneC);
                            primaryStage.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.setDaemon(true);
        t1.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}