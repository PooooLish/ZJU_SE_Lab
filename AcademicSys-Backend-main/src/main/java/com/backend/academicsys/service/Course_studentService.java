package com.backend.academicsys.service;

import com.backend.academicsys.entity.Course_student;

import java.util.List;

public interface Course_studentService {
    List<Course_student> findCoursesBySid(Long student_id);

    void insertCourseStudent(Course_student course_student);

    void deleteCourseStudentBySidCid(Long student_id, int course_id);
}
