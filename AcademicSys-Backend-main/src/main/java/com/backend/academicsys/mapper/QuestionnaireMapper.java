package com.backend.academicsys.mapper;

import com.backend.academicsys.entity.Questionnaire;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface QuestionnaireMapper {

    //根据课程id获取学生id
    @Select("select student_id from course_student where course_id = #{courseId}")
    List<Long> getStudentsByCourseId(int courseId);

    //根据课程id获取教师id
    @Select("select school_id from course_teacher where course_id = #{courseId} and ta_or_tc = 1")
    Long getTeacherByCourseId(int courseId);

    //插入问卷
    @Insert("insert into questionnaire (ques_start_date, ques_end_date, student_id, teacher_id, course_id, ques_status, ques_aspect1, ques_aspect2, ques_aspect3, ques_aspect4, ques_grade, ques_comment) values (#{startTime}, #{endTime}, #{student}, #{teacherId}, #{courseId}, 0, 5, 5, 5, 5, 5, '无')")
    int insertQuestionnaire(Date startTime, Date endTime, Long student, Long teacherId, int courseId);

    //获取所有问卷
    @Results({
            @Result(property = "ques_id", column = "ques_id"),
            @Result(property = "course_id", column = "course_id"),
            @Result(property = "teacher_id", column = "teacher_id"),
            @Result(property = "student_id", column = "student_id"),
            @Result(property = "ques_start_date", column = "ques_start_date"),
            @Result(property = "ques_end_date", column = "ques_end_date"),
            @Result(property = "ques_status", column = "ques_status"),
            @Result(property = "ques_comment", column = "ques_comment"),
            @Result(property = "ques_aspect1", column = "ques_aspect1"),
            @Result(property = "ques_aspect2", column = "ques_aspect2"),
            @Result(property = "ques_aspect3", column = "ques_aspect3"),
            @Result(property = "ques_aspect4", column = "ques_aspect4"),
            @Result(property = "ques_grade", column = "ques_grade")
    })
    @Select("SELECT * from questionnaire")
    List<Questionnaire> getAllQuestionnaires();

    //根据问卷id删除问卷
    @Delete("delete from questionnaire where ques_id = #{quesId}")
    int deleteQuestionnaire(int quesId);

    //根据问卷id查询课程名称？
    @Select("SELECT course_name FROM Course WHERE course_id = #{course_id}")
    String getCourseName(int course_id);

    //根据学生id查询学生名称？
    @Select("SELECT user_name FROM user WHERE school_id = #{stu_id}")
    String getStuName(Long stu_id);

    @Select("SELECT user_name FROM user WHERE school_id = #{tea_id}")
    String getTeacherName(Long tea_id);
}
//    @Results({
//            @Result(property = "ques_id", column = "ques_id"),
//            @Result(property = "course_id", column = "course_id"),
//            @Result(property = "teacher_id", column = "teacher_id"),
//            @Result(property = "student_id", column = "student_id"),
//            @Result(property = "ques_start_date", column = "ques_start_date"),
//            @Result(property = "ques_end_date", column = "ques_end_date"),
//            @Result(property = "ques_status", column = "ques_status"),
//            @Result(property = "ques_comment", column = "ques_comment"),
//            @Result(property = "ques_aspect1", column = "ques_aspect1"),
//            @Result(property = "ques_aspect2", column = "ques_aspect2"),
//            @Result(property = "ques_aspect3", column = "ques_aspect3"),
//            @Result(property = "ques_aspect4", column = "ques_aspect4"),
//            @Result(property = "ques_grade", column = "ques_grade")
//    })