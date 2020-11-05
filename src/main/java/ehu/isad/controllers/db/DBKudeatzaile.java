package ehu.isad.controllers.db;


import ehu.isad.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;

public class DBKudeatzaile {
    Connection conn = null;

    private void conOpen() {

        Properties properties = Utils.lortuEzarpenak();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", properties);
            conn.setCatalog(properties.getProperty("dbname"));

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private ResultSet query(Statement s, String query) {

        ResultSet rs = null;

        try {
            s.executeQuery(query);
            rs = s.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    // singleton patroia
    private static DBKudeatzaile instantzia = new DBKudeatzaile();

    private DBKudeatzaile() {
        this.conOpen();
    }

    public static DBKudeatzaile getInstantzia() {
        return instantzia;
    }

    public ResultSet execSQL(String query) {
        int count = 0;
        Statement s = null;
        ResultSet rs = null;
        try {
            s = (Statement) conn.createStatement();
            if (query.toLowerCase().indexOf("select") == 0) {
                // select agindu bat
                rs = this.query(s, query);
            } else {
                // update, delete, create agindu bat
                count = s.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
