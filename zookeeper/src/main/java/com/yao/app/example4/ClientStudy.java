package com.yao.app.example4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientStudy {

    private static final Logger log = LoggerFactory
            .getLogger(ClientStudy.class);

    public static final int DEFAULT_SESSION_TIMEOUT = 3000;

    public static void main(String[] args) {
        testCreate();
        testAccess();
    }

    public static void testCreate() {
        log.info("test create znode...");
        ZooKeeper zk = null;
        try {
            final CountDownLatch latch = new CountDownLatch(1);

            // session的建立是异步的
            zk = new ZooKeeper("127.0.0.1:2181", DEFAULT_SESSION_TIMEOUT,
                    new Watcher() {
                        @Override
                        public void process(WatchedEvent event) {
                            KeeperState st = event.getState();
                            EventType et = event.getType();
                            System.out.println(st.toString() + " / "
                                    + et.toString() + "/" + event.getPath());
                            if (st == KeeperState.SyncConnected) {
                                log.info("client connected...");
                                latch.countDown();
                            }
                        }
                    });
            // 等着zookeeper连接上
            log.info("waiting for client connected...");
            latch.await();

            List<ACL> acl = new ArrayList<ACL>();
            Id id1 = new Id("digest",
                    DigestAuthenticationProvider
                            .generateDigest("yaolei:abc123"));
            ACL acl1 = new ACL(ZooDefs.Perms.ALL, id1);

            ACL acl2 = new ACL(ZooDefs.Perms.READ, Ids.ANYONE_ID_UNSAFE);
            
            // Ids.OPEN_ACL_UNSAFE

            acl.add(acl1);
            acl.add(acl2);

            String data = "woodypecker";

            String path = zk.create("/study", data.getBytes(), acl,
                    CreateMode.PERSISTENT);

            log.info("write znode " + path + " with data : " + data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zk != null) {
                try {
                    zk.close();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static void testAccess() {
        ZooKeeper zk = null;
        // 使用world angone授权
        log.info("test modify znode use world...");
        try {
            final CountDownLatch latch = new CountDownLatch(1);

            // session的建立是异步的
            zk = new ZooKeeper("127.0.0.1:2181", DEFAULT_SESSION_TIMEOUT,
                    new Watcher() {
                        @Override
                        public void process(WatchedEvent event) {
                            if (event.getState() == KeeperState.SyncConnected) {
                                log.info("client connected...");
                                latch.countDown();
                            }
                        }
                    });
            // 等着zookeeper连接上
            log.info("waiting for client connected...");
            latch.await();

            // zk.addAuthInfo("word", "anyone".getBytes());
            byte[] data = zk.getData("/study", true, null);
            String result = new String(data);

            log.info("read znode /study 's data is : " + result);

            // 应该没有权限
            Stat st = zk.setData("/study", "test".getBytes(), -1);
            log.info("set data result :"+st.toString());
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zk != null) {
                try {
                    zk.close();
                } catch (InterruptedException e) {
                }
            }
        }

        // 使用digest授权
        log.info("test modify znode use digest...");
        try {
            final CountDownLatch latch = new CountDownLatch(1);

            // session的建立是异步的
            zk = new ZooKeeper("127.0.0.1:2181", DEFAULT_SESSION_TIMEOUT,
                    new Watcher() {
                        @Override
                        public void process(WatchedEvent event) {
                            if (event.getState() == KeeperState.SyncConnected) {
                                log.info("client connected...");
                                latch.countDown();
                            }
                        }
                    });
            // 等着zookeeper连接上
            log.info("waiting for client connected...");
            latch.await();

            zk.addAuthInfo("digest", "yaolei:abc123".getBytes());
            byte[] data = zk.getData("/study", true, null);
            String result = new String(data);
            log.info("read znode /study 's data is : " + result);

            Stat st = zk.setData("/study", "test123".getBytes(), -1);
            log.info("set data result :"+st.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zk != null) {
                try {
                    zk.close();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
