package com.backend.academicsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import com.backend.academicsys.entity.Exam_teacher;

@Mapper
public interface Exam_teacherMapper extends BaseMapper<Exam_teacher> {
    /**
     * 根据考试ID列表和教师ID查询对应的考试教师信息列表
     * 
     * @param examIds 考试ID列表
     * @param teacherId 教师ID
     * @return 考试教师信息列表
     */
    @Select("<script>" +
            "SELECT * " +
            "FROM exam_teacher " +
            "WHERE exam_id IN " +
            "<foreach item='id' collection='examIds' open='(' separator=',' close=')'>#{id}</foreach> " +
            "AND school_id = #{teacherId}" +
            "</script>")
    List<Exam_teacher> findExam_teachersByExamIdsAndTeacherId(@Param("examIds") List<Long> examIds, @Param("teacherId") Long teacherId);
}
