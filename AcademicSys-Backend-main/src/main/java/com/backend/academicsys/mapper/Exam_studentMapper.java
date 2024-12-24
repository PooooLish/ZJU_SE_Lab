package com.backend.academicsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import com.backend.academicsys.entity.Exam_student;

@Mapper
public interface Exam_studentMapper extends BaseMapper<Exam_student> {
    /**
     * 根据考试ID列表和学生ID查询对应的考试学生信息列表
     *
     * @param examIds   考试ID列表
     * @param studentId 学生ID
     * @return 考试学生信息列表
     */
    @Select("<script>" +
            "SELECT * " +
            "FROM exam_student " +
            "WHERE exam_id IN " +
            "<foreach item='id' collection='examIds' open='(' separator=',' close=')'>#{id}</foreach> " +
            "AND student_id = #{studentId}" +
            "</script>")
    List<Exam_student> findExam_studentsByExamIdsAndStudentId(@Param("examIds") List<Long> examIds, @Param("studentId") Long studentId);

    /**
     * 根据考试ID和学生ID查询对应的考试学生信息
     *
     * @param examId    考试ID
     * @param studentId 学生ID
     * @return 考试学生信息
     */
    @Select("SELECT * FROM exam_student WHERE exam_id = #{examId} AND student_id = #{studentId}")
    List<Exam_student> findExam_studentsByExamIdAndStudentId(Long examId, Long studentId);

    /**
     * 根据exam_student_id更新考试学生信息
     *
     * @param examStudent 更新后的考试学生信息
     * @return 更新的行数
     */
    @Update("<script>" +
            "UPDATE exam_student " +
            "SET exam_id = #{examId}, " +
            "student_id = #{studentId}, " +
            "exam_location = #{examLocation}, " +
            "exam_sit = #{examSit}, " +
            "exam_status = #{examStatus}, " +
            "exam_grade = #{examGrade}, " +
            "defer_or_resit = #{deferOrResit}, " +
            "defer_resit_grade = #{deferResitGrade} " +
            "WHERE exam_student_id = #{examStudentId}" +
            "</script>")
    int updateById(Exam_student examStudent);

}