package com.yao.app.java.jol;

import com.yao.app.java.jvm.Employee;
import org.openjdk.jol.info.GraphLayout;

/**
 * 描述:
 *
 * @author allen@xiaohongshu.com 2020-01-15
 */
public class JolStudy {

    public static void main(String[] args) {
        Employee st = new Employee();
        st.setId(1);
        st.setCode("111");
        st.setName("li bai");

        GraphLayout layout = GraphLayout.parseInstance(st);
        System.out.println(layout.toFootprint());
    }
}
