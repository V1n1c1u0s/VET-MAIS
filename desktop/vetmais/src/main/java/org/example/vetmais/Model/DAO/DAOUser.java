package org.example.vetmais.Model.DAO;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.example.vetmais.Domain.User;
import org.example.vetmais.Model.Session.SessionManager;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;
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
        String sql = "INSERT INTO users (email, password, token, expiration, nome, cpf) VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, UUID.randomUUID().toString());
            stmt.setObject(4, LocalDateTime.now().plusDays(10));
            stmt.setString(5, user.getName());
            stmt.setString(6, user.getCPF());

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
                if(verifyPassword(user.getPassword(), resultSet.getString("password"))){
                    LocalDateTime newExpiration = LocalDateTime.now().plusDays(10);
                    //LocalDateTime newExpiration = LocalDateTime.now().plusMinutes(2);
                    //LocalDateTime newExpiration = LocalDateTime.now().plusHours(1);
                    String token = resultSet.getString("token");
                    atualizarExpiracaoToken(token, newExpiration);
                    return true;
                }

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

    public boolean isTokenValid(String token) {

        String sql = "SELECT * FROM users WHERE token = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()) {
                //Date exp = resultSet.getDate("expiration");
                LocalDateTime expiration = resultSet.getObject("expiration", java.time.LocalDateTime.class);
                LocalDateTime now = LocalDateTime.now();

                return !expiration.isBefore(now);
                //return resultSet.getString("token").equals(token);
            }

            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void atualizarExpiracaoToken(String token, LocalDateTime expiration) throws SQLException, IOException {
        String updateSql = "UPDATE users SET expiration = ? WHERE token = ?";

        try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
            updateStmt.setObject(1, expiration);
            updateStmt.setString(2, token);
            updateStmt.executeUpdate();
            //System.out.println("Data de expiração do token atualizada com sucesso!");
        }

        SessionManager.clearSession();
        SessionManager.saveSession(token);
    }

}
