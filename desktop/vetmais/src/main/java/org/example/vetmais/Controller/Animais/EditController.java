package org.example.vetmais.Controller.Animais;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.vetmais.Domain.Animal;
import org.example.vetmais.Model.DAO.DAOAnimal;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;

import java.sql.Connection;

public class EditController {

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

    DAOAnimal daoAnimal = new DAOAnimal();

    public void setAnimal(Animal temp) {
        f1.setText(temp.getName());f2.setValue(temp.getBirth_date());f3.setText(temp.getBreed());f4.setText(temp.getCpf_proprietario());
    }

    @FXML
    private void updateAnimal() throws Exception {
        Animal animal = new Animal(f1.getText(), f2.getValue(), f3.getText(), f4.getText());
        daoAnimal.setConnection(connection);
        daoAnimal.atualizar(animal);
    }
}