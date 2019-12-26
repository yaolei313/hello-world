package com.yao.app.database.jdbc;

/**
 * Created by yaolei02 on 2017/2/23.
 */
public interface UserMapper {

    //private static final String SELECT_SQL = "select * from users where id=?";

    UserBean findUser(String id);

    int insert(UserBean user);
}
