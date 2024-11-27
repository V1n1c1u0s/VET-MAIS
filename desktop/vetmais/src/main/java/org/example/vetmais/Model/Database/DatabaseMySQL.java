package org.example.vetmais.Model.Database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseMySQL implements Database {
    private Connection connection;

    private final Dotenv dotenv = Dotenv.load();

    @Override
    public Connection getConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(dotenv.get("MYSQL_DB_URL"),dotenv.get( "MYSQL_DB_USER"),dotenv.get("MYSQL_DB_PASSWD"));
            return this.connection;
        } catch(SQLException | ClassNotFoundException e){
            Logger.getLogger(DatabaseMySQL.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }


    @Override
    public void disconnect(Connection conn) {
        try{
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
