package com.backend.academicsys;

import com.backend.academicsys.dto.*;
import com.backend.academicsys.entity.Course_student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.backend.academicsys.entity.Course;
import com.backend.academicsys.entity.Exam;
import com.backend.academicsys.entity.Student_rank;
import com.backend.academicsys.mapper.CourseMapper;
import com.backend.academicsys.mapper.Course_studentMapper;
import com.backend.academicsys.mapper.ExamMapper;
import com.backend.academicsys.mapper.StudentMapper;
import com.backend.academicsys.mapper.Student_rankMapper;

import java.util.List;

@SpringBootTest
public class ScoreMapperTest {
    private static final Logger logger = LoggerFactory.getLogger(ScoreMapperTest.class);

    @Autowired
    private Student_rankMapper studentRankMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testGetRankByStudentId(){
        Long studentId = 3018076543L;
        List<RankDto> rank = studentRankMapper.findRanksByStudentId(studentId);
        assertNotNull(rank);
        logger.info("rank: {}", rank);

        assertEquals(2, rank.size());
    }

    @Test
    public  void testStudentCurrentRankByStudentId(){
        // 根据学生id查找学生当前学期排名
        Long studentId = 3020012345L;
        CurrentRankDto rank = studentMapper.findCurrentRankByStudentId(studentId);
        logger.info("rank: {}", rank);
        assertNotNull(rank);

    }
    /*
    @Test
    public  void testFindScoresByStudentIdAndCourseId(){
        //根据学生id和courseId查找考试成绩
        Long studentIds = 2020012345L;
//        int courseId = 1;
        List<Integer> courseIds = Course_studentMapper.findCourseIdByStudentId(studentIds);
        for (int courseId : courseIds){
            CourseStudentDto score = Course_studentMapper.findScoresByStudentIdAndCourseId(courseId, studentIds);
            CourseNameAndSemester Name = Course_studentMapper.findCourseNameAndSemesterByCourseId(courseId);
            logger.info("score: {}", score);
            logger.info("Name: {}", Name);
        }
    }

    @Test
    public void testStudentRankByStudentIdAndSemester(){
        // 根据学生id和学期查找学生成绩排名
        Long studentId = 2020012345L;
        String period = "2023春季学期";
        int sem_or_year = 0; //为1表示想查询学年排名
        RankDto rank = Course_studentMapper.findRanksByStudentIdAndSemester(studentId, sem_or_year, period);
        logger.info("rank: {}", rank);
    }

//    @Test
//    public void testFindScoresByStudentId(){
//        // 根据学生id查找考试成绩
//        Long studentIds = 2020012345L;
//        List<Long> courseIds = Course_studentMapper.findCourseIdsByStudentId(studentIds);
//        List<ScoreStudentDto> score = new ArrayList<>();
//        for (Long courseId : courseIds){
//            List<ScoreStudentDto> temp = Course_studentMapper.findScoresByStudentIdAndCourseId(courseId.intValue(), List.of(studentIds));
//            score.addAll(temp);
//        }
//        logger.info("score: {}", score);
//    }

 */

}
