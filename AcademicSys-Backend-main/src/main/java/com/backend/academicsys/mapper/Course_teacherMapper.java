package com.backend.academicsys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.backend.academicsys.entity.Course_teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface Course_teacherMapper extends BaseMapper<Course_teacher>{
    /**
     * 根据教师ID查询该教师教授的所有课程ID
     * 
     * @param teacherId 教师ID
     * @return 课程ID列表
     */
    @Select("SELECT course_id FROM course_teacher WHERE school_id = #{teacherId}")
    List<Long> findCourseIdsByTeacherId(Long teacherId);

    /**
     * 根据课程ID查询该课程的所有教师
     * 
     * @param courseId 课程ID
     * @return 教师ID列表
     */
    @Select("SELECT school_id FROM course_teacher WHERE course_id=#{courseId} AND ta_or_tc=0")
    List<Long> findTeachersByCourseId(int courseId);

    /**
     * 根据课程ID查询该课程的所有助教
     * 
     * @param courseId 课程ID
     * @return 助教ID列表
     */
    @Select("SELECT school_id FROM course_teacher WHERE course_id=#{courseId} AND ta_or_tc=1")
    List<Long> findTAsByCourseId(int courseId);

    /**
     * 根据课程ID和教师ID查询是否存在该记录
     * 
     * @param courseId
     * @param teacherId
     * @return
     */
    @Select("SELECT COUNT(*) FROM course_teacher WHERE course_id=#{courseId} AND school_id=#{teacherId} AND ta_or_tc=0")
    int findTeacherByCourseIdAndTeacherId(@Param("courseId") int courseId, @Param("teacherId") long teacherId);

    /**
     * 根据课程ID和助教ID查询是否存在该记录
     * 
     * @param courseId
     * @param taId
     * @return
     */
    @Select("SELECT COUNT(*) FROM course_teacher WHERE course_id=#{courseId} AND school_id=#{taId} AND ta_or_tc=1")
    int findTAByCourseIdAndTAId(@Param("courseId") int courseId, @Param("taId") long taId);

    /**
     * 添加教师
     * 
     * @param courseId 课程ID
     * @param teacherId 教师ID
     * @return 是否添加成功
     */
    @Insert("INSERT INTO course_teacher (school_id, course_id, ta_or_tc) VALUES (#{teacherId}, #{courseId}, 0)")
    boolean addTeacher(@Param("courseId") int courseId, @Param("teacherId") long teacherId);

    /**
     * 添加助教
     * 
     * @param courseId 课程ID
     * @param taId 助教ID
     * @return 是否添加成功
     */
    @Insert("INSERT INTO course_teacher (school_id, course_id, ta_or_tc) VALUES (#{taId}, #{courseId}, 1)")
    boolean addTA(@Param("courseId") int courseId, @Param("taId") long taId);

    /**
     * 删除教师
     * 
     * @param courseId 课程ID
     * @param teacherId 教师ID
     * @return 是否删除成功
     */
    @Delete("DELETE FROM course_teacher WHERE course_id=#{courseId} AND school_id=#{teacherId} AND ta_or_tc=0")
    boolean deleteTeacher(@Param("courseId") int courseId, @Param("teacherId") long teacherId);

    /**
     * 删除助教
     * 
     * @param courseId 课程ID
     * @param taId 助教ID
     * @return 是否删除成功
     */
    @Delete("DELETE FROM course_teacher WHERE course_id=#{courseId} AND school_id=#{taId} AND ta_or_tc=1")
    boolean deleteTA(@Param("courseId") int courseId, @Param("taId") long taId);
}