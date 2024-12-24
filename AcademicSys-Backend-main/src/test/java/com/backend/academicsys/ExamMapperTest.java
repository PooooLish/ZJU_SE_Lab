package com.backend.academicsys;

import com.backend.academicsys.mapper.Course_studentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.backend.academicsys.entity.Course;
import com.backend.academicsys.entity.Exam;
import com.backend.academicsys.mapper.CourseMapper;
import com.backend.academicsys.mapper.ExamMapper;


@SpringBootTest
public class ExamMapperTest {
    private static final Logger logger = LoggerFactory.getLogger(ExamMapperTest.class);

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private Course_studentMapper course_studentMapper;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testFindExamIdsByCourseIds() {
        //根据课程id查找考试
        List<Long> courseIds = List.of(4L, 5L);
        List<Exam> exams = examMapper.findExamsByCourseIds(courseIds);
        List<Long> examIds = exams.stream().map(Exam::getExamId).collect(Collectors.toList());;
        logger.info("examIds: {}", examIds);
        assertEquals(2, examIds.size());
    }

    @Test
    public  void testFindExamsByStudentId(){
        //根据学生id查找考试时间
        Long studentId = 3020012345L;
        List<Exam> exams = examMapper.findExamsByStudentId(studentId);
        List<Long> examIds = exams.stream().map(Exam::getExamId).collect(Collectors.toList());
        logger.info("examIds: {}", examIds);
        assertEquals(1, examIds.size());
    }

    @Test
    public void testFindExamsBySchoolId(){
        //根据教师id查找考试开始和结束
        Long teacherId = 2020054321L;
        List<Exam> exams = examMapper.findExamsBySchoolId(teacherId);
        List<Long> examIds = exams.stream().map(Exam::getExamId).collect(Collectors.toList());
        logger.info("examIds: {}", examIds);
        assertEquals(2, examIds.size());
    }

}