package com.yao.app.raft;

import com.ecwid.consul.v1.ConsulClient;
import com.yao.app.raft.ServiceRegistry;
import java.net.URL;
import java.util.List;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-03-02
 */
public class ConsulServiceRegistry implements ServiceRegistry {

    private ConsulClient client;

    @Override
    public void register(URL url) {

    }

    @Override
    public void unregister(URL url) {

    }

    @Override
    public List<URL> lookup(URL conditionUrl) {
        return null;
    }
}
