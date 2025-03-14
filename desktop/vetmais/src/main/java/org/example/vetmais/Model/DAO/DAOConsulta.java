package org.example.vetmais.Model.DAO;

import org.example.vetmais.Domain.Consulta;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOConsulta {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean cadastrar(Consulta consulta) throws Exception {
        String sql = "INSERT INTO Consultas (veterinario, data_agendada, pet, proprietario) VALUES (?,?,?,?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, consulta.getNamevet());
            stmt.setDate(2, Date.valueOf(consulta.getData_agendada()));
            stmt.setString(3, consulta.getPet());
            stmt.setString(4, consulta.getCpf_proprietario());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAOConsulta.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean atualizar(Consulta consulta) {
        String sql = "UPDATE Consultas SET veterinario = ?, data_agendada = ?, pet = ? WHERE proprietario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, consulta.getNamevet());
            stmt.setDate(2, Date.valueOf(consulta.getData_agendada()));
            stmt.setString(3, consulta.getPet());
            stmt.setString(4, consulta.getCpf_proprietario());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true se a atualização foi bem-sucedida
        } catch (SQLException ex) {
            Logger.getLogger(DAOAnimal.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deletar(Consulta consulta) throws Exception {
        return false;
    }

    public boolean getALL(Consulta consulta) throws Exception {
        return false;
    }

}
