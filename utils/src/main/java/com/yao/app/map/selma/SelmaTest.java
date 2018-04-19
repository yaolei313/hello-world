package com.yao.app.map.selma;

import com.yao.app.map.Student;
import com.yao.app.map.StudentDTO;
import fr.xebia.extras.selma.Selma;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yaolei02 on 2018/3/1.
 */
public class SelmaTest {

    private static final Logger LOG = LoggerFactory.getLogger(SelmaTest.class);

    public static void main(String[] args) {
        StudentMapper mapper = Selma.builder(StudentMapper.class).build();

        Student stu = new Student();
        stu.setId(196907);
        stu.setName("李白");

        StudentDTO r1 = mapper.asStudentDTO(stu);
        //LOG.info("{}",JsonMapper);

    }
}
