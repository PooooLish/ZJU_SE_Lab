package com.backend.academicsys.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.*;
import com.backend.academicsys.entity.Course_change;

@Mapper
public interface Course_changeMapper extends BaseMapper<Course_change>{
    @Insert("INSERT INTO course_change (student_id, course_id, change_type, change_date, change_reason, " +
            "previous_status, new_status, approved_by, approval_date, record_status) " +
            "VALUES (#{student_id}, #{course_id}, #{change_type}, #{change_date}, #{change_reason}, " +
            "#{previous_status}, #{new_status}, #{approved_by}, #{approval_date}, #{record_status})")
    void insertCourseChange(Course_change courseChange);

    @Select(
            "SELECT * FROM course_change " +
            "WHERE new_status = '待审批'")
    List<Course_change> selectStudent();

    @Select("SELECT * FROM course_change WHERE change_id = #{id}")
    Course_change selectById(int id);

    @Update(
    "UPDATE course_change " +
    "SET previous_status = '待审批', new_status = '已批准'"+
    "WHERE change_id = #{change_id}")
int ApproveById(int change_id);
}
