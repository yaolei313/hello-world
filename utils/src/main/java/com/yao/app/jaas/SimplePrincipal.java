package com.yao.app.jaas;

import java.security.Principal;

public class SimplePrincipal implements Principal {

    private String name;
    
	public SimplePrincipal(String name) {
        super();
        this.name = name;
    }

    @Override
	public String getName() {
		return name;
	}

}
