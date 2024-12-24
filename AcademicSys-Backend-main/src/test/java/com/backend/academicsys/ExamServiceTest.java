package com.backend.academicsys;

import com.backend.academicsys.dto.CourseStudentDto;
import com.backend.academicsys.dto.ExamStudentDto;
import com.backend.academicsys.dto.ExamTeacherDto;
import com.backend.academicsys.dto.ScoreStudentDto;
import com.backend.academicsys.dto.SemesterIdDto;
import com.backend.academicsys.entity.Course;
import com.backend.academicsys.entity.Exam;
import com.backend.academicsys.entity.Exam_student;
import com.backend.academicsys.mapper.Course_studentMapper;
import com.backend.academicsys.mapper.CourseMapper;
import com.backend.academicsys.mapper.ExamMapper;
import com.backend.academicsys.mapper.Exam_studentMapper;
import com.backend.academicsys.service.ExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ExamServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(ExamServiceTest.class);

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private Exam_studentMapper examStudentMapper;


    @Test
    public void testGetExamsByStudentAndSemester() {
        //根据学生id和学期查找考试成绩
        Long studentId = 3018076543L;
        String year = "2023";
        String semester = "春夏";
        List<ExamStudentDto> examStudentDtos = examService.getExamsByStudentAndSemesters(studentId, year, semester);
        // logger.info("examStudentDtos: {}", examStudentDtos);
        assertNotNull(examStudentDtos);

        assertEquals(3, examStudentDtos.size());
    }

    @Test
    public void testGetExamsByTeacherAndSemester() {
        //根据教师id和学期查找考试成绩
        Long teacherId = 2020065432L;
        String year = "2023";
        String semester = "夏";
        List<ExamTeacherDto> examTeacherDtos = examService.getExamsByTeacherAndSemesters(teacherId, year, semester);
        // logger.info("examTeacherDtos: {}", examTeacherDtos);
        assertNotNull(examTeacherDtos);

        assertEquals(1, examTeacherDtos.size());
    }

    @Test
    public void testMarkStudentDeferForCourse() {
        //标记学生缓考
        Long courseId = 4L;
        Long studentId = 3018076543L;
        examService.markStudentDeferForCourse(courseId, studentId);
        List<Exam> exams = examMapper.findExamsByCourseIds(List.of(courseId));
        List<Long> examIds = exams.stream().map(Exam::getExamId).collect(Collectors.toList());
        List<Exam_student> examStudents = examStudentMapper.findExam_studentsByExamIdsAndStudentId(examIds, studentId);
        // logger.info("examStudents: {}", examStudents);
        assertNotNull(examStudents);

        assertEquals(2, examStudents.size());
        assertEquals("缓考", examStudents.get(0).getExamStatus());
    }

    @Test
    public void testgetStudentsByTeacherIdAndCourseNameAndSemester() {
        //根据教师id和课程名和学期查找学生成绩
        Long teacherId = 2020065432L;
        String courseName = "大学英语(4)";
        String year = "2023";
        String semester = "春夏";
        List<CourseStudentDto> courseStudentDtos = examService.getStudentsByTeacherIdAndCourseNameAndSemester(teacherId, courseName, year, semester);
        logger.info("courseStudentDtos: {}", courseStudentDtos);
        assertNotNull(courseStudentDtos);

        assertEquals(2, courseStudentDtos.size());
    }

    @Test
    public void testgetScoresByStudentIdAndSemester() {
        //根据学生id和学期查找学生成绩
        Long studentId = 3018076543L;
        String year = "2023";
        String semester = "春夏";
        List<ScoreStudentDto> scoreStudentDtos = examService.getScoresByStudentIdAndSemester(studentId, year, semester);
        logger.info("scoreStudentDtos: {}", scoreStudentDtos);
        assertNotNull(scoreStudentDtos);

        assertEquals(2, scoreStudentDtos.size());
    }

    @Test
    public void testgetSemesterIds(){
        List<SemesterIdDto> semesterIdDtos = examService.getSemesterIds();
        logger.info("semesterIdDtos: {}", semesterIdDtos);
        assertNotNull(semesterIdDtos);
    }
}
