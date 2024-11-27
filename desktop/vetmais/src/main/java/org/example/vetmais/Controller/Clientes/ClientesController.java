package org.example.vetmais.Controller.Clientes;

import com.jfoenix.controls.JFXButton;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.vetmais.Domain.Client;
import org.example.vetmais.Model.DAO.DAOClient;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;

import java.sql.Connection;

public class ClientesController{

    @FXML
    private TextField t1 = new TextField();

    @FXML
    private TextField t2 = new TextField();

    @FXML
    private TextField t3 = new TextField();

    @FXML
    private TextField t4 =  new TextField();

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection;

    {
        assert database != null;
        connection = database.getConnection();
    }

    private final DAOClient daoClient = new DAOClient();

    @FXML
    private void saveClient() throws Exception {
        Client client = new Client(t1.getText(), t2.getText(), t3.getText(), t4.getText());
        daoClient.setConnection(connection);
        daoClient.cadastrar(client);
        t1.clear();t2.clear();t3.clear();t4.clear();
    }
}
