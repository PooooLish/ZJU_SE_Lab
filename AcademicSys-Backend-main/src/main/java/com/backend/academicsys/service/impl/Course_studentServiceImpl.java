package com.backend.academicsys.service.impl;

import com.backend.academicsys.entity.Course_student;
import com.backend.academicsys.mapper.Course_studentMapper;
import com.backend.academicsys.service.Course_studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Course_studentServiceImpl implements Course_studentService {
    @Autowired
    Course_studentMapper course_studentMapper;

    @Override
    public List<Course_student> findCoursesBySid(Long student_id) {
        return course_studentMapper.getCourseByStudentId(student_id);
    }

    @Override
    public void insertCourseStudent(Course_student course_student) {
        course_studentMapper.insertCourseStudent(course_student);
    }

    @Override
    public void deleteCourseStudentBySidCid(Long student_id, int course_id){
        course_studentMapper.deleteCourseStudent(student_id, course_id);
    }
}
