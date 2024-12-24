package com.backend.academicsys.mapper;

import com.backend.academicsys.entity.QuesGrade;
import com.backend.academicsys.entity.Questionnaire;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface QuesStuMapper {
    //学生未填写问卷
    @Select("SELECT q.* " +
            "FROM questionnaire q " +
            "WHERE q.student_id = #{student_id} " +
            "AND q.ques_status = 0 " +
            "AND q.ques_start_date < #{curTime} " +
            "AND q.ques_end_date > #{curTime}")
    @Results({
            @Result(property = "ques_id", column = "ques_id"),
            @Result(property = "ques_start_date", column = "ques_start_date"),
            @Result(property = "ques_end_date", column = "ques_end_date"),
            @Result(property = "ques_status", column = "ques_status"),
            @Result(property = "ques_aspect1", column = "ques_aspect1"),
            @Result(property = "ques_aspect2", column = "ques_aspect2"),
            @Result(property = "ques_aspect3", column = "ques_aspect3"),
            @Result(property = "ques_aspect4", column = "ques_aspect4"),
            @Result(property = "ques_grade", column = "ques_grade"),
            @Result(property = "ques_comment", column = "ques_comment"),
            @Result(property = "student_id", column = "student_id"),
            @Result(property = "teacher_id", column = "teacher_id"),
            @Result(property = "course_id", column = "course_id")
    })
    List<Questionnaire> getUndoQues(Date curTime, long student_id);

    //数据库问卷修改
    @Update("UPDATE questionnaire SET ques_status = #{ques_status}, ques_aspect1 = #{aspect_1}, ques_aspect2 = #{aspect_2}, ques_aspect3 = #{aspect_3}, ques_aspect4 = #{aspect_4}, ques_grade = #{grade}, ques_comment = #{ques_comment} WHERE student_id = #{student_id} AND teacher_id = #{teacher_id} AND course_id = #{course_id}")
    int addQues(int ques_status, int aspect_1, int aspect_2, int aspect_3, int aspect_4, double grade, String ques_comment, long student_id, long teacher_id, int course_id);

    //根据教师id和课程id查询问卷列表，教师查询
    @Select("SELECT * FROM questionnaire WHERE teacher_id = #{teacher_id} AND course_id = #{course_id} AND ques_status = 1")
    @Results({
            @Result(property = "ques_id", column = "ques_id"),
            @Result(property = "ques_start_date", column = "ques_start_date"),
            @Result(property = "ques_end_date", column = "ques_end_date"),
            @Result(property = "ques_status", column = "ques_status"),
            @Result(property = "ques_aspect1", column = "ques_aspect1"),
            @Result(property = "ques_aspect2", column = "ques_aspect2"),
            @Result(property = "ques_aspect3", column = "ques_aspect3"),
            @Result(property = "ques_aspect4", column = "ques_aspect4"),
            @Result(property = "ques_grade", column = "ques_grade"),
            @Result(property = "ques_comment", column = "ques_comment"),
            @Result(property = "student_id", column = "student_id"),
            @Result(property = "teacher_id", column = "teacher_id"),
            @Result(property = "course_id", column = "course_id")
    })
    List<Questionnaire> getSelectQues(long teacher_id,int course_id);

    //根据教师id查询全部问卷列表，教师查询（课程为空）
    @Select("SELECT * FROM questionnaire WHERE teacher_id = #{teacher_id} AND ques_status = 1")
    @Results({
            @Result(property = "ques_id", column = "ques_id"),
            @Result(property = "ques_start_date", column = "ques_start_date"),
            @Result(property = "ques_end_date", column = "ques_end_date"),
            @Result(property = "ques_status", column = "ques_status"),
            @Result(property = "ques_aspect1", column = "ques_aspect1"),
            @Result(property = "ques_aspect2", column = "ques_aspect2"),
            @Result(property = "ques_aspect3", column = "ques_aspect3"),
            @Result(property = "ques_aspect4", column = "ques_aspect4"),
            @Result(property = "ques_grade", column = "ques_grade"),
            @Result(property = "ques_comment", column = "ques_comment"),
            @Result(property = "student_id", column = "student_id"),
            @Result(property = "teacher_id", column = "teacher_id"),
            @Result(property = "course_id", column = "course_id")
    })
    List<Questionnaire> getQues(long teacher_id);

    //根据问卷id查询课程名称
    @Select("SELECT c.course_name FROM course c JOIN questionnaire q ON c.course_id = q.course_id WHERE q.ques_id = #{ques_id}")
    String getCourseName(int ques_id);

    //根据问卷id查询学生名称
    @Select("SELECT u.user_name FROM user u JOIN questionnaire q ON u.school_id = q.student_id WHERE q.ques_id = #{ques_id}")
    String getStuName(int ques_id);

    //根据教师id查询教师姓名
    @Select("SELECT user_name FROM user WHERE school_id = #{teacher_id}")
    String getTeacherName(long teacher_id);

    //根据老师id查询教授课程id
    @Select("SELECT course_id FROM course_teacher WHERE school_id = #{teacher_id}")
    List<Integer> getCourseTeach(long teacher_id);

    //根据课程id查询课程名称
    @Select("SELECT course_name FROM course WHERE course_id = #{course_id}")
    String courseNameGet(int course_id);
}
