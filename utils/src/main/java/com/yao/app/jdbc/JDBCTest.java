package com.yao.app.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCTest {

    private static final String INSERT_SQL = "insert into users(id,name,email) values (?,?,?)";

    public static void main(String[] args) throws Exception {
        Connection conn = getDefaultConnection();

        try {
            conn.setAutoCommit(false);
            
            PreparedStatement ps = conn.prepareStatement(INSERT_SQL);

            ps.setString(1, "y00196910");
            ps.setString(2, "dufu");
            ps.setString(3, "dufu@gmail.com");

            ps.executeUpdate();

            ps.setString(1, "y00196907");
            ps.setString(2, "yaolei");
            ps.setString(3, "yaolei313@gmail.com");

            ps.executeUpdate();
            
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }

    }

    public static Connection getDefaultConnection() throws ClassNotFoundException, SQLException {
        Class.forName("net.sf.log4jdbc.DriverSpy");
        Connection conn = DriverManager.getConnection("jdbc:log4jdbc:mysql://192.168.189.28:3306/study", "study",
                "study");

        return conn;
    }
}
