package com.backend.academicsys.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.backend.academicsys.entity.Course;
import com.backend.academicsys.entity.Result;

@Service
public interface CourseService {
    Result<List<Course>> findByCondition(Map<String, Object> condition);
    Result<String> deleteById(Course course);
    Result<String> createCourse(Course course);
    Result<String> modCourse(Course course);
    Result<List<Map<String, Object>>> getAllTeachers(Course course);
    Result<List<Map<String, Object>>> getAllTAs(Course course);
    Result<String> addTeacher(int courseId, Long teacherId);
    Result<String> addTA(int courseId, Long taId);
    Result<String> deleteTeacher(int courseId, Long teacherId);
    Result<String> deleteTA(int courseId, Long taId);
    Course findCourseByCourseId(int course_id);
    List<Course> findCourseByCourseName(String course_name);
    List<Course> findCourseByCourseCredit(int credit);
    List<Course> findCourseByCourseDepartment(String department);
    List<Course> findCourseByCourseCategory(String category);
    List<Course> findCourseByCourseSemester(String semester);
    List<Course> findCourseByCourseLocation(String location);
    List<Course> findCourseByCourseTarget(String target);
}
