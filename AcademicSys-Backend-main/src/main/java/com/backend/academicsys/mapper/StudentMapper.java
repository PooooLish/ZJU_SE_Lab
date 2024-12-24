package com.backend.academicsys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.backend.academicsys.dto.CurrentRankDto;
import com.backend.academicsys.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
        /*
    * 根据studentId在student中查找学生排名
     */
    @Select("SELECT  school_id, college, major, class_name AS class_name, current_ranking, current_grades FROM student WHERE school_id = #{studentId} ")
    CurrentRankDto findCurrentRankByStudentId(Long studentId);
}
