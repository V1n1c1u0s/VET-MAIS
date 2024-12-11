package org.example.vetmais.Model.DAO;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import org.example.vetmais.Domain.Animal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOAnimal {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean cadastrar(Animal animal) throws Exception {
        String sql = "INSERT INTO Animals (name, birth_date, breed, cpf_proprietario) VALUES (?,?,?,?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, animal.getName());
            stmt.setDate(2, Date.valueOf(animal.getBirth_date()));
            stmt.setString(3, animal.getBreed());
            stmt.setString(4, animal.getCpf_proprietario());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAOAnimal.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean atualizar(Animal animal) throws Exception {
        String sql = "UPDATE Animals SET name = ?, birth_date = ?, breed = ? WHERE cpf_proprietario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, animal.getName());
            stmt.setDate(2, Date.valueOf(animal.getBirth_date()));
            stmt.setString(3, animal.getBreed());
            stmt.setString(4, animal.getCpf_proprietario());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true se a atualização foi bem-sucedida
        } catch (SQLException ex) {
            Logger.getLogger(DAOAnimal.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deletar(Animal animal) throws Exception {
        String sql = "DELETE FROM Animals WHERE cpf_proprietario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, animal.getCpf_proprietario());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true se a exclusão foi bem-sucedida
        } catch (SQLException ex) {
            Logger.getLogger(DAOAnimal.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean buscar(Animal animal) throws Exception {
        String sql = "SELECT * FROM Animals WHERE cpf_proprietario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, animal.getCpf_proprietario());
            var resultSet = stmt.executeQuery();

            return resultSet.next(); // Retorna true se o cliente for encontrado
        } catch (SQLException ex) {
            Logger.getLogger(DAOAnimal.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
