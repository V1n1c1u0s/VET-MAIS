package org.example.vetmais.Controller.Consultas;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.vetmais.Controller.UserAware;
import org.example.vetmais.Domain.Consulta;
import org.example.vetmais.Domain.User;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;
import java.io.IOException;
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

public class ConsultasController implements Initializable , UserAware {

    private User currentUser;

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
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

    @FXML
    private TableColumn<Consulta, String> editColumn;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection;

    {
        assert database != null;
        connection = database.getConnection();
    }

    Consulta consulta = null;

    ObservableList<Consulta> consultaList = FXCollections.observableArrayList();
    FilteredList<Consulta> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //TODO
        try {
            refresh();
            filteredList = new FilteredList<>(consultaList, p -> true);
            load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addConsulta(MouseEvent event) throws Exception {
        AnchorPane addConsultaPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/vetmais/View/Consultas/FXML/add.fxml")));
        Scene scene = new Scene(addConsultaPane);
        Stage stage = new Stage();
        stage.setTitle("Consultas");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(ev -> {
            try{
                refresh();
                load();
            }catch(SQLException e){
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            }
        });
    }

    void load() throws SQLException {
        refresh();
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("pet"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("cpf_proprietario"));
        vetColumn.setCellValueFactory(new PropertyValueFactory<>("namevet"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("data_agendada"));

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(consulta -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // If search bar is empty, show all
                }

                String lowerCaseFilter = newValue.toLowerCase();
                // Filter based on 'pet', 'cpf_proprietario', or 'namevet'
                return consulta.getPet().toLowerCase().contains(lowerCaseFilter) ||
                        consulta.getCpf_proprietario().toLowerCase().contains(lowerCaseFilter) ||
                        consulta.getNamevet().toLowerCase().contains(lowerCaseFilter);
            });
        });

        consultaTable.setItems(filteredList);

        Callback<TableColumn<Consulta, String>, TableCell<Consulta, String>> cellFactory = (TableColumn<Consulta, String> param) -> {
            final TableCell<Consulta, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {

                        JFXButton editButton = new JFXButton("Editar");
                        JFXButton deleteButton = new JFXButton("Excluir");

                        deleteButton.setStyle("-fx-background-color: #ff1744; -fx-text-fill: white; -fx-font-size: 12px;");
                        editButton.setStyle("-fx-background-color: #00E676; -fx-text-fill: white; -fx-font-size: 12px;");

                        if (currentUser != null && !"admin".equals(currentUser.getPrivilege())) {
                            deleteButton.setDisable(true);
                            deleteButton.setOpacity(0.5); // Opcional: deixa o botão visualmente desativado
                        }

                        deleteButton.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                consulta = consultaTable.getSelectionModel().getSelectedItem();
                                String query = "DELETE FROM Consultas WHERE proprietario = ?";
                                PreparedStatement preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1, consulta.getCpf_proprietario());
                                preparedStatement.execute();
                                refresh();
                                load();
                            }
                            catch (SQLException ex) {
                                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        editButton.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                consulta = consultaTable.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/org/example/vetmais/View/Consultas/FXML/Edit.fxml")));
                                Parent root = loader.load();
                                EditController editController = loader.getController();
                                editController.setConsulta(consulta);
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.show();
                                stage.setOnCloseRequest(ev -> {
                                    try{
                                        refresh();
                                        load();
                                    }catch(SQLException e){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                                    }
                                });
                            } catch (IOException ex) {
                                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        HBox managebtn = new HBox(editButton, deleteButton);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteButton, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editButton, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);
                    }
                }
            };
            return cell;
        };
        editColumn.setCellFactory(cellFactory);
    }


    private void refresh() throws SQLException {
        try{
            consultaList.clear();
            String query = "select * from Consultas";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                consultaList.add(new Consulta(
                        resultSet.getString("veterinario"),
                        resultSet.getDate("data_agendada").toLocalDate(),
                        resultSet.getString("pet"),
                        resultSet.getString("proprietario")
                ));
                consultaTable.setItems(consultaList);
                //filteredList.setPredicate(filteredList.getPredicate());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
