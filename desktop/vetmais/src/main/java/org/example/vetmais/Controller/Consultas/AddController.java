package org.example.vetmais.Controller.Consultas;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.vetmais.Domain.Consulta;
import org.example.vetmais.Model.DAO.DAOConsulta;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;

import java.sql.Connection;

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

    DAOConsulta daoConsulta = new DAOConsulta();

    @FXML
    private void saveConsulta() throws Exception {
        Consulta consulta = new Consulta(f1.getText(), f2.getValue(), f3.getText(), f4.getText());
        daoConsulta.setConnection(connection);
        daoConsulta.cadastrar(consulta);
        f1.clear();f2.setValue(null);f3.clear();f4.clear();
    }

}