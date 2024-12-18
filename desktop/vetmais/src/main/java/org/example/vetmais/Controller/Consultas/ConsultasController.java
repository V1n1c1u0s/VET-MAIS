package org.example.vetmais.Controller.Consultas;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
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
import org.example.vetmais.Domain.Consulta;
import org.example.vetmais.HelloApplication;
import org.example.vetmais.Model.Database.Database;
import org.example.vetmais.Model.Database.DatabaseFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    void addConsulta(MouseEvent event) throws Exception {
        AnchorPane addConsultaPane = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("View/Consultas/FXML/add.fxml")));
        Scene scene = new Scene(addConsultaPane);
        Stage stage = new Stage();
        stage.setTitle("Consultas");
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
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("pet"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("cpf_proprietario"));
        vetColumn.setCellValueFactory(new PropertyValueFactory<>("namevet"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("data_agendada"));

        //
        Callback<TableColumn<Consulta, String>, TableCell<Consulta, String>> cellFactory = (TableColumn<Consulta, String> param) -> {
            final TableCell<Consulta, String> cell = new TableCell<>() {
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
                                consulta = consultaTable.getSelectionModel().getSelectedItem();
                                String query = "DELETE FROM Consultas WHERE proprietario = ?";
                                PreparedStatement preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1, consulta.getCpf_proprietario());
                                preparedStatement.execute();
                                refresh();
                            }
                            catch (SQLException ex) {
                                Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        editButton.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                consulta = consultaTable.getSelectionModel().getSelectedItem();
                                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(HelloApplication.class.getResource("View/Consultas/FXML/Edit.fxml")));
                                Parent root = loader.load();
                                EditController editController = loader.getController();
                                editController.setConsulta(consulta);
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
        consultaTable.setItems(consultaList);
    }

    private void refresh() throws SQLException {
        try{
            consultaList.clear();
            String query = "select * from consultas";
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
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
