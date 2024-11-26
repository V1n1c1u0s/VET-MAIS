package org.example.vetmais.Model.Database;

public class DatabaseFactory {

    // Método que cria uma instância de Database baseado no tipo de banco de dados
    public static Database getDatabase(String dbType) {
        switch (dbType.toLowerCase()) {
            case "mysql":
                return new DatabaseMySQL();
            case "postgresql":
                // return new DatabasePostgreSQL();
                break;
            case "sqlite":
                // return new DatabaseSQLite();
                break;
            default:
                throw new IllegalArgumentException("Tipo de banco de dados não suportado: " + dbType);
        }
        return null;
    }
}
