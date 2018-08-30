package com.haulmont.db.classes;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbCreate {

    private String dbUrl = "jdbc:hsqldb:file:/src/main/java/com/haulmont/db/";
    private String user = "SA";
    private String password = "";
    private Connection connection;

    public DbCreate() {

        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection(dbUrl, user, password);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean executeScript(String filename) {
        String script;

        try {
            FileInputStream fstream = new FileInputStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            while((strLine = br.readLine()) !=null) {
                script = strLine;
                System.out.println(script);

                PreparedStatement ps = connection.prepareStatement(script);

                ps.executeUpdate();

                connection.commit();
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
