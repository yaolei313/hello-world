package com.yao.app.nebula.util;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.AbstractFactory;

import com.stormpath.sdk.api.ApiKey;
import com.stormpath.sdk.api.ApiKeys;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.shiro.cache.ShiroCacheManager;

public class StormpathClientFactory extends AbstractFactory<Client> {

    private String id;
    
    private String secret;
    
    private com.stormpath.sdk.cache.CacheManager cacheManager;
    
    public void setId(String id) {
        this.id = id;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = new ShiroCacheManager(cacheManager);
    }
    
    @Override
    protected Client createInstance() {
        ApiKey apiKey = ApiKeys.builder().setId(id).setSecret(secret).build();
        Client client = Clients.builder().setApiKey(apiKey).setCacheManager(cacheManager).build();
        return client;
    }

}
