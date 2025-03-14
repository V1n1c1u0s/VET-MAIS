package org.example.vetmais.Controller.Animais;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.vetmais.Controller.UserAware;
import org.example.vetmais.Domain.Animal;
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

public class AnimaisController implements Initializable , UserAware {

    private User currentUser;

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private JFXButton addButton;

    @FXML
    private TableView<Animal> animalTable;

    @FXML
    private TableColumn<Animal, String> breedColumn;

    @FXML
    private TableColumn<Animal, String> cpfColumn;

    @FXML
    private TableColumn<Animal, Date> dateColumn;

    @FXML
    private TableColumn<Animal, String> editColumn;

    @FXML
    private TableColumn<Animal, String> nameColumn;

    @FXML
    private TextField searchBar;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection;

    {
        assert database != null;
        connection = database.getConnection();
    }

    Animal animal = null;

    ObservableList<Animal> animaisList = FXCollections.observableArrayList();
    FilteredList<Animal> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //TODO
        try {
            refresh();
            filteredList = new FilteredList<>(animaisList, p -> true);
            load();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addAnimal(MouseEvent event) throws IOException{
        AnchorPane addConsultaPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/vetmais/View/Animais/FXML/Add.fxml")));
        Scene scene = new Scene(addConsultaPane);
        Stage stage = new Stage();
        stage.setTitle("Animais");
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
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf_proprietario"));
        breedColumn.setCellValueFactory(new PropertyValueFactory<>("breed"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("birth_date"));

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(animal -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // If search bar is empty, show all
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return animal.getBreed().toLowerCase().contains(lowerCaseFilter) ||
                        animal.getCpf_proprietario().toLowerCase().contains(lowerCaseFilter) ||
                        animal.getBirth_date().toString().contains(lowerCaseFilter) ||
                        animal.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });

        animalTable.setItems(filteredList);

        Callback<TableColumn<Animal, String>, TableCell<Animal, String>> cellFactory = (TableColumn<Animal, String> param) -> {
            final TableCell<Animal, String> cell = new TableCell<>() {
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
                                animal = animalTable.getSelectionModel().getSelectedItem();
                                String query = "DELETE FROM animals WHERE cpf_proprietario = ?";
                                PreparedStatement preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1, animal.getCpf_proprietario());
                                preparedStatement.execute();
                                refresh();
                                load();
                            }
                            catch (SQLException ex) {
                                Logger.getLogger(AnimaisController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        editButton.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                animal = animalTable.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/org/example/vetmais/View/Animais/FXML/Edit.fxml")));
                                Parent root = loader.load();
                                EditController editController = loader.getController();
                                editController.setAnimal(animal);
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.show();
                                stage.setOnCloseRequest(evento -> {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmação de Fechamento");
                                    alert.setHeaderText("Tem certeza que deseja fechar?");
                                    alert.setContentText("Se você fechar, as alterações não serão salvas.");

                                    // O que fazer caso o usuário aceite ou cancele o fechamento
                                    alert.showAndWait().ifPresent(response -> {
                                        if (response != ButtonType.OK) {
                                            // Impede o fechamento do Stage
                                            evento.consume(); // Impede o fechamento se o usuário cancelar
                                        } else {
                                            try {
                                                refresh();
                                                load();
                                            } catch (SQLException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    });
                                });
                            } catch (IOException ex) {
                                Logger.getLogger(AnimaisController.class.getName()).log(Level.SEVERE, null, ex);
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
            animaisList.clear();
            String query = "select * from animals";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                animaisList.add(new Animal(
                        resultSet.getString("name"),
                        resultSet.getDate("birth_date").toLocalDate(),
                        resultSet.getString("breed"),
                        resultSet.getString("cpf_proprietario")
                ));
                animalTable.setItems(animaisList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimaisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
