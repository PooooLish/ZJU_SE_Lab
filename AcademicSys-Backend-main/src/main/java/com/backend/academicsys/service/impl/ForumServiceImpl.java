package com.backend.academicsys.service.impl;

import com.backend.academicsys.entity.Course;
import com.backend.academicsys.entity.Forum_post;
import com.backend.academicsys.entity.Forum_user;
import com.backend.academicsys.entity.Teacher;
import com.backend.academicsys.mapper.ForumMapper;
import com.backend.academicsys.service.ForumService;
import com.backend.academicsys.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ForumMapper forumMapper;

    @Autowired
    private SensitiveWordService sensitiveWordService;

    @Override
    public boolean isTeacher(String school_id) {
        // `school_id`：一共 11 位，开头 1 / 2 / 3 分别代表管理员、教师、学生角色。随后三位表示入学 / 入职年份 (如 2024 对应 024)，最后 7 位为给定编号
        return school_id.charAt(0) == '2';
    }

    @Override
    public boolean isStudent(String school_id) {
        return school_id.charAt(0) == '3';
    }

    @Override
    public boolean hasRegistered(String school_id) {
        // 判断用户是否已经注册论坛
        Forum_user user = forumMapper.getForumUserBySchoolId(school_id);
        return user != null;
    }

    @Override
    public boolean addForumUser(String school_id) {
        int rows = forumMapper.insertForumUser(school_id);
        return rows > 0;
    }

    @Override
    public boolean manageOpt(String forumId, String postId, String operation, String direction) {
        if(operation.equals("dislike")) {
            int row = forumMapper.getOptByForumIdLike(forumId, postId);
            if(row > 0){
                return true;
            }
        }
        if(operation.equals("like")) {
            int row = forumMapper.getOptByForumIdDislike(forumId, postId);
            if(row > 0){
                return true;
            }
        }

        int rows;
        if(direction.equals("up")){
            rows = forumMapper.insertForumOpt(Integer.parseInt(forumId), Integer.parseInt(postId), operation);
        }else if(direction.equals("down")){
            rows = forumMapper.deleteForumOpt(Integer.parseInt(forumId), Integer.parseInt(postId), operation);
        }else{
            return false;
        }
        return rows > 0;
    }

    @Override
    public Integer getForumId(String schoolId) {
        Forum_user user = forumMapper.getForumUserBySchoolId(schoolId);
        return user.getForum_id();
    }

    @Override
    public List<Forum_post> findAllPosts() {
        return forumMapper.getAllPosts();
    }

    @Override
    public List<Teacher> getTeachersSearch(String teacherName) {
        return forumMapper.getTeachersSearch(teacherName);
    }

    @Override
    public List<Course> getCoursesSearch(String courseName) {
        return forumMapper.getCoursesSearch(courseName);
    }

    @Override
    public List<Course> getCoursesByTeacherId(String teacherId) {
        return forumMapper.getCoursesByTeacherId(teacherId);
    }

    @Override
    public List<Teacher> getTeachersByCourseId(String courseId) {
        return forumMapper.getTeachersByCourseId(courseId);
    }

    @Override
    public List<Forum_post> getPosts(String teacherId, String courseId) {
        return forumMapper.getPosts(teacherId, courseId);
    }

    @Override
    public boolean addPost(String forumId, String teacherId, String courseId, String content, String grade) {

        int rows =forumMapper.addPost(Integer.parseInt(forumId), teacherId, courseId, content, Integer.parseInt(grade));
        return rows>0;
    }

    @Override
    public List<Forum_post> getLikePosts(String forumId) {
        return forumMapper.getPostsByOptType(Integer.parseInt(forumId), "like");
    }

    @Override
    public List<Forum_post> getCollectPosts(String forumId) {
        return forumMapper.getPostsByOptType(Integer.parseInt(forumId), "favor");
    }

    @Override
    public List<Forum_post> getPostPosts(String forumId) {
        return forumMapper.getPostsByForumId(forumId);
    }

    @Override
    public List<Forum_post> getDislikePosts(String forumId) {
        return forumMapper.getPostsByOptType(Integer.parseInt(forumId), "dislike");
    }


    @Override
    public List<Forum_post> findPostsWithSensitiveWords(){
        //获取所有帖子
        List<Forum_post> allPosts = forumMapper.getAllPosts();
        List<Forum_post> postsWithSensitiveWords = new ArrayList<>();

        for (Forum_post post : allPosts) {
            if (containsSensitiveWord(post.getContent())) {
                postsWithSensitiveWords.add(post);
            }
        }
        return postsWithSensitiveWords;
    }

    //判断内容是否有敏感词
    private boolean containsSensitiveWord(String content) {
        // 调用敏感词服务来检查内容是否包含敏感词
        return sensitiveWordService.containsSensitiveWord(content);
    }

    @Override
    public boolean adminOpt(String postId, String operation){
        switch (operation) {
            case "delete" -> {
                // 删除帖子
                return forumMapper.deletePost(Integer.parseInt(postId)) > 0;
            }
            case "top" -> {
                // 置顶帖子
                return forumMapper.topPost(Integer.parseInt(postId)) > 0;
            }
            case "cancel_top" -> {
                // 取消置顶
                return forumMapper.cancelTop(Integer.parseInt(postId)) > 0;
            }default -> {
                return false;
            }
        }
    }

    @Override
    public List<Teacher> getHotTeacher() {
        return forumMapper.getHotTeacher();
    }

    @Override
    public List<Course> getHotCourse() {
        return forumMapper.getHotCourse();
    }


    @Override
    public boolean batchDelete(List<Long> ids) {
        int rows = 0;
        for (Long id : ids){
            rows+=forumMapper.deletePost(Math.toIntExact(id));
        }
        return rows>0;
    }

    @Override
    public String getTeacherNameByTeacherId(Long teacherId) {
        return forumMapper.getTeacherNameByTeacherId(teacherId);
    }

    @Override
    public String getCourseNameByCourseId(int courseId) {
        return forumMapper.getCourseNameByCourseId(courseId);
    }

    @Override
    public List<Forum_post> getTopPosts() {
        return forumMapper.getTopPosts();
    }

    @Override
    public List<Forum_post> getPostsByTeacher(String teacherName) {
        return forumMapper.getPostsByTeacher(teacherName);
    }

    @Override
    public List<Forum_post> getPostsByCourse(String courseName) {
        return forumMapper.getPostsByCourse(courseName);
    }

    @Override
    public Forum_post getPostById(int i) {
        return forumMapper.getPostById(i);
    }

    @Override
    public boolean isLiked(String postId, String forumId) {
        return forumMapper.getLikeOpt(Integer.parseInt(forumId), Integer.parseInt(postId)) != null;
    }

    @Override
    public boolean isDisliked(String postId, String forumId) {
        return forumMapper.getDislikeOpt(Integer.parseInt(forumId), Integer.parseInt(postId)) != null;
    }

    @Override
    public boolean isFavored(String postId, String forumId) {
        return forumMapper.getFavorOpt(Integer.parseInt(forumId), Integer.parseInt(postId)) != null;
    }

    @Override
    public List<String> getAllCourse(){
        List<String> courses=forumMapper.getAllCourse();
        return courses;
    }

    @Override
    public Long getTeacherIdByTeacherName(String teacher) {
        return forumMapper.getTeacherIdByTeacherName(teacher);
    }

    @Override
    public List<Teacher> getAllTeacher() {
        return forumMapper.getAllTeacher();
    }

    @Override
    public int getCourseIdByCourseName(String course) {
        return forumMapper.getCourseIdByCourseName(course);
    }


}
