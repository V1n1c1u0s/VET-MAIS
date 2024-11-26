package org.example.vetmais.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.example.vetmais.HelloApplication;

import java.io.IOException;
import java.util.Objects;

public class SwitchScene {
    public SwitchScene(AnchorPane currentAnchorPane, String sceneName) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource(sceneName)));
        currentAnchorPane.getChildren().removeAll();
        currentAnchorPane.getChildren().setAll(nextAnchorPane);
    }
}
