package com.airportsim.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static ConnectionManager instance;
    private final String dbUrl;
    private final String user;
    private final String password;

    private ConnectionManager() {
        this.dbUrl = "jdbc:mysql://localhost:3306/simuairport?useSSL=false&serverTimezone=UTC";
        this.user = "root";
        this.password = "deinpasswort";
    }

    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Aufbau der DB-Verbindung", e);
        }
    }
}
