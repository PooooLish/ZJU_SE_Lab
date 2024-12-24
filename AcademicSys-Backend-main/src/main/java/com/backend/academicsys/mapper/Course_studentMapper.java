package com.backend.academicsys.mapper;

import com.backend.academicsys.dto.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import com.backend.academicsys.entity.Course_student;

@Mapper
public interface Course_studentMapper extends BaseMapper<Course_student> {
    /**
     * 根据学生ID查询该学生选修的所有课程ID
     *
     * @param studentId 学生ID
     * @return 课程ID列表
     */
    @Select("SELECT course_id FROM course_student WHERE student_id = #{studentId}")
    List<Long> findCourseIdsByStudentId(Long studentId);
    /**
     * 根据学生ID查询该学生选修的所有课程
     *
     * @param studentIds 学生ID
     * @return 课程列表
     */
    @Select("<script>" +
            "SELECT * " +
            "FROM course_student " +
            "WHERE student_id IN " +
            "<foreach item='id' collection='studentIds' open='(' separator=',' close=')'>#{id}</foreach>" +
            "</script>")
    List<Course_student> findCoursesByStudentIds(@Param("studentIds") List<Long> studentIds);

    /**
     * 根据学生ID和课程ID查询该学生在该课程的成绩信息
     *
     * @param studentId 学生ID
     * @param courseId 课程ID
     * @return 课程列表
     */
    @Select("SELECT * FROM course_student WHERE student_id = #{studentId} AND course_id = #{courseId}")
    Course_student findCoursesByStudentIdAndCourseId(@Param("courseId") Long courseId, @Param("studentId") Long studentId);

    /**
     * 根据课程ID查询该课程的所有学生课程
     *
     * @param courseId 课程ID
     * @return 课程列表
     */
    @Select("SELECT * FROM course_student WHERE course_id = #{courseId}")
    List<Course_student> findCoursesByCourseId(Long courseId);

    @Select("SELECT course_id FROM course_student WHERE student_id = #{studentId}")
    List<Integer> findCourseIdByStudentId(Long studentId);

    /*
    * 根据courseId寻找所有studentId
     */
    @Select("SELECT student_id FROM course_student WHERE course_id = #{courseId}")
    List<Long> findStudentIdsByCourseId(Long courseId);

    /*
    * 根据teacherId， studentId， courseId，  double grade,
            double ord_grade,
            double exam_grade,
            Boolean is_pass,
            double retake_grade
            * 设置学生成绩
     */
    @Update("UPDATE course_student SET grade = #{grade}, ord_grade = #{ord_grade}, exam_grade = #{exam_grade}, is_pass = #{is_pass}, retake_grade = #{retake_grade} WHERE student_id = #{studentId} AND course_id = #{courseId}")
    void setScoresByTeacher(@Param("studentId") Long studentId, @Param("courseId") Long courseId, @Param("grade") double grade, @Param("ord_grade") double ord_grade, @Param("exam_grade") double exam_grade, @Param("is_pass") Boolean is_pass, @Param("retake_grade") double retake_grade);

    @Select("select * from course_student where student_id = #{student_id}")
    @Results({
            @Result(property = "course_student_id", column = "course_student_id"),
            @Result(property = "course_id", column = "course_id"),
            @Result(property = "student_id", column = "student_id"),
            @Result(property = "enroll_date", column = "enroll_date"),
            @Result(property = "status", column = "status"),
            @Result(property = "grade", column = "grade"),
            @Result(property = "ord_grade", column = "ord_grade"),
            @Result(property = "exam_grade", column = "exam_grade"),
            @Result(property = "is_pass", column = "is_pass"),
            @Result(property = "feedback", column = "feedback"),
            @Result(property = "retake", column = "retake"),
            @Result(property = "retake_grade", column = "retake_grade")
    })
    List<Course_student> getCourseByStudentId(Long student_id);

    @Insert("INSERT INTO course_student (course_student_id, student_id, course_id, enroll_date, status, " +
            "grade, ord_grade, exam_grade, is_pass, feedback, retake, retake_grade) " +
            "VALUES (#{course_student_id}, #{student_id}, #{course_id}, #{enroll_date}, #{status}, " +
            "#{grade}, #{ord_grade}, #{exam_grade}, #{is_pass}, #{feedback}, #{retake}, #{retake_grade})")
    void insertCourseStudent(Course_student courseStudent);

    @Delete("DELETE FROM course_student WHERE student_id = #{student_id} AND course_id = #{course_id}")
    void deleteCourseStudent(Long student_id, int course_id);

    @Update(
            "UPDATE course_student " +
            "SET status='已选' " +
            " where course_student_id = #{id} "
            )
    int acceptById(int id);

    @Update(
            "delete from course_student " +
            "where course_student_id = #{id}"
            )
    int rejectById(int id);

    @Select(
        "SELECT * from course_student " +
        "where status='待定'"
    )
    List<Course_student> SelectStudent();
}
