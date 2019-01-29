package com.yao.app.database.jdbc;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

/**
 * SqlSessionFactoryBuilder
 * <p>
 * 一旦创建了 SqlSessionFactory，就不再需要了。因此 SqlSessionFactoryBuilder 实例的作用域是方法作用域，即启动过程中初始化完成SqlSessionFactory后
 * 其生命周期结束。
 * 可以使用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，对应使用场景为多数据源时。
 * <p>
 * SqlSessionFactory
 * <p>
 * 一旦被创建就应该在应用的运行期间一直存在，创建之后一般没有理由修改，除非动态添加Mapper，但是这个可以属于初始化阶段。
 * 因此 SqlSessionFactory 的作用域是应用作用域。
 * <p>
 * SqlSession
 * <p>
 * 每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的。
 *
 * <p>
 * <p>
 * Created by yaolei02 on 2017/2/23.
 */
public class MybatisTest {
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("classpath*:database/application.xml");
        DataSource dateSource = context.getBean("dataSource", DataSource.class);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment = new Environment("env1", transactionFactory, dateSource);
        Configuration config = new Configuration(environment);

        config.getTypeAliasRegistry().registerAlias("UserBean", UserBean.class);

        // 注册方式1，若不注册别名UserBean，parse就会报错
        // resource路径和mapper不一致的情况可以使用此种
        if (!config.isResourceLoaded(UserMapper.class.toString())) {
            Resource mapperLocation = new ClassPathResource("database/jdbc/UserMapper.xml");
            try {
                XMLMapperBuilder xmlMapperBuilder =
                    new XMLMapperBuilder(mapperLocation.getInputStream(), config, mapperLocation.toString(),
                        config.getSqlFragments());
                xmlMapperBuilder.parse();
            } catch (Exception e) {
                System.out.println("Failed to parse mapping resource: '" + mapperLocation + "'");
            }
        }

        // 注册方式2
        config.addMapper(PostMapper.class);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
        // 默认openSession行为
        // 会开启一个事务(也就是不自动提交) autoCommit=false
        // 连接对象会从由活动环境配置的数据源实例中得到。
        // 事务隔离级别将会使用驱动或数据源的默认设置。
        // 预处理语句不会被复用,也不会批量处理更新。
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserBean user = sqlSession.selectOne("com.yao.app.database.jdbc.UserMapper.findUser", "y00196907");
            System.out.print(user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession2.getMapper(UserMapper.class);
            UserBean user = userMapper.findUser("y00196907");
            System.out.println(user);
            // 验证一级缓存
            user = userMapper.findUser("y00196907");
            System.out.println(user);
        } finally {
            sqlSession2.close();
        }

        SqlSession sqlSession3 = sqlSessionFactory.openSession();
        try {
            PostMapper postMapper = sqlSession3.getMapper(PostMapper.class);
            String title = postMapper.findPostTitle(1L);
            System.out.print(title);
        } finally {
            sqlSession3.close();
        }

    }
}
