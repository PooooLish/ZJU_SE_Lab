package com.backend.academicsys.controller;

import com.backend.academicsys.entity.Course;
import com.backend.academicsys.entity.Forum_post;
import com.backend.academicsys.entity.Teacher;
import com.backend.academicsys.service.ForumService;
import com.backend.academicsys.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ForumController {
    @Autowired
    private ForumService forumService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 论坛入口页面
    // 前端应该在用户点击进入论坛时调用此接口
    // 参数：通过路径参数的方式传递，需要school_id
    @GetMapping("/login")
    public ResponseEntity<?> login() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        String school_id = String.valueOf(id);
        if (forumService.isTeacher(school_id)) {
            // 用户不是学生或管理员，返回错误信息
            // 前端应提示错误信息
            return new ResponseEntity<>("教师无法登录论坛", HttpStatus.FORBIDDEN);
        }

        if (!forumService.hasRegistered(school_id)) {
            // 用户未注册，自动注册
            // 前端应跳转到论坛主页面
            if(forumService.addForumUser(school_id)){
                if(forumService.isStudent(school_id))
                    return new ResponseEntity<>("1", HttpStatus.OK);
                else return new ResponseEntity<>("0", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Invalid school_id", HttpStatus.BAD_REQUEST);
            }
        }

        // 用户已注册
        // 前端应根据是学生还是管理员跳转到相应论坛主页面
        ForumResponse response = new ForumResponse();
        if(forumService.isStudent(school_id))
            return new ResponseEntity<>("1", HttpStatus.OK);
        else return new ResponseEntity<>("0", HttpStatus.OK);
    }

    // 用来把数据库的实体类转换成前端需要的post
    private ResponseEntity<?> getResponseEntityPost(List<Forum_post> posts) {
        List<PostResponse> response = new ArrayList<>();
        for(Forum_post post : posts){
            String teacher = forumService.getTeacherNameByTeacherId(post.getTeacher_id());
            String course = forumService.getCourseNameByCourseId(post.getCourse_id());
            PostResponse postResponse = new PostResponse(post, teacher, course,
                    forumService.isLiked(String.valueOf(post.getPost_id()), String.valueOf(post.getForum_id())),
                    forumService.isDisliked(String.valueOf(post.getPost_id()), String.valueOf(post.getForum_id())),
                    forumService.isFavored(String.valueOf(post.getPost_id()), String.valueOf(post.getForum_id())));
            response.add(postResponse);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<?> getResponseEntityTeacher(List<Teacher> teachers) {
        List<TeacherResponse> response = new ArrayList<>();
        for (Teacher teacher : teachers) {
            String teacher_name = forumService.getTeacherNameByTeacherId(teacher.getSchoolId());
            response.add(new TeacherResponse(teacher, teacher_name));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // 论坛主页面
    @GetMapping("/getAll")
    public ResponseEntity<?> forum() {
        // 获取所有帖子
        // 前端应显示所有帖子
        List<Forum_post> posts = forumService.findAllPosts();
        return getResponseEntityPost(posts);
    }

    @GetMapping("/top")
    public ResponseEntity<?> getTop() {
        // 获取置顶帖子
        // 前端应显示所有置顶帖子
        List<Forum_post> posts = forumService.getTopPosts();
        return getResponseEntityPost(posts);
    }

    @GetMapping("/getpostbyteacher")
    public ResponseEntity<?> getPostByTeacher(@RequestParam String teacher) {
        // 获取教师的所有帖子
        // 前端应显示所有帖子
        List<Forum_post> posts = forumService.getPostsByTeacher(teacher);
        return getResponseEntityPost(posts);
    }

    @GetMapping("/getpostbycourse")
    public ResponseEntity<?> getPostByCourse(@RequestParam String course) {
        // 获取课程的所有帖子
        // 前端应显示所有帖子
        List<Forum_post> posts = forumService.getPostsByCourse(course);
        return getResponseEntityPost(posts);
    }

    @GetMapping("/allcourse")
    public ResponseEntity<?> getAllCourse() {
        // 获取所有课程
        // 前端应显示所有课程
        List<String> courses = forumService.getAllCourse();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/allteacher")
    public ResponseEntity<?> getAllTeacher() {
        // 获取所有教师
        // 前端应显示所有教师
        List<Teacher> teachers = forumService.getAllTeacher();
        return getResponseEntityTeacher(teachers);
    }

    // 用户搜索
    // 前端应该在论坛主页面，用户发起搜索时，调用这个接口
    // 参数：通过路径参数的方式传递, 需要courseName或teacherName，search_type("teacher" or "course")
    @GetMapping("/search")
    public ResponseEntity<?> get(@RequestParam String keyword, @RequestParam String type) {
        // 如果courseName非空，返回所有包含courseName的课程名
        // 如果teacherName非空，返回所有包含teacherName的教师名
        // 前端应显示所有课程名或教师名
        List<Forum_post> posts = new ArrayList<>();
        if(type.equals("teacher")){
            posts = forumService.getPostsByTeacher(keyword);
        } else if(type.equals("course")){
            posts = forumService.getPostsByCourse(keyword);
        }
        return getResponseEntityPost(posts);
    }

//    // 展示按照老师搜索的结果：某老师的所有课程
//    // 前端应在用户搜索后，点击某老师时调用这个接口
//    // 参数：通过路径参数的方式传递，需要teacher_id
//    @GetMapping("/{teacher_id}")
//    public ResponseEntity<?> get(@PathVariable String teacher_id) {
//        // 获取教师的所有课程
//        // 前端应显示所有课程
//        ForumResponse response = new ForumResponse();
//        response.setCourses(forumService.getCoursesByTeacherId(teacher_id));
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    // 展示按照课程搜索的结果：某课程的所有老师
//    // 前端应在用户搜索后，点击某课程时调用这个接口
//    // 参数：通过路径参数的方式传递，需要course_id
//    @GetMapping("/{course_id}")
//    public ResponseEntity<?> getCourse(@PathVariable String course_id) {
//        // 获取课程的所有教师
//        // 前端应显示所有教师
//        ForumResponse response = new ForumResponse();
//        response.setTeachers(forumService.getTeachersByCourseId(course_id));
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    // 某老师某课程的论坛页面
//    // 前端应在用户点击某搜索结果时调用这个接口
//    @GetMapping("/{teacher_id}/{course_id}")
//    public ResponseEntity<?> get_a(@PathVariable String teacher_id, @PathVariable String course_id) {
//        // 获取教师或课程的所有帖子
//        // 前端应显示所有帖子
//        List<Forum_post> posts = forumService.getPosts(teacher_id, course_id);
//        ForumResponse response = new ForumResponse();
//        response.setPosts(posts);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    // 用户点赞点踩收藏
    // 前端应在用户发起点赞点踩收藏操作时，用POST方法调用此接口，最后返回到/{teacher_id}/{course_id}，刷新
    // POST数据传递格式是@RequestBody Map<String, String> body，需要operation("like", "dislike", "favor")、direction("up","down")和post_id
    @PostMapping("/operation")
    public ResponseEntity<?> handlePostOperation(@RequestBody Map<String, String> body) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        String school_id = String.valueOf(id);
        //String school_id="12222222222L";
        String forum_id = String.valueOf(forumService.getForumId(school_id));
        String operation = body.get("operation");
        String direction = body.get("direction");
        String post_id = body.get("postId");

        forumService.manageOpt(forum_id, post_id, operation, direction);
        PostResponse postResponse = new PostResponse(forumService.getPostById(Integer.parseInt(post_id)),
                forumService.getTeacherNameByTeacherId(forumService.getPostById(Integer.parseInt(post_id)).getTeacher_id()),
                forumService.getCourseNameByCourseId(forumService.getPostById(Integer.parseInt(post_id)).getCourse_id()),
                forumService.isLiked(post_id, forum_id), forumService.isDisliked(post_id, forum_id), forumService.isFavored(post_id, forum_id));
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }


    // 用户发帖
    // 前端应在用户发起发帖操作时，用POST方法调用此接口，最后返回到/{teacher_id}/{course_id}，刷新
    // POST数据传递格式是@RequestBody Map<String, String> body，需要content，grade
    @PostMapping("/post")
    public ResponseEntity<?> post(@RequestBody Map<String, String> body) {
        String teacher = body.get("teacher");
        String course = body.get("course");
        String content = body.get("content");
        String grade = body.get("grade");
        Map<String, Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        String school_id = String.valueOf(id);
        //String school_id="12222222222";
        String forum_id = String.valueOf(forumService.getForumId(school_id));
        String teacher_id = String.valueOf(forumService.getTeacherIdByTeacherName(teacher));
        String course_id = String.valueOf(forumService.getCourseIdByCourseName(course));
        if(forumService.addPost(forum_id, teacher_id, course_id, content, grade)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else{
            return new ResponseEntity<>("Invalid teacher_id or course_id", HttpStatus.BAD_REQUEST);
        }
    }

    // 展示所有该用户点赞的帖子
    // 前端应在用户点击显示点赞帖子时调用这个接口
    // 参数：通过路径参数的方式传递，需要school_id
    @GetMapping("/like")
    public ResponseEntity<?> getLike() {
        // 获取用户点赞的所有帖子
        // 前端应显示所有帖子
        Map<String, Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        String school_id = String.valueOf(id);
        //String school_id="12222222222";
        String forum_id = String.valueOf(forumService.getForumId(school_id));
        List<Forum_post> posts = forumService.getLikePosts(forum_id);
        return getResponseEntityPost(posts);
    }

    // 展示所有该用户收藏的帖子
    // 前端应在用户点击显示收藏帖子时调用这个接口
    // 参数：通过路径参数的方式传递，需要school_id
    @GetMapping("/favor")
    public ResponseEntity<?> getCollect() {
        // 获取用户收藏的所有帖子
        // 前端应显示所有帖子
        Map<String, Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        String school_id = String.valueOf(id);
        //String school_id = "12222222222";
        String forum_id = String.valueOf(forumService.getForumId(school_id));
        List<Forum_post> posts = forumService.getCollectPosts(forum_id);
        return getResponseEntityPost(posts);
    }

    // 展示所有该用户发表的帖子
    // 前端应在用户点击显示发表帖子时调用这个接口
    // 参数：通过路径参数的方式传递，需要school_id
    @GetMapping("/pub")
    public ResponseEntity<?> getPost() {
        // 获取用户发表的所有帖子
        // 前端应显示所有帖子
        Map<String, Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        String school_id = String.valueOf(id);
        //String school_id = "12222222222";
        String forum_id = String.valueOf(forumService.getForumId(school_id));
        List<Forum_post> posts = forumService.getPostPosts(forum_id);
        return getResponseEntityPost(posts);
    }

    // 展示所有用户点踩的帖子
    // 前端应在用户点击显示点踩帖子时调用这个接口
    // 参数：通过路径参数的方式传递，需要school_id
    @GetMapping("/dislike")
    public ResponseEntity<?> getDislike() {
        // 获取用户点踩的所有帖子
        // 前端应显示所有帖子
        Map<String, Object> map = ThreadLocalUtil.get();
        Long id = (Long) map.get("id");
        String school_id = String.valueOf(id);
        //String school_id = "12222222222";
        String forum_id = String.valueOf(forumService.getForumId(school_id));
        List<Forum_post> posts = forumService.getDislikePosts(forum_id);
        return getResponseEntityPost(posts);
    }

    // 热门老师
    // 前端应在用户点击热门老师时调用这个接口
    @GetMapping("/hotTeacher")
    public ResponseEntity<?> getHotTeacher() {
        // 获取热门老师
        // 前端应显示所有热门老师
        List<Teacher> teachers = forumService.getHotTeacher();
        return getResponseEntityTeacher(teachers);
    }

    //  热门课程
    // 前端应在用户点击热门课程时调用这个接口
    @GetMapping("/hotCourse")
    public ResponseEntity<?> getHotCourse() {
        // 获取热门课程
        // 前端应显示所有热门课程
        List<Course> courses = forumService.getHotCourse();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }


    //展示所有带有敏感词的帖子
    //前端应在用户点击显示敏感词帖子时调用这个接口
    //参数：无
    @GetMapping("/manager/sensitivePosts")
    public ResponseEntity<?> getPostsWithSensitiveWords() {
        List<Forum_post> posts = forumService.findPostsWithSensitiveWords();
        return getResponseEntityPost(posts);
    }

    //管理员删除帖子、置顶、取消置顶
    //前端应在用户点击删除帖子、置顶、取消置顶时调用这个接口
    //参数：数据传递格式是@RequestBody Map<String, String> body 通过的方式传递，需要post_id、operation("delete", "top", "cancel_top")
    @PostMapping("/manager/adminOperation")
    public ResponseEntity<?> deletePost(@RequestBody Map<String, String> body) {
        String post_id = body.get("postId");
        String operation = body.get("operation") ;
        if(forumService.adminOpt(post_id, operation))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>("fail to operate", HttpStatus.BAD_REQUEST);
    }

    //管理员批量删除
    //参数：ids
    @PostMapping("/manager/post/batch-delete")
    public ResponseEntity<?> batchDelete(@RequestBody Map<String, List<Long>> body) {
        List<Long> ids=body.get("ids");
        if(forumService.batchDelete(ids))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>("fail to operate", HttpStatus.BAD_REQUEST);
    }

    // 管理员获取所有帖子
    @GetMapping("/manager/getAll")
    public ResponseEntity<?> getAll() {
        // 获取所有帖子
        // 前端应显示所有帖子
        List<Forum_post> posts = forumService.findAllPosts();
        return getResponseEntityPost(posts);
    }
}
