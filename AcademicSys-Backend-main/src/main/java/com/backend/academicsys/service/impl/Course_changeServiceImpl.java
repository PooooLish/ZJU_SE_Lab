package com.backend.academicsys.service.impl;


import com.backend.academicsys.entity.Course_change;
import com.backend.academicsys.mapper.Course_changeMapper;
import com.backend.academicsys.service.Course_changeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Course_changeServiceImpl implements Course_changeService {
    @Autowired
    Course_changeMapper course_changeMapper;

    @Override
    public void insertCourseChange(Course_change courseChange){
        course_changeMapper.insertCourseChange(courseChange);
    }
    @Override
    public List<Course_change> findAllStudentChange(){
        return course_changeMapper.selectStudent();
    }

    @Override
    public Course_change findCourseChangeByCid(int cid){
        return course_changeMapper.selectById(cid);
    }

    @Override
    public void updateCourseChange(int cid){
        course_changeMapper.ApproveById(cid);
    }
}
