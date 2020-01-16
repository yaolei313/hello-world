package com.yao.app.java.trove;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import java.util.HashMap;
import java.util.Map;
import org.openjdk.jol.info.GraphLayout;

/**
 * trove4j减少了primitive对象包装后增加的空间
 *
 * Created by yaolei02 on 2019/1/21.
 */
public class TroveStudy {
    public static void main(String[] args) {

        Map<Integer, Object> map1 = new HashMap<>();

        TIntObjectMap<Object> map2 = new TIntObjectHashMap<>();

        for (int i = 0; i < 100; i++) {
            TestObject t = new TestObject(i, "name" + i);
            map1.put(t.id, t);
            map2.put(t.id, t);
        }

        // 13088个字节
        GraphLayout layout1 = GraphLayout.parseInstance(map1);
        System.out.println(layout1.toFootprint());

        // 10920个字节
        GraphLayout layout2 = GraphLayout.parseInstance(map2);
        System.out.println(layout2.toFootprint());
    }

    public static class TestObject {
        private int id;

        private String name;

        public TestObject(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
