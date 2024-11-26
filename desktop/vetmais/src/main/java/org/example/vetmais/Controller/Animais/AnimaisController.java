package org.example.vetmais.Controller.Animais;
import com.jfoenix.controls.JFXButton;
import java.sql.Connection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;

public class AnimaisController{

    @FXML
    private JFXButton add_animal;

    @FXML
    private TextField f1;

    @FXML
    private TextField f2;

    @FXML
    private TextField f3;

    @FXML
    private TextField f4;

    @FXML
    private Label l1;

    @FXML
    private Label l2;

    @FXML
    private Label l3;

    @FXML
    private Label l4;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection;

    {
        assert database != null;
        connection = database.getConnection();
    }

    private final DAOClient daoClient = new daoClient();
}

