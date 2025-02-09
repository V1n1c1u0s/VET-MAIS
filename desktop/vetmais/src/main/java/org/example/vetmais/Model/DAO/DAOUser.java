package org.example.vetmais.Model.DAO;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.example.vetmais.Domain.User;

import java.sql.*;
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
        if(user.getEmail().isEmpty() || user.getPassword().isEmpty() || !user.isValidEmail()  || !user.isValidCPF()) {
            return false;
        }
        String sql = "INSERT INTO users (email, password) VALUES (?,?)";
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
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next())
                return verifyPassword(user.getPassword(), resultSet.getString("password"));

            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    private boolean verifyPassword(String password, String hash) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.verify(hash, password.toCharArray());
    }
}
