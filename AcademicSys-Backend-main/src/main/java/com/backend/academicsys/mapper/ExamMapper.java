package com.backend.academicsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import com.backend.academicsys.entity.Exam;

@Mapper
public interface ExamMapper extends BaseMapper<Exam> {

    @Delete("DELETE FROM exam")
    void deleteAllExams();
    /**
     * 根据课程ID列表查询对应的考试ID列表
     *
     * @param courseIds 课程ID列表
     * @return 考试ID列表
     */
    @Select("<script>" +
            "SELECT * " +
            "FROM exam " +
            "WHERE course_id IN " +
            "<foreach item='id' collection='courseIds' open='(' separator=',' close=')'>#{id}</foreach>" +
            "</script>")
    List<Exam> findExamsByCourseIds(@Param("courseIds") List<Long> courseIds);

    /**
     * 根据courseId找exam
     * 
     * @param courseId 课程ID
     * @return 考试
     */
    @Select("SELECT * FROM exam WHERE course_id = #{courseId}")
    Exam findExamByCourseId(Long courseId);

    @Insert("INSERT INTO exam (exam_id, course_id, exam_date, exam_start_time, exam_end_time, exam_location, exam_type, duration, exam_format, total_marks, pass_marks, additional_info) " +
            "VALUES (#{examId}, #{courseId}, #{examDate}, #{examStartTime}, #{examEndTime}, #{examLocation}, #{examType}, #{duration}, #{examFormat}, #{totalMarks}, #{passMarks}, #{additionalInfo})")
    @Options(useGeneratedKeys = true, keyProperty = "examId")
    int insert(Exam exam);

    /**
     * 根据学生ID查询该学生参加的所有考试
     *
     * @param studentId 学生ID
     * @return 考试列表
     */
    @Select("SELECT exam_date, exam_start_time, exam_end_time " +
            "FROM exam e " +
            "INNER JOIN exam_student es ON e.exam_id = es.exam_id " +
            "WHERE es.student_id = #{studentId}")
    List<Exam> findExamsByStudentId(@Param("studentId") Long studentId);
    /**
     * 根据教师ID查询该教师的所有考试
     * @param schoolId 教师ID
     * @return 考试列表
     */
    @Select("SELECT e.exam_date, e.exam_start_time, e.exam_end_time " +
            "FROM exam e " +
            "INNER JOIN exam_teacher et ON e.exam_id = et.exam_id " +
            "WHERE et.school_id = #{schoolId}")
    List<Exam> findExamsBySchoolId(@Param("schoolId") Long schoolId);

}