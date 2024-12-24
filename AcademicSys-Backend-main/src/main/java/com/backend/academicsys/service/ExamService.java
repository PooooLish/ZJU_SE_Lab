package com.backend.academicsys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.backend.academicsys.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.academicsys.dto.CourseStudentDto;
import com.backend.academicsys.dto.ExamStudentDto;
import com.backend.academicsys.dto.ExamTeacherDto;
import com.backend.academicsys.dto.ScoreStudentDto;
import com.backend.academicsys.dto.SemesterIdDto;
import com.backend.academicsys.entity.Course;
import com.backend.academicsys.entity.Course_student;
import com.backend.academicsys.entity.Exam;
import com.backend.academicsys.entity.Exam_student;
import com.backend.academicsys.entity.Exam_teacher;
import com.backend.academicsys.mapper.CourseMapper;
import com.backend.academicsys.mapper.Course_studentMapper;
import com.backend.academicsys.mapper.Course_teacherMapper;
import com.backend.academicsys.mapper.ExamMapper;
import com.backend.academicsys.mapper.Exam_studentMapper;
import com.backend.academicsys.mapper.Exam_teacherMapper;

@Service
public class ExamService {
    private static final Logger logger = LoggerFactory.getLogger(ExamService.class);

    @Autowired
    private Course_studentMapper courseStudentMapper;

    @Autowired
    private Course_teacherMapper courseTeacherMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private Exam_studentMapper examStudentMapper;

    @Autowired
    private Exam_teacherMapper examTeacherMapper;

    public List<String> merge2semesterId(String year, String semester){
        List<String> semesterIds = new ArrayList<>();
        if (semester.equals("春夏")) {
            semesterIds.add(year + "春");
            semesterIds.add(year + "夏");
            semesterIds.add(year + "春夏");
        } else if (semester.equals("秋冬")) {
            semesterIds.add(year + "秋");
            semesterIds.add(year + "冬");
            semesterIds.add(year + "秋冬");
        } else {
            semesterIds.add(year + semester);
        }
        return semesterIds;
    }

    public List<ExamStudentDto> getExamsByStudentAndSemesters(Long studentId, String year, String semester){
        List<String> semesterId = merge2semesterId(year, semester);
        List<Course> courses = courseMapper.findCoursesByCourseIdsAndSemesters(courseStudentMapper.findCourseIdsByStudentId(studentId), semesterId);
        List<ExamStudentDto> examStudentDtos = new ArrayList<>();
        for (Course course : courses) {
            Long courseId = (long)course.getCourseId();
            Exam exam = examMapper.findExamByCourseId(courseId);
            Long examId = (long)exam.getExamId();
            List<Exam_student> exam_students = examStudentMapper.findExam_studentsByExamIdAndStudentId(examId, studentId);
            for (Exam_student exam_student : exam_students) {
                String exam_semesterId = course.getSemester();
                String time = exam.getExamDate().toString() + exam.getExamStartTime() + "-" + exam.getExamEndTime();
                ExamStudentDto dto = new ExamStudentDto(
                    exam_semesterId,
                    course.getCourseName(),
                    time,
                    exam.getExamLocation(),
                    exam_student.getExamSit()
                );
                examStudentDtos.add(dto);
            }
        }
        
        return examStudentDtos;
    }

    public List<ExamTeacherDto> getExamsByTeacherAndSemesters(Long teacherId, String year, String semester){
        List<String> semesterId = merge2semesterId(year, semester);
        List<Course> courses = courseMapper.findCoursesByCourseIdsAndSemesters(courseTeacherMapper.findCourseIdsByTeacherId(teacherId), semesterId);
        List<ExamTeacherDto> examTeacherDtos = new ArrayList<>();
        for (Course course : courses) {
            Long courseId = (long)course.getCourseId();
            Exam exam = examMapper.findExamByCourseId(courseId);
            String exam_semesterId = course.getSemester();
            ExamTeacherDto dto = new ExamTeacherDto(
                exam_semesterId,
                course.getCourseName(),
                exam.getExamDate(),
                exam.getExamStartTime(),
                exam.getExamEndTime(),
                exam.getExamLocation()
            );
            examTeacherDtos.add(dto);
        }
        return examTeacherDtos;
    }

    public List<CourseStudentDto> getStudentsByTeacherIdAndCourseNameAndSemester(Long teacherId, String courseName, String year, String semester){
        List<String> semesterId = merge2semesterId(year, semester);
        List<Course> courses = courseMapper.findCoursesByCourseIdsAndSemesters(courseTeacherMapper.findCourseIdsByTeacherId(teacherId), semesterId);
        List<CourseStudentDto> courseStudentDtos = new ArrayList<>();
        for (Course course : courses) {
            if (course.getCourseName().equals(courseName)) {
                Long courseId = (long)course.getCourseId();
                List<Course_student> courseStudents = courseStudentMapper.findCoursesByCourseId(courseId);
                logger.info("courseStudents: {}", courseStudents);
                Exam exam = examMapper.findExamByCourseId(courseId);
                for (Course_student courseStudent : courseStudents) {
                    Long studentId = courseStudent.getStudentId();
                    List<Exam_student> exam_students = examStudentMapper.findExam_studentsByExamIdAndStudentId((long)exam.getExamId(), studentId);
                    Boolean defer=false, resit=false;
                    for (Exam_student exam_student : exam_students) {
                        if(exam_student.getDeferOrResit() && exam_student.getExamStatus().equals("缓考")) defer = true;
                        if(exam_student.getDeferOrResit() && exam_student.getExamStatus().equals("补考")) resit = true;
                    }
                    CourseStudentDto dto = new CourseStudentDto(
                        studentId,
                        courseStudent.getGrade(),
                        courseStudent.getOrdGrade(),
                        courseStudent.getExamGrade(),
                        courseStudent.getIsPass(),
                        defer,
                        resit,
                        courseStudent.getRetake()
                    );
                    courseStudentDtos.add(dto);
                }
            }
        }
        return courseStudentDtos;
    }

    public List<String> getCoursesByTeacherAndSemesters(Long teacherId, String year, String semester){
        List<String> semesterId = merge2semesterId(year, semester);
        List<Course> courses = courseMapper.findCoursesByCourseIdsAndSemesters(courseTeacherMapper.findCourseIdsByTeacherId(teacherId), semesterId);
        List<String> courses_name = new ArrayList<>();
        for (Course course : courses) {
            courses_name.add(course.getCourseName());
        }
        return courses_name;
    }

    public List<ScoreStudentDto> getScoresByStudentIdAndSemester(Long studentId, String year, String semester){
        List<String> semesterId = merge2semesterId(year, semester);
        List<Course> courses = courseMapper.findCoursesByCourseIdsAndSemesters(courseStudentMapper.findCourseIdsByStudentId(studentId), semesterId);
        List<ScoreStudentDto> scores = new ArrayList<>();
        for (Course course : courses) {
            Long courseId = (long)course.getCourseId();
            Course_student courseStudent = courseStudentMapper.findCoursesByStudentIdAndCourseId(courseId, studentId);
            ScoreStudentDto dto = new ScoreStudentDto(
                course.getSemester(),
                course.getCourseName(),
                course.getCategory(),
                courseStudent.getGrade(),
                courseStudent.getOrdGrade(),
                courseStudent.getExamGrade(),
                courseStudent.getIsPass()
            );
            scores.add(dto);
        }
        return scores;
    }

    public void markStudentDeferForCourse(Long courseId, Long studentId) {
        List<Exam> exams = examMapper.findExamsByCourseIds(List.of(courseId));
        List<Long> examIds = exams.stream().map(Exam::getExamId).collect(Collectors.toList());
        List<Exam_student> examStudents = examStudentMapper.findExam_studentsByExamIdsAndStudentId(examIds, studentId);
        for (Exam_student examStudent : examStudents) {
            examStudent.setExamStatus("缓考");
            examStudent.setDeferOrResit(true);
            examStudentMapper.updateById(examStudent);
        }
    }

    public void markStudentResitForCourse(Long courseId, Long studentId) {
        List<Exam> exams = examMapper.findExamsByCourseIds(List.of(courseId));
        List<Long> examIds = exams.stream().map(Exam::getExamId).collect(Collectors.toList());
        List<Exam_student> examStudents = examStudentMapper.findExam_studentsByExamIdsAndStudentId(examIds, studentId);
        for (Exam_student examStudent : examStudents) {
            examStudent.setExamStatus("补考");
            examStudent.setDeferOrResit(true);
            examStudentMapper.updateById(examStudent);
        }
    }

    public List<SemesterIdDto> getSemesterIds(){
        List<String> years = new ArrayList<>();
        for (int i = 2020; i <= 2025; i++) {
            years.add(String.valueOf(i));
        }
        List<String> semesters = new ArrayList<>();
        semesters.add("春夏");
        semesters.add("夏");
        semesters.add("春");
        semesters.add("秋冬");
        semesters.add("秋");
        semesters.add("冬");
        List<SemesterIdDto> semesterIds = new ArrayList<>();
        for (String year : years) {
            for (String semester : semesters) {
                SemesterIdDto dto = new SemesterIdDto(year, semester);
                semesterIds.add(dto);
            }
        }
        
        return semesterIds;
    }
}
