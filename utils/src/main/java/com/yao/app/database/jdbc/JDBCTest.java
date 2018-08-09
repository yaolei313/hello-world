package com.yao.app.database.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCTest {

    protected static final Logger log = LoggerFactory.getLogger(JDBCTest.class);

    private static final String INSERT_SQL = "insert into users(id,name,email) values (?,?,?)";

    public static void main(String[] args) {
        try {
            log.warn("测试logback配置");
            
            log.info("测试normal");
            // test();

            log.info("测试savepoint");
            testSavepoint();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void test() throws ClassNotFoundException, SQLException {
        Connection conn = getDefaultConnection();

        try {
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(INSERT_SQL);

            ps.setString(1, "d00196910");
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

    public static void testSavepoint() throws ClassNotFoundException, SQLException {
        Connection conn = getDefaultConnection();
        Savepoint savepoint = null;
        try {
            log.debug("开始事务");
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(INSERT_SQL);

            ps.setString(1, "l00196910");
            ps.setString(2, "libai");
            ps.setString(3, "libai@gmail.com");

            ps.executeUpdate();

            log.debug("设置savepoint");
            savepoint = conn.setSavepoint("savepoint1");

            ps.setString(1, "y00196907");
            ps.setString(2, "yaolei");
            ps.setString(3, "yaolei313@gmail.com");

            ps.executeUpdate();

            log.debug("提交事务");
            conn.commit();
        } catch (SQLException e) {
            log.debug("出现错误", e);
            try {
                if (savepoint != null) {
                    log.debug("回滚savepoint");
                    conn.rollback(savepoint);
                    log.debug("部分提交");
                    conn.commit();
                } else {
                    log.debug("回滚全部");
                    conn.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static Connection getDefaultConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.189.28:3306/study", "study",
                "study");

        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        return conn;
    }
}
