package org.example.vetmais.Controller.Clientes;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.example.vetmais.Domain.Client;
import org.example.vetmais.HelloApplication;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientesController implements Initializable {

    @FXML
    private JFXButton addButton;

    @FXML
    private TableView<Client> clienteTable;

    @FXML
    private TableColumn<Client, String> cpfColumn;

    @FXML
    private TableColumn<Client, String> editColumn;

    @FXML
    private TableColumn<Client, String> mailColumn;

    @FXML
    private TableColumn<Client, String> nameColumn;

    @FXML
    private TableColumn<Client, String> phoneColumn;

    @FXML
    private TextField searchBar;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection;

    {
        assert database != null;
        connection = database.getConnection();
    }

    Client client = null;

    ObservableList<Client> clientList = FXCollections.observableArrayList();

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
    void addCliente(MouseEvent event) throws Exception {
        AnchorPane addClientePane = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("View/Clientes/FXML/Add.fxml")));
        Scene scene = new Scene(addClientePane);
        Stage stage = new Stage();
        stage.setTitle("Clientes");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(ev -> {
            try{
                refresh();
            }catch(SQLException e){
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            }
        });
    }

    void load() throws SQLException {
        refresh();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        //
        Callback<TableColumn<Client, String>, TableCell<Client, String>> cellFactory = (TableColumn<Client, String> param) -> {
            final TableCell<Client, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        /* Ajeitar icones
                        FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
                        FontAwesomeIcon editIcon = new FontAwesomeIcon();


                        deleteIcon.setIcon(FontAwesomeIcons.TRASH);
                        editIcon.setIcon(FontAwesomeIcons.valueOf("PENCIL_SQUARE"));*/

                        JFXButton editButton = new JFXButton("Editar");
                        JFXButton deleteButton = new JFXButton("Excluir");

                        deleteButton.setStyle("-fx-background-color: #ff1744; -fx-text-fill: white; -fx-font-size: 12px;");
                        editButton.setStyle("-fx-background-color: #00E676; -fx-text-fill: white; -fx-font-size: 12px;");

                        /*deleteIcon.setStyle(
                                "-fx-cursor: hand; -fx-fill:#ff1744; -fx-font-size:28px;"
                        );
                        editIcon.setStyle(
                                "-fx-cursor: hand; -glyph-size:28px; -fx-fill:#00E676;"
                        );*/
                        deleteButton.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                client = clienteTable.getSelectionModel().getSelectedItem();
                                String query = "DELETE FROM clients WHERE cpf = ?";
                                PreparedStatement preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1, client.getCpf());
                                preparedStatement.execute();
                                refresh();
                            }
                            catch (SQLException ex) {
                                Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        editButton.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                client = clienteTable.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(HelloApplication.class.getResource("View/Clientes/FXML/Edit.fxml")));
                                Parent root = loader.load();
                                EditController editController = loader.getController();
                                editController.setClient(client);
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.show();
                                stage.setOnCloseRequest(ev -> {
                                    try{
                                        refresh();
                                    }catch(SQLException e){
                                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                                    }
                                });
                            } catch (IOException ex) {
                                Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
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
        clienteTable.setItems(clientList);
    }

    private void refresh() throws SQLException {
        try{
            clientList.clear();
            String query = "select * from clients";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                clientList.add(new Client(
                        resultSet.getString("name"),
                        resultSet.getString("cpf"),
                        resultSet.getString("email"),
                        resultSet.getString("telephone")
                ));
                clienteTable.setItems(clientList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
