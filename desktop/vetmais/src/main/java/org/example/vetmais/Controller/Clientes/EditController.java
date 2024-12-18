package org.example.vetmais.Controller.Clientes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.vetmais.Domain.Client;
import org.example.vetmais.Domain.Consulta;
import org.example.vetmais.Model.DAO.DAOClient;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;

import java.sql.Connection;

public class EditController {

    @FXML
    private TextField f1;

    @FXML
    private TextField f2;

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

    DAOClient daoClient = new DAOClient();

    public void setClient(Client temp) {
        f1.setText(temp.getName());f2.setText(temp.getCpf());f3.setText(temp.getEmail());f4.setText(temp.getTelephone());
    }

    @FXML
    void updateCliente(ActionEvent event) throws Exception {
        Client client  = new Client(f1.getText(), f2.getText(), f3.getText(), f4.getText());
        daoClient.setConnection(connection);
        daoClient.atualizar(client);
    }

}
