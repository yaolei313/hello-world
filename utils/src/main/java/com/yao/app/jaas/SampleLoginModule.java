package com.yao.app.jaas;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author lei.yao
 *
 */
public class SampleLoginModule implements LoginModule {

	protected final Logger log = LoggerFactory
			.getLogger(SampleLoginModule.class);

	// configurable options
	private boolean debug = false;

	// initial state
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String, Object> sharedState;
	private Map<String, ?> options;

	// the user
	private SamplePrincipal user;

	// Authentication status
	private boolean succeeded = false;
	private boolean commitSucceeded = false;

	@Override
	@SuppressWarnings("unchecked")
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = (Map<String, Object>) sharedState;
		this.options = options;

		this.debug = "true".equalsIgnoreCase((String) this.options.get("debug"));
	}

	@Override
	public boolean login() throws LoginException {
		try {
			NameCallback nameCallback = new NameCallback("username");
			PasswordCallback passwordCallback = new PasswordCallback(
					"password", false);
			final Callback[] calls = new Callback[] { nameCallback,
					passwordCallback };
			// 获取用户数据

			callbackHandler.handle(calls);
			String username = nameCallback.getName();
			String password = String.valueOf(passwordCallback.getPassword());
			// TODO 验证，如：查询数据库、LDAP。。。
			if (username.equals("summer") && password.equals("123456")) {
				log.debug("login",
						"Authentication using cached password has succeeded");
			} else {
				throw new LoginException("user or password is wrong");
			}
			user = new SamplePrincipal(username);
			succeeded = true;
		} catch (IOException e) {
			throw new LoginException("no such user");
		} catch (UnsupportedCallbackException e) {
			throw new LoginException("login failure");
		}
		return succeeded;
	}

	@Override
	public boolean commit() throws LoginException {
		if (succeeded == false) {
			return false;
		} else {
			if (subject.isReadOnly()) {
				throw new LoginException("Subject is read-only");
			}
			// add Principals to the Subject
			if (!subject.getPrincipals().contains(user)) {
				subject.getPrincipals().add(user);
			}

			log.debug("commit", "Authentication has completed successfully");
		}
		// in any case, clean out state
		commitSucceeded = true;
		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		log.debug("abort", "Authentication has not completed successfully");

		if (succeeded == false) {
			return false;
		} else if (succeeded == true && commitSucceeded == false) {

			// Clean out state
			succeeded = false;
			user = null;
		} else {
			// overall authentication succeeded and commit succeeded,
			// but someone else's commit failed
			logout();
		}
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		if (subject.isReadOnly()) {
			throw new LoginException("Subject is read-only");
		}
		subject.getPrincipals().remove(user);

		// clean out state
		succeeded = false;
		commitSucceeded = false;
		user = null;

		log.debug("logout", "Subject is being logged out");

		return true;
	}

}
