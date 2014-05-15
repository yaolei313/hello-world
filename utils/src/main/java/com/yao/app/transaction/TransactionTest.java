package com.yao.app.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionTest {

    private static final String INSERT_SQL = "insert into users(id,name,email) values (?,?,?)";
    // private static final String SELECT_SQL = "select * from users where id=?";

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath*:transaction/application.xml");
        PlatformTransactionManager txManager = context.getBean("transactionManager", PlatformTransactionManager.class);
        DataSource ds = context.getBean("dataSource", DataSource.class);

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus ts = txManager.getTransaction(definition);
        Connection conn = DataSourceUtils.getConnection(ds);

        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_SQL);

            ps.setString(1, "y00196909");
            ps.setString(2, "libai");
            ps.setString(3, "libai@gmail.com");

            int count = ps.executeUpdate();
            System.out.println("count1:" + count);

            ps.setString(1, "y00196907");
            ps.setString(2, "yaolei");
            ps.setString(3, "yaolei313@gmail.com");

            count = ps.executeUpdate();
            System.out.println("count2:" + count);

            txManager.commit(ts);
        } catch (SQLException e) {
            e.printStackTrace();
            ts.setRollbackOnly();
            txManager.rollback(ts);
        }
        DataSourceUtils.releaseConnection(conn, ds);

        context.close();
    }
}
