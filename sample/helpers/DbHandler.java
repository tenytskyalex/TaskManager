package sample.helpers;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class DbHandler {
    // Константа, в которой хранится адрес подключения
    private static final String CON_STR = "jdbc:sqlite:task.db";

    private static DbHandler instance = null;

    public static synchronized Connection getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance.connection;
    }

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }
}
