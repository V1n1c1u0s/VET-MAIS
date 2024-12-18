package org.example.vetmais.Controller.Animais;
import java.sql.Connection;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.vetmais.Domain.Animal;
import org.example.vetmais.Model.DAO.DAOAnimal;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;

public class AddController {

    @FXML
    private TextField f1;

    @FXML
    private DatePicker f2;

    @FXML
    private TextField f3;

    @FXML
    private TextField f4;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection;

    {
        assert database != null;
        connection = database.getConnection();
    }

    private final DAOAnimal daoAnimal = new DAOAnimal();

    @FXML
    private void saveAnimal() throws Exception {
        Animal animal = new Animal(f1.getText(), f2.getValue(), f3.getText(), f4.getText());
        daoAnimal.setConnection(connection);
        daoAnimal.cadastrar(animal);
        f1.clear();f2.setValue(null);f3.clear();f4.clear();
    }
}

