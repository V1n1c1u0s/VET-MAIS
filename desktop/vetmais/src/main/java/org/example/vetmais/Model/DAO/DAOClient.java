package org.example.vetmais.Model.DAO;

import org.example.vetmais.Domain.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOClient {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean cadastrar(Client client)  throws Exception {
        String sql = "INSERT INTO clients (name, cpf, email, telephone) VALUES (?,?,?,?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getCpf());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getTelephone());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAOClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean atualizar(Client client) throws Exception {
        String sql = "UPDATE clients SET name = ?, email = ?, telephone = ? WHERE cpf = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getTelephone());
            stmt.setString(4, client.getCpf());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true se a atualização foi bem-sucedida
        } catch (SQLException ex) {
            Logger.getLogger(DAOClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deletar(Client client) throws Exception {
        String sql = "DELETE FROM clients WHERE cpf = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, client.getCpf());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true se a exclusão foi bem-sucedida
        } catch (SQLException ex) {
            Logger.getLogger(DAOClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean buscar(Client client) throws Exception {
        String sql = "SELECT * FROM clients WHERE cpf = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, client.getCpf());
            var resultSet = stmt.executeQuery();

            return resultSet.next(); // Retorna true se o cliente for encontrado
        } catch (SQLException ex) {
            Logger.getLogger(DAOClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
