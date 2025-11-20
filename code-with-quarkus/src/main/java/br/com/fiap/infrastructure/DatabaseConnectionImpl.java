package br.com.fiap.infrastructure;

import jakarta.enterprise.context.ApplicationScoped;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


@ApplicationScoped
public class DatabaseConnectionImpl implements DatabaseConnection{

    private final DataSource dataSource;

    public DatabaseConnectionImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

}
