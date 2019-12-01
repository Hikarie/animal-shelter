package com.animal.conn;

import java.sql.*;

public class dbConnector {

    private ResultSet rs = null;
    private Statement stmt = null;
    private Connection conn = null;

    public void connect() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }     //new oracle.jdbc.driver.OracleDriver();
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "SYSTEM", "yojiro8575");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(conn);
    }

    public Connection getConn(){
        return conn;
    }
}
