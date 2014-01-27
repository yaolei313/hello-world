package com.yao.app.example4;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class ClientStudy {

    public static final int DEFAULT_SESSION_TIMEOUT = 3000;

    public static void main(String[] args) {
        try {
            ZooKeeper zk = new ZooKeeper("127.0.0.1:2181",
                    DEFAULT_SESSION_TIMEOUT, new Watcher() {
                        @Override
                        public void process(WatchedEvent event) {
                            KeeperState st = event.getState();
                            EventType et = event.getType();
                            System.out.println(st.toString() + " / "
                                    + et.toString() + "/" + event.getPath());
                        }
                    });
            
            List<ACL> acl = new ArrayList<ACL>();
            Id id1 = new Id("digest",DigestAuthenticationProvider.generateDigest("yaolei:abc123"));
            ACL acl1 = new ACL(ZooDefs.Perms.ALL,id1);
            
            Id id2 = new Id("world","anyone");
            ACL acl2 = new ACL(ZooDefs.Perms.READ,id2);
            
            acl.add(acl1);
            acl.add(acl2);
            zk.create(path, data, acl, createMode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
