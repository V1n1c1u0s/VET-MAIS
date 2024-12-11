module org.example.vetmais {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.jfoenix;
    requires java.sql;
    requires jdk.jdi;
    requires mysql.connector.j;
    requires java.desktop;
    requires io.github.cdimascio.dotenv.java;
    requires fontawesomefx;
    requires de.mkammerer.argon2.nolibs;

    opens org.example.vetmais to javafx.fxml;
    exports org.example.vetmais;
    exports org.example.vetmais.Controller;
    opens org.example.vetmais.Controller to javafx.fxml;
    exports org.example.vetmais.Controller.Menu;
    opens org.example.vetmais.Controller.Menu to javafx.fxml;
    exports org.example.vetmais.Controller.Login;
    opens org.example.vetmais.Controller.Login to javafx.fxml;
    exports org.example.vetmais.Controller.Animais;
    opens org.example.vetmais.Controller.Animais to javafx.fxml;
    exports org.example.vetmais.Model;
    opens org.example.vetmais.Model to javafx.fxml;
    exports org.example.vetmais.Controller.Clientes;
    opens org.example.vetmais.Controller.Clientes to javafx.fxml;
    exports org.example.vetmais.Controller.Consultas;
    opens org.example.vetmais.Controller.Consultas to javafx.fxml;
    exports org.example.vetmais.Domain;
    opens org.example.vetmais.Domain to javafx.fxml;
    exports org.example.vetmais.Model.DAO;
    opens org.example.vetmais.Model.DAO to javafx.fxml;
    exports org.example.vetmais.Model.Database;
    opens org.example.vetmais.Model.Database to javafx.fxml;
}