package com.backend.academicsys.mapper;

import com.backend.academicsys.entity.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Result;

import java.util.List;

@Mapper
public interface ForumMapper {

    // 根据学校ID获取论坛用户
    @Results(id = "forum_user", value = {
            @Result(property = "forum_id", column = "forum_id"),
            @Result(property = "school_id", column = "school_id"),
            @Result(property = "reg_time", column = "reg_time")
    })
    @Select("SELECT * FROM forum_user WHERE school_id = #{schoolId}")
    Forum_user getForumUserBySchoolId(String schoolId);

    // 插入新的论坛用户
    @Insert("INSERT INTO forum_user (school_id) VALUES (#{schoolId})")
    int insertForumUser(String schoolId);

    // 插入新的论坛操作
    @Insert("INSERT INTO forum_opt (forum_id, post_id, opt_type) VALUES (#{forumId}, #{postId}, #{operation})")
    int insertForumOpt(int forumId, int postId, String operation);

    // 删除论坛操作
    @Delete("DELETE FROM forum_opt WHERE forum_id = #{forumId} AND post_id = #{postId} AND opt_type = #{operation}")
    int deleteForumOpt(int forumId, int postId, String operation);

    // 获取所有的论坛帖子
    @Results(id = "forum_post", value = {
            @Result(property = "post_id", column = "post_id"),
            @Result(property = "forum_id", column = "forum_id"),
            @Result(property = "post_time", column = "post_time"),
            @Result(property = "teacher_id", column = "teacher_id"),
            @Result(property = "course_id", column = "course_id"),
            @Result(property = "grade", column = "grade"),
            @Result(property = "content", column = "content"),
            @Result(property = "like_count", column = "like_count"),
            @Result(property = "dislike_count", column = "dislike_count"),
            @Result(property = "favor_count", column = "favor_count"),
            @Result(property = "is_top", column = "is_top")
    })
    @Select("SELECT * FROM forum_post ORDER BY is_top DESC, post_time DESC")
    List<Forum_post> getAllPosts();

    // 根据教师名字搜索教师
    @Results(id = "teacher", value = {
            @Result(property = "schoolId", column = "school_id"),
            @Result(property = "jobTitle", column = "job_title"),
            @Result(property = "admissionTime", column = "admission_time"),
            @Result(property = "education", column = "education"),
            @Result(property = "pastEducation", column = "past_education"),
            @Result(property = "pastWork", column = "past_work"),
            @Result(property = "research", column = "research"),
            @Result(property = "teaching", column = "teaching"),
    })
    @Select("SELECT t.* FROM teacher t INNER JOIN user u ON t.school_id = u.school_id WHERE u.user_name LIKE CONCAT('%', #{teacherName}, '%')")
    List<Teacher> getTeachersSearch(String teacherName);

    // 根据课程名字搜索课程
    @Results(id = "course", value = {
            @Result(property = "courseId", column = "course_id"),
            @Result(property = "courseName", column = "course_name"),
            @Result(property = "credit", column = "credit"),
            @Result(property = "courseDepartment", column = "course_department"),
            @Result(property = "category", column = "category"),
            @Result(property = "schedule", column = "schedule"),
            @Result(property = "location", column = "location"),
            @Result(property = "semester", column = "semester"),
            @Result(property = "maxStudents", column = "max_students"),
            @Result(property = "numStudents", column = "num_students"),
            @Result(property = "testTime", column = "test_time"),
            @Result(property = "gradeCal", column = "grade_cal"),
            @Result(property = "target", column = "target"),
            @Result(property = "description", column = "description")
    })
    @Select("SELECT * FROM course WHERE course_name LIKE CONCAT('%', #{courseName}, '%')")
    List<Course> getCoursesSearch(String courseName);

    // 根据教师ID获取课程
    @ResultMap("course")
    @Select("SELECT c.* FROM course c INNER JOIN course_teacher ct ON c.course_id = ct.course_id WHERE ct.school_id = #{teacherId}")
    List<Course> getCoursesByTeacherId(String teacherId);

    // 根据课程ID获取教师
    @ResultMap("teacher")
    @Select("SELECT t.* FROM teacher t INNER JOIN course_teacher ct ON t.school_id = ct.school_id WHERE ct.course_id = #{courseId}")
    List<Teacher> getTeachersByCourseId(String courseId);

    // 根据教师ID和课程ID获取论坛帖子
    @ResultMap("forum_post")
    @Select("SELECT * FROM forum_post WHERE teacher_id = #{teacherId} AND course_id = #{courseId}")
    List<Forum_post> getPosts(String teacherId, String courseId);

    // 添加新的论坛帖子
    @Insert("INSERT INTO forum_post (forum_id, teacher_id, course_id, content, grade) VALUES (#{forumId}, #{teacherId}, #{courseId}, #{content}, #{grade})")
    int addPost(int forumId, String teacherId, String courseId, String content, int grade);

    // 根据学校ID和操作类型获取论坛帖子
    @ResultMap("forum_post")
    @Select("SELECT * FROM forum_post INNER JOIN forum_opt ON forum_post.post_id = forum_opt.post_id WHERE forum_opt.forum_id = #{forumId} AND forum_opt.opt_type = #{optType}")
    List<Forum_post> getPostsByOptType(int forumId, String optType);

    // 根据帖子id删除帖子
    @Delete("DELETE FROM forum_post WHERE post_id = #{postId}")
    int deletePost(int postId);

    // 根据帖子id置顶帖子
    @Update("UPDATE forum_post SET is_top = 1 WHERE post_id = #{postId}")
    int topPost(int postId);

    // 根据帖子id取消置顶帖子
    @Update("UPDATE forum_post SET is_top = 0 WHERE post_id = #{postId}")
    int cancelTop(int postId);

    @ResultMap("teacher")
    @Select("SELECT t.*, AVG(fp.grade) as avg_grade FROM teacher t INNER JOIN forum_post fp ON t.school_id = fp.teacher_id GROUP BY t.school_id ORDER BY avg_grade DESC")
    List<Teacher> getHotTeacher();

    @ResultMap("course")
    @Select("SELECT c.*, AVG(fp.grade) as avg_grade FROM course c INNER JOIN forum_post fp ON c.course_id = fp.course_id GROUP BY c.course_id ORDER BY avg_grade DESC")
    List<Course> getHotCourse();

    @Select("SELECT user_name FROM user WHERE school_id = #{teacherId}")
    String getTeacherNameByTeacherId(Long teacherId);

    @Select("SELECT course_name FROM course WHERE course_id = #{courseId}")
    String getCourseNameByCourseId(int courseId);

    @ResultMap("forum_post")
    @Select("SELECT * FROM forum_post WHERE is_top = 1 ORDER BY post_time DESC")
    List<Forum_post> getTopPosts();

    @ResultMap("forum_post")
    @Select("SELECT * FROM forum_post WHERE teacher_id IN ( SELECT school_id FROM user WHERE user_name = #{teacherName})")
    List<Forum_post> getPostsByTeacher(String teacherName);

    @ResultMap("forum_post")
    @Select("SELECT * FROM forum_post WHERE course_id IN ( SELECT course_id FROM course WHERE course_name = #{courseName})")
    List<Forum_post> getPostsByCourse(String courseName);

    @ResultMap("forum_post")
    @Select("SELECT * FROM forum_post WHERE post_id = #{i}")
    Forum_post getPostById(int i);

    @Results(id = "forumOptResult", value = {
            @Result(property = "forum_opt_id", column = "forum_opt_id", id = true),
            @Result(property = "forum_id", column = "forum_id"),
            @Result(property = "post_id", column = "post_id"),
            @Result(property = "opt_time", column = "opt_time"),
            @Result(property = "opt_type", column = "opt_type")
    })
    @Select("SELECT * FROM forum_opt WHERE forum_id = #{i} AND post_id = #{i1} AND opt_type = 'like'")
    Forum_opt getLikeOpt(int i, int i1);

    @ResultMap("forumOptResult")
    @Select("SELECT * FROM forum_opt WHERE forum_id = #{i} AND post_id = #{i1} AND opt_type = 'dislike'")
    Forum_opt getDislikeOpt(int i, int i1);

    @ResultMap("forumOptResult")
    @Select("SELECT * FROM forum_opt WHERE forum_id = #{i} AND post_id = #{i1} AND opt_type = 'favor'")
    Forum_opt getFavorOpt(int i, int i1);

    //@ResultMap("course")
    @Select("SELECT course_name FROM course")
    List<String> getAllCourse();

    @Select("SELECT school_id FROM user WHERE user_name = #{teacher}")
    Long getTeacherIdByTeacherName(String teacher);

    @ResultMap("teacher")
    @Select("SELECT * FROM teacher")
    List<Teacher> getAllTeacher();

    @Select("SELECT course_id FROM course WHERE course_name = #{course}")
    int getCourseIdByCourseName(String course);

    @Select("SELECT COUNT(*) FROM forum_opt WHERE forum_id = #{forumId} AND post_id = #{postId} AND opt_type = 'like'")
    int getOptByForumIdLike(String forumId, String postId);

    @Select("SELECT COUNT(*) FROM forum_opt WHERE forum_id = #{forumId} AND post_id = #{postId} AND opt_type = 'dislike'")
    int getOptByForumIdDislike(String forumId, String postId);

    @ResultMap("forum_post")
    @Select("SELECT * FROM forum_post WHERE forum_id = #{forumId}")
    List<Forum_post> getPostsByForumId(String forumId);
}
