package com.yao.app.java.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;



public class MemoryConstrainedCache {
	private ConcurrentHashMap<String, KeySoftReference<String,User>> map;

	private ReferenceQueue<User> queue;

	public MemoryConstrainedCache() {
		super();

		map = new ConcurrentHashMap<String, KeySoftReference<String,User>>();
		queue = new ReferenceQueue<User>();
	}

	public void set(String key, User value){
		processQueue();

		map.put(key, new KeySoftReference<String,User>(key,value,queue));
	}

	public User get(String key){
		processQueue();

		User result = null;
		KeySoftReference<String, User> softReference = map.get(key);
		if(softReference != null){
			result = softReference.get();
			if(result == null){
				map.remove(key);
			}
		}

		return result;
	}

	public User remove(String key){
		processQueue();

		User result = null;
		KeySoftReference<String, User> softReference = map.remove(key);
		if(softReference != null){
			result = softReference.get();
		}

		return result;
	}

	public void clear(){
		processQueue();
		map.clear();
	}

	@SuppressWarnings("rawtypes")
	private void processQueue() {
		KeySoftReference sv;
		while ((sv = (KeySoftReference) queue.poll()) != null) {
			//noinspection SuspiciousMethodCalls
			map.remove(sv.key); // we can access private data!
		}
	}


	private final class KeySoftReference<K,V> extends SoftReference<V>{

		private K key;

		public KeySoftReference(K key, V referent, ReferenceQueue<? super V> q) {
			super(referent, q);
			this.key = key;
		}

	}
}
