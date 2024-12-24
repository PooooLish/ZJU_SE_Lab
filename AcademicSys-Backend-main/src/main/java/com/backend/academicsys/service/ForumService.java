package com.backend.academicsys.service;

import com.backend.academicsys.entity.Course;
import com.backend.academicsys.entity.Forum_post;
import com.backend.academicsys.entity.Teacher;

import java.util.List;

public interface ForumService {

    boolean isTeacher(String school_id);

    boolean hasRegistered(String school_id);

    boolean addForumUser(String school_id);

    boolean isStudent(String school_id);

    List<Forum_post> findAllPosts();

    List<Teacher> getTeachersSearch(String teacherName);

    List<Course> getCoursesSearch(String courseName);

    List<Course> getCoursesByTeacherId(String teacherId);

    List<Teacher> getTeachersByCourseId(String courseId);

    List<Forum_post> getPosts(String teacherId, String courseId);

    boolean addPost(String forumId, String teacherId, String courseId, String content, String grade);

    List<Forum_post> getLikePosts(String forumId);

    List<Forum_post> getCollectPosts(String forumId);

    List<Forum_post> getPostPosts(String forumId);

    List<Forum_post> getDislikePosts(String forumId);

    boolean manageOpt(String forum_id, String postId, String operation, String direction);

    Integer getForumId(String schoolId);

    List<Forum_post> findPostsWithSensitiveWords();

    boolean adminOpt(String postId, String operation);

    List<Teacher> getHotTeacher();

    List<Course> getHotCourse();

    boolean batchDelete(List<Long> ids);

    String getTeacherNameByTeacherId(Long teacherId);

    String getCourseNameByCourseId(int courseId);

    List<Forum_post> getTopPosts();

    List<Forum_post> getPostsByTeacher(String teacherName);

    List<Forum_post> getPostsByCourse(String courseName);

    Forum_post getPostById(int i);

    boolean isLiked(String postId, String forumId);

    boolean isDisliked(String postId, String forumId);

    boolean isFavored(String postId, String forumId);

    List<String> getAllCourse();

    Long getTeacherIdByTeacherName(String teacher);

    List<Teacher> getAllTeacher();

    int getCourseIdByCourseName(String course);
}
