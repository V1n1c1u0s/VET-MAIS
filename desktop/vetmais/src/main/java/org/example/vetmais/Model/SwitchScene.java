package org.example.vetmais.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.example.vetmais.Controller.UserAware;
import org.example.vetmais.Domain.User;
import java.io.IOException;
import java.util.Objects;

public class SwitchScene {
    public SwitchScene(AnchorPane currentAnchorPane, String sceneName, User currentUser) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(sceneName)));
        AnchorPane nextAnchorPane = loader.load();

        Object controller = loader.getController();
        if (controller instanceof UserAware)
            ((UserAware) controller).setCurrentUser(currentUser);

        currentAnchorPane.getChildren().clear();
        currentAnchorPane.getChildren().add(nextAnchorPane);
    }
}
