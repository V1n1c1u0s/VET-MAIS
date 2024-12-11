package org.example.vetmais.Model.DAO;

import org.example.vetmais.Domain.Animal;
import org.example.vetmais.Domain.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOUser {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean cadastrar(User user) throws Exception {
        String sql = "INSERT INTO Users (email, password) VALUES (?,?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean buscar(User user) throws Exception {
        if(user.getEmail().isEmpty() || user.getPassword().isEmpty())
            return false;
        String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            var resultSet = stmt.executeQuery();

            return resultSet.next(); // Retorna true se o cliente for encontrado
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
