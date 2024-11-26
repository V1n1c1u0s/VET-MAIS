package org.example.vetmais.Model.Database;

import java.sql.Connection;

public interface Database {
    public Connection getConnection();
    public void disconnect(Connection conn);
}
