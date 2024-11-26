package org.example.vetmais.Model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseMySQL implements Database {
    private Connection connection;

    @Override
    public Connection getConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "");
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
