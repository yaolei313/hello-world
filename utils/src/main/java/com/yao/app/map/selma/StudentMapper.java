package com.yao.app.map.selma;

import com.yao.app.map.Student;
import com.yao.app.map.StudentDTO;
import fr.xebia.extras.selma.Field;
import fr.xebia.extras.selma.Mapper;

/**
 * Created by yaolei02 on 2018/3/1.
 */
@Mapper(withCustomFields = {@Field({"name", "stuName"})}, withIgnoreFields = {})
public interface StudentMapper {
    StudentDTO asStudentDTO(Student student);

    Student asStudent(StudentDTO in,Student out);
}
