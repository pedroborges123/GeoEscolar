package br.es.geo.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DAOGeneric {

    private Connection con;

    protected void openConnection() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/geoescolar?zeroDateTimeBehavior=convertToNull";
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "ifes");
        con = DriverManager.getConnection(url, properties);
    }

    protected void execute(String query) throws SQLException {
        System.out.println(query);
        Statement statement = con.createStatement();
        // Comando para criar
        statement.execute(query);

        statement.close();

    }

    protected ResultSet executeQuery(String query) throws SQLException {
        Statement statement = con.createStatement();
        System.out.println(query);
        ResultSet rs = statement.executeQuery(query);

        return rs;
    }

    protected int executeUpdate(String query) throws SQLException {
        System.out.println(query);
        Statement statement = con.createStatement();

        int numero = 0;
        // Comando para update, insert e delete		
        statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = statement.getGeneratedKeys();

        if (rs.next()) {
            numero = rs.getInt(1);
        }

        statement.close();

        return numero;
    }

    protected void closeConnection() throws SQLException {
        con.close();
    }

}
