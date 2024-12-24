package com.backend.academicsys.controller;

import static java.sql.Types.NULL;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.academicsys.entity.Course;
import com.backend.academicsys.entity.Result;
import com.backend.academicsys.mapper.CourseMapper;
import com.backend.academicsys.service.CourseService;
import com.backend.academicsys.utils.ThreadLocalUtil;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;

    @PostMapping("/getCourse")
    public Object getCourse(@RequestBody Map<String, Object> condition) {
        return courseService.findByCondition(condition);
    }

    @PostMapping("/delCourse")
    public Object delCourse(@RequestBody Course course) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        if ((int) (schoolId / 1e10) != 1) {
            return Result.fail("只有管理员可以删除课程信息");
        }
        return courseService.deleteById(course);
    }

    // 现在要求新建 course 的时候自带 course_id 而不是让数据库生成，虽然看起来比较傻逼但是暂时懒得改了
    // btw createTextbook 也这样
    @PostMapping("/createCourse")
    public Object createCourse(@RequestBody Course course) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        if ((int) (schoolId / 1e10) != 1) {
            return Result.fail("只有管理员可以创建课程信息");
        }
        return courseService.createCourse(course);
    }

    // 现在要求修改的 course 必须传入完整属性（相当于直接覆盖本来的 course，待改善
    @PostMapping("/modCourse")
    public Object modCourse(@RequestBody Course course) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        if ((int) (schoolId / 1e10) != 1) {
            return Result.fail("只有管理员可以修改课程信息");
        }
        return courseService.modCourse(course);
    }

    // 获取课程的所有老师
    @PostMapping("/getAllTeachers")
    public Object getAllTeachers(@RequestBody Course course) {
        return courseService.getAllTeachers(course);
    }

    // 获取课程的所有助教
    @PostMapping("/getAllTAs")
    public Object getAllTAs(@RequestBody Course course) {
        return courseService.getAllTAs(course);
    }

    // 添加课程的老师
    @PostMapping("/addTeacher")
    public Object addTeacher(@RequestBody Map<String, Object> map) {
        Map<String, Object> threadMap = ThreadLocalUtil.get();
        Long schoolId = (Long) threadMap.get("id");
        if ((int) (schoolId / 1e10) != 1) {
            return Result.fail("只有管理员可以添加课程老师");
        }
        int courseId = Integer.parseInt(map.get("courseId").toString());
        Long teacherId = Long.valueOf(map.get("teacherId").toString());
        // 检查 courseId 和 teacherId 是否为空
        if (courseId == NULL) {
            return Result.fail("未指定 course_id，添加失败");
        }
        if (teacherId == NULL) {
            return Result.fail("未指定 teacher_id，添加失败");
        }
        // 调用 service 层的方法
        return courseService.addTeacher(courseId, teacherId);
    }

    // 添加课程的助教
    @PostMapping("/addTA")
    public Object addTA(@RequestBody Map<String, Object> map) {
        Map<String, Object> threadMap = ThreadLocalUtil.get();
        Long schoolId = (Long) threadMap.get("id");
        if ((int) (schoolId / 1e10) != 1) {
            return Result.fail("只有管理员可以添加课程助教");
        }
        int courseId = Integer.parseInt(map.get("courseId").toString());
        Long taId = Long.valueOf(map.get("taId").toString());
        // 检查 courseId 和 taId 是否为空
        if (courseId == NULL) {
            return Result.fail("未指定 course_id，添加失败");
        }
        if (taId == NULL) {
            return Result.fail("未指定 ta_id，添加失败");
        }
        // 调用 service 层的方法
        return courseService.addTA(courseId, taId);
    }

    // 删除课程的老师
    @PostMapping("/delTeacher")
    public Object deleteTeacher(@RequestBody Map<String, Object> map) {
        Map<String, Object> threadMap = ThreadLocalUtil.get();
        Long schoolId = (Long) threadMap.get("id");
        if ((int) (schoolId / 1e10) != 1) {
            return Result.fail("只有管理员可以删除课程老师");
        }
        int courseId = Integer.parseInt(map.get("courseId").toString());
        Long teacherId = Long.valueOf(map.get("teacherId").toString());
        // 检查 courseId 和 teacherId 是否为空
        if (courseId == NULL) {
            return Result.fail("未指定 course_id，删除失败");
        }
        if (teacherId == NULL) {
            return Result.fail("未指定 teacher_id，删除失败");
        }
        // 调用 service 层的方法
        return courseService.deleteTeacher(courseId, teacherId);
    }

    // 删除课程的助教
    @PostMapping("/delTA")
    public Object deleteTA(@RequestBody Map<String, Object> map) {
        Map<String, Object> threadMap = ThreadLocalUtil.get();
        Long schoolId = (Long) threadMap.get("id");
        if ((int) (schoolId / 1e10) != 1) {
            return Result.fail("只有管理员可以删除课程助教");
        }
        int courseId = Integer.parseInt(map.get("courseId").toString());
        Long taId = Long.valueOf(map.get("taId").toString());
        // 检查 courseId 和 taId 是否为空
        if (courseId == NULL) {
            return Result.fail("未指定 course_id，删除失败");
        }
        if (taId == NULL) {
            return Result.fail("未指定 ta_id，删除失败");
        }
        // 调用 service 层的方法
        return courseService.deleteTA(courseId, taId);

    }

    @GetMapping("/usr/student/course")
    public Object CourseSelectionAll() {
        List<Course> courses = courseMapper.selectAll_Course();
        return Result.success( courses);
    }

    @GetMapping("/usr/student/course/category/{category}")
    public Object CourseSelectionCategory(@PathVariable("category") String category) {
        List<Course> courses = courseMapper.selectCategory_course(category);
        return Result.success( courses);
    }

    @GetMapping("usr/student/course/id/{course_id}")
    public Object CourseSelectionId(@PathVariable int course_id){
        Course course = courseMapper.selectId_course(course_id);
        return Result.success(course);
    }
    @GetMapping("/usr/student/course/course_name/{course_name}")
    public Object CourseSelectionName(@PathVariable("course_name") String course_name) {
        List<Course> courses = courseMapper.selectName_course(course_name);
        return Result.success( courses);
    }
    @GetMapping("/usr/student/course/credit/{credit}")
    public Object CourseSelectionCredit(@PathVariable int credit) {
        List<Course> courses = courseMapper.selectCredit_course(credit);
        return Result.success( courses);
    }
    @GetMapping("/usr/student/course/semester/{semester}")
    public Object CourseSelectionSemester(@PathVariable("semester") String semester) {
        List<Course> courses = courseMapper.selectSemester_course(semester);
        return Result.success( courses);
    }
    @GetMapping("/usr/student/course/location/{location}")
    public Object CourseSelectionLocation(@PathVariable("location") String location) {
        List<Course> courses = courseMapper.selectLocation_course(location);
        return Result.success( courses);
    }

}
