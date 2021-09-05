package com.yao.app.zk.example2;

import static org.apache.zookeeper.CreateMode.EPHEMERAL;
import static org.apache.zookeeper.CreateMode.PERSISTENT;

import java.lang.management.ManagementFactory;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Barrier implements Watcher {
	private static final Logger LOG = LoggerFactory.getLogger(Barrier.class);
	private static final String PARTICIPANTS = "/participants";
	private static final byte[] NO_DATA = new byte[0];

	private Object sharedMutex = new Object();
	private Integer participantCount;
	private ZooKeeper zooKeeper;

	public Barrier(String host, Integer participantCount) throws Exception {
		this.participantCount = participantCount;
		this.zooKeeper = new ZooKeeper(host, 3000, this);

		createWhenThereIsNoNode(PARTICIPANTS, PERSISTENT);
	}

	public boolean ensureAllParticipantsAreReady() throws KeeperException,
			InterruptedException {
		String name = getName();
		createWhenThereIsNoNode(PARTICIPANTS + "/" + name, EPHEMERAL);
		LOG.info("Created PID : " + name);

		while (true) {
			synchronized (sharedMutex) {
				List<String> children = zooKeeper.getChildren(PARTICIPANTS,
						true);
				if (children.size() == participantCount) {
					return true;
				} else {
					waitUntilThereIsEventNotificationFromZooKeeper();
				}
			}
		}
	}

	public boolean leave() throws KeeperException, InterruptedException {
		final int anyVersion = -1;
		String name = getName();
		zooKeeper.delete(PARTICIPANTS + "/" + name, anyVersion);
		LOG.info("Removed PID : " + name);
		while (true) {
			synchronized (sharedMutex) {
				List<String> children = zooKeeper.getChildren(PARTICIPANTS,
						true);
				if (children.isEmpty()) {
					zooKeeper.close();
					return true;
				} else {
					waitUntilThereIsEventNotificationFromZooKeeper();
				}
			}
		}
	}

	private void createWhenThereIsNoNode(String path, CreateMode createMode)
			throws KeeperException, InterruptedException {
		if (zooKeeper.exists(path, false) == null) {
			zooKeeper.create(path, NO_DATA, Ids.OPEN_ACL_UNSAFE, createMode);
		}
	}

	@Override
	public void process(WatchedEvent event) {
		wakeUpWatingThread();
	}

	private void wakeUpWatingThread() {
		synchronized (sharedMutex) {
			sharedMutex.notify();
		}
	}

	private void waitUntilThereIsEventNotificationFromZooKeeper()
			throws InterruptedException {
		sharedMutex.wait();
	}
	
	private String getName() {
		return ManagementFactory.getRuntimeMXBean().getName()
				+ Thread.currentThread().getId();
	}
}