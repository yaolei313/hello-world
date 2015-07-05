package com.yao.app.jaas;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 * http://tech.it168.com/j/2008-02-02/200802020103734.shtml
 * 
 * @author Yao
 *
 */
public class SampleLoginModule implements LoginModule {

	private boolean isAuthenticated = false;
	private CallbackHandler callbackHandler;
	private Subject subject;
	private SamplePrincipal principal;
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean login() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean commit() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean abort() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean logout() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

}
