package com.yao.app.json.object;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Group implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Map<String, User> users = new HashMap<String, User>();

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

}
