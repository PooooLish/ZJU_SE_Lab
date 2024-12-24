package com.backend.academicsys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.backend.academicsys.dto.*;
import com.backend.academicsys.entity.Exam;
import com.backend.academicsys.entity.Result;
import com.backend.academicsys.mapper.CourseMapper;
import com.backend.academicsys.mapper.Course_studentMapper;
import com.backend.academicsys.mapper.Course_teacherMapper;
import com.backend.academicsys.mapper.ExamMapper;
import com.backend.academicsys.mapper.StudentMapper;
import com.backend.academicsys.mapper.Student_rankMapper;

import com.backend.academicsys.utils.ThreadLocalUtil;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import com.backend.academicsys.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.academicsys.service.ExamService;

@RestController
@RequestMapping("/exam")
public class ExamController {
    private static final Logger logger = LoggerFactory.getLogger(ExamController.class);

    @Autowired
    private ExamService examService;
    @Autowired
    private Course_studentMapper course_studentMapper;
    @Autowired
    private Course_teacherMapper course_teacherMapper;
    @Autowired
    private Student_rankMapper student_rankMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean checkRole(Long Id, String role) {
        char firstDigit = Id.toString().charAt(0);
        if (role.equals("student") && firstDigit == '3') {
            return true;
        } else if (role.equals("teacher") && firstDigit == '2') {
            return true;
        } 
        return false;
    }

    @GetMapping("/teacher/exam_score")
    public Result<List<CourseStudentDto>> getScoresByTeacherId(
            // Long teacherId,
            String year,
            String semester,
            String course_name
    ){
        Map<String, Object> map = ThreadLocalUtil.get();
        Long teacherId = Long.parseLong(map.get("id").toString());
        if (!checkRole(teacherId, "teacher"))  return Result.fail("查询失败");

        try {
            List<CourseStudentDto> scores = examService.getStudentsByTeacherIdAndCourseNameAndSemester(teacherId, course_name, year, semester);
            logger.info("score: {}", scores);
            if (scores == null || scores.isEmpty()) {
                return Result.fail("查询失败");
            } 
            return new Result<>(200, "查询成功", scores);
        } catch (Exception e) {
            return Result.fail("查询失败");
        }
    }

    @PostMapping("/teacher/set_scores")
    public Result<String> setScoresByTeacher(ScoreDto scoreDto
    ){
        Map<String, Object> map = ThreadLocalUtil.get();
        Long teacherId = Long.parseLong(map.get("id").toString());
        if (!checkRole(teacherId, "teacher"))  return Result.fail("查询失败");

        boolean is_pass = true;
        String course_name = scoreDto.getCourseName();
        Long studentId = scoreDto.getStudentId();
        double grade = scoreDto.getGrade();
        double ord_grade = scoreDto.getOrdGrade();
        double exam_grade = scoreDto.getExamGrade();
        double retake_grade = scoreDto.getRetakeGrade();
        logger.info("scoreDto: {}", scoreDto);

        if (grade < 60)
            is_pass = false;
        try {
            Long courseId = courseMapper.findCourseByFuzzyCourseName(course_name);
            course_studentMapper.setScoresByTeacher(studentId, courseId, grade, ord_grade, exam_grade, is_pass, retake_grade);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.fail("修改失败");
        }

    }

    @GetMapping("/teacher/exam_info")
    public Result<List<ExamTeacherDto>> getExamsByTeacherAndSemester(
            // Long teacherId,
            String year,
            String semester) {
        // 调用服务层方法获取教师在指定学期的考试信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Long teacherId = Long.parseLong(map.get("id").toString());
        if (!checkRole(teacherId, "teacher"))  return Result.fail("查询失败");

        try{
            List<ExamTeacherDto> exams = examService.getExamsByTeacherAndSemesters(teacherId, year, semester);
            if (exams == null || exams.isEmpty()) {
                return Result.fail("查询失败");
            }
            return new Result<>(200, "查询成功", exams);
        } catch (Exception e) {
            return Result.fail("查询失败");
        }
        
    }

    @GetMapping("/teacher/course_info")
    public Result<List<String>> getCoursesByTeacherAndSemester(
            // Long teacherId,
            String year,
            String semester) {
        // 调用服务层方法获取教师在指定学期的课程信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Long teacherId = Long.parseLong(map.get("id").toString());
        if (!checkRole(teacherId, "teacher"))  return Result.fail("查询失败");

        try {
            List<String> courses_names = examService.getCoursesByTeacherAndSemesters(teacherId, year, semester);
            if (courses_names == null || courses_names.isEmpty()) {
                return Result.fail("查询失败");
            }
            return new Result<>(200, "查询成功", courses_names);
        } catch (Exception e) {
            return Result.fail("查询失败");
        }
    }


    @GetMapping("/student/exam_info")
    public Result<List<ExamStudentDto>> getExamsByStudentAndSemester(
            // Long studentId,
            String year,
            String semester) {
        // 调用服务层方法获取学生在指定学期的考试信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Long studentId = Long.parseLong(map.get("id").toString());
        if (!checkRole(studentId, "student"))  return Result.fail("查询失败");

        try {
            List<ExamStudentDto> exams = examService.getExamsByStudentAndSemesters(studentId, year, semester);
            if (exams == null || exams.isEmpty()) {
                return Result.fail("查询失败");
            }
            return new Result<>(200, "查询成功", exams);
        } catch (Exception e) {
            return Result.fail("查询失败");
        } 
    }

    
    @GetMapping("/student/exam_score")
    public Result<List<ScoreStudentDto>> getScoresByStudentId(
            // Long studentId, 
            String year,
            String semester
    ){
        Map<String, Object> map = ThreadLocalUtil.get();
        Long studentId = Long.parseLong(map.get("id").toString());
        if (!checkRole(studentId, "student"))  return Result.fail("查询失败");

        try {
            List<ScoreStudentDto> scores = examService.getScoresByStudentIdAndSemester(studentId, year, semester);
            logger.info("score: {}", scores);
            if (scores == null || scores.isEmpty()) {
                return Result.fail("查询失败");
            }
            return new Result<>(200, "查询成功", scores);
        } catch (Exception e) {
            return Result.fail("查询失败");
        }
    }

    @GetMapping("/student/rankall")
    public Result<List<RankDto>> getRankByStudentId(
            // Long studentId
    ){
        Map<String, Object> map = ThreadLocalUtil.get();
        Long studentId = Long.parseLong(map.get("id").toString());
        if (!checkRole(studentId, "student"))  return Result.fail("查询失败");

        try {
            List<RankDto> rank = student_rankMapper.findRanksByStudentId(studentId);
            logger.info("rank: {}", rank);
            return new Result<>(200, "查询成功", rank);
        } catch (Exception e) {
            return Result.fail("查询失败");
        } 
    }


    @GetMapping("/student/current_ranking")
    public Result<CurrentRankDto> getCurrentRankByStudentId(
            // Long studentId
    ){
        Map<String, Object> map = ThreadLocalUtil.get();
        Long studentId = Long.parseLong(map.get("id").toString());
        if (!checkRole(studentId, "student"))  return Result.fail("查询失败");

        try {
            CurrentRankDto rank = studentMapper.findCurrentRankByStudentId(studentId);
            logger.info("rank: {}", rank);
            return new Result<>(200, "查询成功", rank);
        } catch (Exception e) {
            return Result.fail("查询失败");
        } 
    }

/*
    @PutMapping("/student/course/defer")
    public Result<String> markStudentDeferForCourse(
            String courseName,
            Long studentId) {
        // 调用服务层方法标记学生在指定课程中为缓考
        try {
            Long courseId = courseMapper.findCourseByFuzzyCourseName(courseName);
            examService.markStudentDeferForCourse(courseId, studentId);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.fail("修改失败");
        }
    }


    @PutMapping("/student/course/resit")
    public Result<String> markStudentResitForCourse(
            String courseName,
            Long studentId) {
        // 调用服务层方法标记学生在指定课程中为补考
        try {
            Long courseId = courseMapper.findCourseByFuzzyCourseName(courseName);
            examService.markStudentResitForCourse(courseId, studentId);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.fail("修改失败");
        } 
    }
*/

    @GetMapping("/semester")
    public Result<List<SemesterIdDto>> getSemesters() {
        // 调用服务层方法获取所有学期
        List<SemesterIdDto> semesters = examService.getSemesterIds();
        return new Result<>(200, "查询成功", semesters);
    }

}
