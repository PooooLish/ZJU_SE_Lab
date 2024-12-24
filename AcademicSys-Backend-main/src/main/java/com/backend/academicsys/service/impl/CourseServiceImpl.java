package com.backend.academicsys.service.impl;

import static java.sql.Types.NULL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.backend.academicsys.entity.Course;
import com.backend.academicsys.entity.Result;
import com.backend.academicsys.entity.User;
import com.backend.academicsys.mapper.CourseMapper;
import com.backend.academicsys.mapper.Course_teacherMapper;
import com.backend.academicsys.service.CourseService;
import com.backend.academicsys.service.UserService;

import jakarta.annotation.Resource;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private Course_teacherMapper course_teacherMapper;

    @Resource
    private UserService userService;

    // @Autowired
    // CourseMapper courseMapper;

    @Override
    public Result<List<Course>> findByCondition(Map<String, Object> condition) {
        // 如果 condition 为空，返回所有课程
        List<Course> courses;
        if (condition == null || condition.isEmpty()) {
            courses = courseMapper.findAll();
        }
        else {
            courses = courseMapper.findByCondition(condition);
        }
        // Add teachers to courses
        for (Course course : courses) {
            List<Map<String, Object>> teachers = new ArrayList<>();
            List<Long> teacherIds = course_teacherMapper.findTeachersByCourseId(course.getCourseId());
            for (Long teacherId : teacherIds) {
                User teacher = userService.findTeacher(teacherId);
                if (teacher != null) {
                    teachers.add(Map.of(
                        "teacherId", teacher.getSchoolId(),
                        "teacherName", teacher.getUserName()
                    ));
                }
            }
            course.setTeachers(teachers);
        }
        // Add tas to courses
        for (Course course : courses) {
            List<Map<String, Object>> tas = new ArrayList<>();
            List<Long> taIds = course_teacherMapper.findTAsByCourseId(course.getCourseId());
            for (Long taId : taIds) {
                User ta = userService.findUser(taId);
                if (ta != null) {
                    tas.add(Map.of(
                        "taId", ta.getSchoolId(),
                        "taName", ta.getUserName()
                    ));
                }
            }
            course.setTas(tas);
        }
        return Result.success(courses);
    }

    @Override
    public Result<String> deleteById(Course course) {
        int courseId = course.getCourseId();
        if (courseId == NULL) {
            System.out.println("Failed to delete course since courseId is invalid.");
            return Result.fail("删除失败，未指定 course_id");
        }
        // 检查 Course 是否存在
        if (courseMapper.findById(courseId) == null) {
            System.out.printf("Failed to delete course with id %d since it does not exist.\n", courseId);
            return Result.fail("删除失败，请检查 course_id 是否存在");
        }
        if (courseMapper.deleteById(courseId)) {
            System.out.printf("Course with id %d is deleted successfully.\n", courseId);
            return Result.success("删除成功");
        }
        else {
            System.out.printf("Failed to delete course with id %d.\n", courseId);
            return Result.fail("删除失败");
        }
    }

    @Override
    public Result<String> createCourse(Course course) {
        int courseId = course.getCourseId();
        if (courseMapper.findById(courseId) != null) {
            System.out.printf("Course with id %d can not created since there has been the one with the same id.\n", courseId);
            return Result.fail("添加失败，已存在相同 course_id 的课程");
        }
        if (courseMapper.createCourse(course)) {
            System.out.printf("Course with id %d is created successfully.\n", courseId);
            return Result.success("添加成功");
        }
        else {
            System.out.printf("Failed to create course with id %d.\n", courseId);
            return Result.fail("添加失败");
        }
    }

    @Override
    public Result<String> modCourse(Course course) {
        int courseId = course.getCourseId();
        if (courseMapper.findById(courseId) == null) {
            System.out.printf("Failed to modify! Please create the course with id %d first\n", courseId);
            return Result.fail("修改失败，不存在该 course_id 的课程");
        }
        if (courseMapper.modCourse(course)) {
            System.out.printf("Successfully modified course %d.\n", courseId);
            return Result.success("修改成功");
        }
        else {
            System.out.printf("Failed to modify course %d.\n", courseId);
            return Result.fail("修改失败");
        }
    }

    @Override
    public Result<List<Map<String, Object>>> getAllTeachers(Course course) {
        int courseId = course.getCourseId();
        if (courseMapper.findById(courseId) == null) {
            System.out.println("Failed to get teachers since courseId is invalid.");
            return Result.fail("查询失败，不存在该 course_id 的课程");
        }
        if (courseMapper.findById(courseId) == null) {
            System.out.println("Failed to get teachers since courseId is invalid.");
            return Result.fail("查询失败，不存在该 course_id 的课程");
        }
        List<Long> teacherIds = course_teacherMapper.findTeachersByCourseId(courseId);
        List<Map<String, Object>> teachers = new ArrayList<>();
        for (Long teacherId : teacherIds) {
            User teacher = userService.findTeacher(teacherId);
            if (teacher != null) {
                teachers.add(Map.of(
                    "teacherId", teacher.getSchoolId(),
                    "teacherName", teacher.getUserName()
                ));
            }
        }
        return Result.success(teachers);
    }

    @Override
    public Result<List<Map<String, Object>>> getAllTAs(Course course) {
        int courseId = course.getCourseId();
        if (courseMapper.findById(courseId) == null) {
            System.out.println("Failed to get TAs since courseId is invalid.");
            return Result.fail("查询失败，不存在该 course_id 的课程");
        }
        if (courseMapper.findById(courseId) == null) {
            System.out.println("Failed to get TAs since courseId is invalid.");
            return Result.fail("查询失败，不存在该 course_id 的课程");
        }
        List<Long> taIds = course_teacherMapper.findTAsByCourseId(courseId);
        List<Map<String, Object>> tas = new ArrayList<>();
        for (Long taId : taIds) {
            User ta = userService.findUser(taId);
            if (ta != null) {
                tas.add(Map.of(
                    "taId", ta.getSchoolId(),
                    "taName", ta.getUserName()
                ));
            }
        }
        return Result.success(tas);
    }

    @Override
    public Result<String> addTeacher(int courseId, Long teacherId) {
        // 检查 courseId 和 teacherId 是否有效
        if (courseMapper.findById(courseId) == null) {
            System.out.println("Failed to add teacher since courseId is invalid.");
            return Result.fail("添加失败，不存在该 course_id 的课程");
        }
        if (userService.findTeacher(teacherId) == null) {
            System.out.println("Failed to add teacher since teacherId is invalid.");
            return Result.fail("添加失败，不存在该 teacher_id 的老师");
        }
        // 检查 course_teacher 表中是否存在该记录
        if (course_teacherMapper.findTeacherByCourseIdAndTeacherId(courseId, teacherId) != 0) {
            System.out.println("Failed to add teacher since the record already exists.");
            return Result.fail("添加失败，记录已存在");
        }
        // 添加老师
        if (course_teacherMapper.addTeacher(courseId, teacherId)) {
            System.out.println("Successfully added teacher.");
            return Result.success("添加成功");
        }
        else {
            System.out.println("Failed to add teacher.");
            return Result.fail("添加失败");
        }
    }

    @Override
    public Result<String> addTA(int courseId, Long taId) {
        // 检查 courseId 和 taId 是否有效
        if (courseMapper.findById(courseId) == null) {
            System.out.println("Failed to add TA since courseId is invalid.");
            return Result.fail("添加失败，不存在该 course_id 的课程");
        }
        if (userService.findUser(taId) == null) {
            System.out.println("Failed to add TA since taId is invalid.");
            return Result.fail("添加失败，不存在该 ta_id 的用户");
        }
        // 检查 course_teacher 表中是否存在该记录
        if (course_teacherMapper.findTAByCourseIdAndTAId(courseId, taId) != 0) {
            System.out.println("Failed to add TA since the record already exists.");
            return Result.fail("添加失败，记录已存在");
        }
        // 添加助教
        if (course_teacherMapper.addTA(courseId, taId)) {
            System.out.println("Successfully added TA.");
            return Result.success("添加成功");
        }
        else {
            System.out.println("Failed to add TA.");
            return Result.fail("添加失败");
        }
    }

    @Override
    public Result<String> deleteTeacher(int courseId, Long teacherId) {
        // 检查 courseId 和 teacherId 是否有效
        if (courseMapper.findById(courseId) == null) {
            System.out.println("Failed to delete teacher since courseId is invalid.");
            return Result.fail("删除失败，不存在该 course_id 的课程");
        }
        if (userService.findTeacher(teacherId) == null) {
            System.out.println("Failed to delete teacher since teacherId is invalid.");
            return Result.fail("删除失败，不存在该 teacher_id 的老师");
        }
        // 检查 course_teacher 表中是否存在该记录
        if (course_teacherMapper.findTeacherByCourseIdAndTeacherId(courseId, teacherId) == 0) {
            System.out.println("Failed to delete teacher since the record does not exist.");
            return Result.fail("删除失败，不存在该记录");
        }
        // 删除老师
        if (course_teacherMapper.deleteTeacher(courseId, teacherId)) {
            System.out.println("Successfully deleted teacher.");
            return Result.success("删除成功");
        }
        else {
            System.out.println("Failed to delete teacher.");
            return Result.fail("删除失败");
        }
    }
    @Override
    public Course findCourseByCourseId(int course_id) {
        return courseMapper.getCourseById(course_id);
    }
    public List<Course> findCourseByCourseCredit(int credit){
        return courseMapper.selectCredit_course(credit);
    }
    public List<Course> findCourseByCourseDepartment(String department){
        return courseMapper.selectDepartment_course(department);
    }
    public List<Course> findCourseByCourseCategory(String category){
        return courseMapper.selectCategory_course(category);
    }
    public List<Course> findCourseByCourseSemester(String semester){
        return courseMapper.selectSemester_course(semester);
    }
    public List<Course> findCourseByCourseLocation(String location){
        return courseMapper.selectLocation_course(location);
    }
    public List<Course> findCourseByCourseTarget(String target){
        return courseMapper.selectTarget_course(target);
    }
    public List<Course> findCourseByCourseName(String name){
        return courseMapper.selectName_course(name);
    }

    @Override
    public Result<String> deleteTA(int courseId, Long taId) {
        // 检查 courseId 和 taId 是否有效
        if (courseMapper.findById(courseId) == null) {
            System.out.println("Failed to delete TA since courseId is invalid.");
            return Result.fail("删除失败，不存在该 course_id 的课程");
        }
        if (userService.findUser(taId) == null) {
            System.out.println("Failed to delete TA since taId is invalid.");
            return Result.fail("删除失败，不存在该 ta_id 的用户");
        }
        // 检查 course_teacher 表中是否存在该记录
        if (course_teacherMapper.findTAByCourseIdAndTAId(courseId, taId) == 0) {
            System.out.println("Failed to delete TA since the record does not exist.");
            return Result.fail("删除失败，不存在该记录");
        }
        // 删除助教
        if (course_teacherMapper.deleteTA(courseId, taId)) {
            System.out.println("Successfully deleted TA.");
            return Result.success("删除成功");
        }
        else {
            System.out.println("Failed to delete TA.");
            return Result.fail("删除失败");
        }
    }
}
