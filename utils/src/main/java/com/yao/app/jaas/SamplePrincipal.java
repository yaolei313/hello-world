package com.yao.app.jaas;

import java.security.Principal;

public class SamplePrincipal implements Principal {

    private String name;
    
	public SamplePrincipal(String name) {
        super();
        this.name = name;
    }

    @Override
	public String getName() {
		return name;
	}

}
