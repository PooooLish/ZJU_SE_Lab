package com.backend.academicsys.service;
import java.util.List;

import com.backend.academicsys.entity.Course_change;

public interface Course_changeService {
    void insertCourseChange(Course_change courseChange);
    List<Course_change> findAllStudentChange();
    Course_change findCourseChangeByCid(int cid);
    void updateCourseChange(int cid);
}
