package com.yao.app.jaas;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class LoginManager {

    public static boolean login(String username, String password) {
        try {
            // 此处指定了使用配置文件的“Sample”验证模块，对应的实现类为SampleLoginModule
            System.setProperty("java.security.auth.login.config",
                    "D:/GIT_REPO/hello-world/utils/src/main/resources/jaas/jaas.conf");

            LoginContext lc = new LoginContext("Sample", new UsernamePasswordCallbackHandler(username, password));
            lc.login();// 如果验证失败会抛出异常

            return true;
        } catch (LoginException e) {
            e.printStackTrace();
            return false;
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        boolean r1 = LoginManager.login("summer", "");
        System.out.println(r1);
        boolean r2 = LoginManager.login("summer", "123456");
        System.out.println(r2);
    }

    public static class UsernamePasswordCallbackHandler implements CallbackHandler {

        private String username;
        
        private String password;
        
        public UsernamePasswordCallbackHandler(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
            for (Callback callback : callbacks) {
                if (callback instanceof NameCallback) {
                    NameCallback nc = (NameCallback) callback;
                    nc.setName(username);

                } else if (callback instanceof PasswordCallback) {
                    PasswordCallback pc = (PasswordCallback) callback;
                    pc.setPassword(password.toCharArray());

                } else {
                    throw new UnsupportedCallbackException(callback, "Unrecognized Callback");
                }
            }
        }

    }

}
