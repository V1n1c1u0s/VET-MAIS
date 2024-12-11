package org.example.vetmais.Controller.Consultas;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.vetmais.Domain.Consulta;
import org.example.vetmais.HelloApplication;
import org.example.vetmais.Model.DAO.DAOConsulta;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasController implements Initializable {
    @FXML
    private JFXButton addButton;

    @FXML
    private TextField searchBar;


    @FXML
    private TableView<Consulta> consultaTable;

    @FXML
    private TableColumn<Consulta, String> clientColumn;

    @FXML
    private TableColumn<Consulta, Date> dateColumn;

    @FXML
    private TableColumn<Consulta, String> ownerColumn;

    @FXML
    private TableColumn<Consulta, String> vetColumn;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection;

    {
        assert database != null;
        connection = database.getConnection();
    }

    DAOConsulta daoConsulta = new DAOConsulta();
    Consulta consulta = null;

    ObservableList<Consulta> consultaList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //TODO
        try {
            load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void addConsulta(MouseEvent event) throws Exception {
        AnchorPane addConsultaPane = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("View/Consultas/FXML/add.fxml")));
        Scene scene = new Scene(addConsultaPane);
        Stage stage = new Stage();
        stage.setTitle("Consultas");
        stage.setScene(scene);
        stage.show();
    }

    private void load() throws SQLException {
        daoConsulta.setConnection(connection);
        refresh();
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("pet"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("data_agendada"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("proprietario"));
        vetColumn.setCellValueFactory(new PropertyValueFactory<>("veterinario"));
        //
        /*Callback<TableColumn<Consulta, String>, TableCell<Consulta, String>> cellFactory = (TableColumn<Consulta, String> param) ->
                final TableCell<Consulta, String> cell = new TableCell<Consulta, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                            FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                            deleteIcon.setStyle();
                        }
                    }
                    return cell;
                };
        */
    }

    private void refresh() throws SQLException {
        try{
            consultaList.clear();
            String query = "select * from `consulta`";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                consultaList.add(new Consulta(
                        resultSet.getString("pet"),
                        resultSet.getDate("data_agendada").toLocalDate(),
                        resultSet.getString("proprietario"),
                        resultSet.getString("veterinario")
                ));
                consultaTable.setItems(consultaList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
