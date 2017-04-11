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
 * 这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法变量）。
 * 你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，但是最好还是不要让其一直存在以保证所有的 XML 解析资源开放给更重要的事情。
 * <p>
 * SqlSessionFactory
 * <p>
 * 一旦被创建就应该在应用的运行期间一直存在，没有任何理由对它进行清除或重建。
 * 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏味道（bad smell）”。
 * 因此 SqlSessionFactory 的最佳作用域是应用作用域。有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。
 * <p>
 * SqlSession
 * <p>
 * 每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。
 * 绝对不能将 SqlSession 实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。也绝不能将 SqlSession 实例的引用放在任何类型的管理作用域中，
 * 比如 Serlvet 架构中的 HttpSession。如果你现在正在使用一种 Web 框架，要考虑 SqlSession 放在一个和 HTTP 请求对象相似的作用域中。
 * 换句话说，每次收到的 HTTP 请求，就可以打开一个 SqlSession，返回一个响应，就关闭它。这个关闭操作是很重要的，你应该把这个关闭操作放到 finally 块中以确保每次都能执行关闭。
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

        // 若不注册别名UserBean，parse就会报错
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
            System.out.print(user);
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
