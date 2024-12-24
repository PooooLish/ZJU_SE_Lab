package com.backend.academicsys.controller;


import com.backend.academicsys.entity.Course;
import com.backend.academicsys.entity.Course_student;
import com.backend.academicsys.mapper.CourseMapper;
import com.backend.academicsys.mapper.Course_studentMapper;
import com.backend.academicsys.service.CourseService;
import com.backend.academicsys.service.Course_studentService;
import com.backend.academicsys.utils.Result;
import com.backend.academicsys.utils.ThreadLocalUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.backend.academicsys.mapper.Course_studentMapper;
import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class Course_studentController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private Course_studentService course_studentService;
    @Autowired
    private Course_studentMapper course_studentMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    Long get_school_id(){
        Map <String, Object> map  = ThreadLocalUtil.get();
        return (Long)map.get("id");

    }

    Boolean timeOverLap(String start1, String end1, String start2, String end2){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startDateTime1 = LocalDateTime.parse(start1, formatter);
        LocalDateTime endDateTime1 = LocalDateTime.parse(end1, formatter);
        LocalDateTime startDateTime2 = LocalDateTime.parse(start2, formatter);
        LocalDateTime endDateTime2 = LocalDateTime.parse(end2, formatter);

        return startDateTime1.isBefore(endDateTime2) && startDateTime2.isBefore(endDateTime1);
    }//若重叠则返回true

    @PostMapping("/usr/student/selectCourse")
    Result selectCourse(@RequestBody Map<String, Integer> payload) {
        Long student_id = get_school_id();
        Integer course_id = payload.get("course_id");

        Course course = courseService.findCourseByCourseId(course_id);
        System.out.println(course);
        if(course.getNumStudents()>course.getMaxStudents()) return Result.error().message("该课程已经选满！");
        String course_start_time = course.getCourseStartTime();
        System.out.println(course_start_time);
        String course_end_time = course.getCourseEndTime();
        String test_start_time = course.getTestStartTime();
        String test_end_time = course.getTestEndTime();

        List<Course_student> hasChosen = course_studentService.findCoursesBySid(student_id);
        for(Course_student i: hasChosen){
            int cid = i.getCourseId();
            Course chosenCourse = courseService.findCourseByCourseId(cid);

            if(timeOverLap(course_start_time, course_end_time,
                    chosenCourse.getCourseStartTime(), chosenCourse.getCourseEndTime())
                    &&
                    timeOverLap(test_start_time, test_end_time,
                            chosenCourse.getTestStartTime(), chosenCourse.getTestEndTime()))
                return Result.error().message("该课程与"+chosenCourse.getCourseName()+"时间冲突！");

        }//查询得到已选课程信息，并比对时间是否冲突

        Date currentDate = new Date();
        Course_student new_record = new Course_student( course_id, (long)student_id, currentDate, "待定", 0, 0);
        course_studentService.insertCourseStudent(new_record);
        return Result.ok();
    }

    @GetMapping("/usr/student/courseSelectionInformation")
    Result CourseSelectionInformation(){
        Long student_id = get_school_id();

        List<Course_student> hasChosen = course_studentService.findCoursesBySid(student_id);
        return Result.ok().data("info", hasChosen);
    }

    @DeleteMapping("/usr/student/dropCourse")
    Result dropCourse(@RequestBody Map<String, Integer> payload){
        Long student_id = get_school_id();
        Integer course_id = payload.get("course_id");

        course_studentService.deleteCourseStudentBySidCid(student_id, course_id);
        return Result.ok();
    }

    @PostMapping("/admin/screenStudents")
    public Result screenStudents() {

        List<Course_student> pendingStudents = course_studentMapper.SelectStudent();


        // 按课程ID分组
        Map<Integer, List<Course_student>> groupedByCourse = pendingStudents.stream()
                .collect(Collectors.groupingBy(Course_student::getCourseId));

        for (Map.Entry<Integer, List<Course_student>> entry : groupedByCourse.entrySet()) {
            int courseId = entry.getKey();
            List<Course_student> students = entry.getValue();

            Course course = courseMapper.selectCourseById(courseId);
            int availableSlots = course.getMaxStudents() - course.getNumStudents();

            if (availableSlots <= 0) {
                // 没有空位的课程，所有待定申请标记为未通过
                for (Course_student student : students) {
                    int id = student.getCourseStudentId();
                    course_studentMapper.rejectById(id);
                }
            } else if (students.size() <= availableSlots) {
                // 申请人数小于或等于空位数，全部申请通过
                for (Course_student student : students) {
                    int id = student.getCourseStudentId();
                    // course.setNum_students(course.getNum_students() + 1);
                    // course_studentMapper.acceptById(id);
                }
                int num = course.getNumStudents();
                courseMapper.set_num_student(courseId, num);
            } else {
                // 申请人数超过空位数，随机选出最大容量允许的人数
                Collections.shuffle(students);
                List<Course_student> selectedStudents = students.subList(0, availableSlots);
                List<Course_student> notSelectedStudents = students.subList(availableSlots, students.size());

                for (Course_student student : selectedStudents) {
                    int id = student.getCourseStudentId();
                    course_studentMapper.acceptById(id);
                }

                for (Course_student student : notSelectedStudents) {
                    int id = student.getCourseStudentId();
                    course_studentMapper.rejectById(id);
                }

                courseMapper.set_num_student(courseId, availableSlots);
            }
        }

        return Result.ok().message("筛选完毕");
    }
}
