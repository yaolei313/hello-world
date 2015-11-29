package com.yao.app.nebula.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThreadLocalHolder {
	private static InheritableThreadLocal<Map<String, String>> inheritableThreadLocal = new InheritableThreadLocal<Map<String, String>>();

	public static void put(String key, String val) {
		if (key == null) {
			throw new IllegalArgumentException("key cannot be null");
		}
		Map<String, String> map = (Map<String, String>) inheritableThreadLocal.get();
		if (map == null) {
			map = Collections.<String, String> synchronizedMap(new HashMap<String, String>());
			inheritableThreadLocal.set(map);
		}
		map.put(key, val);
	}

	public static String get(String key) {
		Map<String, String> Map = (Map<String, String>) inheritableThreadLocal.get();
		if ((Map != null) && (key != null)) {
			return (String) Map.get(key);
		} else {
			return null;
		}
	}

	public static void remove(String key) {
		Map<String, String> map = (Map<String, String>) inheritableThreadLocal.get();
		if (map != null) {
			map.remove(key);
		}
	}

	public static void clear() {
		Map<String, String> map = (Map<String, String>) inheritableThreadLocal.get();
		if (map != null) {
			map.clear();
		}
		inheritableThreadLocal.remove();
	}
}
