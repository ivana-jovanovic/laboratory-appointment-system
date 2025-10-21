/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domain.AbstractDomainObject;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author Ivana
 */
public class DBBroker {

    private static DBBroker instance;
    private Connection connection;

    private DBBroker() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("dbconfig.properties"));
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBBroker getInstance() {
        if (instance == null) {
            instance = new DBBroker();
        }
        return instance;
    }

    public ArrayList<AbstractDomainObject> select(AbstractDomainObject ado) throws SQLException {
        String upit = "SELECT * FROM " + ado.nazivTabele() + " " + ado.alijas()
                + " " + ado.join() + " " + ado.uslovZaSelect();
        System.out.println(upit + "\n");
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(upit);
        return ado.vratiListu(rs);
    }

    public PreparedStatement insert(AbstractDomainObject ado) throws SQLException {
        String naredba = "INSERT INTO " + ado.nazivTabele() + " "
                + ado.koloneZaInsert() + " VALUES(" + ado.vrednostiZaInsert() + ")";
        System.out.println(naredba + "\n");
        PreparedStatement ps = connection.prepareStatement(naredba, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
        return ps;
    }

    public void update(AbstractDomainObject ado) throws SQLException {
        String naredba = "UPDATE " + ado.nazivTabele() + " SET "
                + ado.vrednostiZaUpdate() + " WHERE " + ado.uslov();
        System.out.println(naredba + "\n");
        Statement s = connection.createStatement();
        s.executeUpdate(naredba);
    }

    public void delete(AbstractDomainObject ado) throws SQLException {
        String naredba = "DELETE FROM " + ado.nazivTabele() + " WHERE " + ado.uslov();
        System.out.println(naredba + "\n");
        Statement s = connection.createStatement();
        s.executeUpdate(naredba);
    }

}
