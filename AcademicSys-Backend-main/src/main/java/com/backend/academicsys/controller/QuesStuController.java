package com.backend.academicsys.controller;

import com.backend.academicsys.entity.QuesGrade;
import com.backend.academicsys.entity.QuesStatistics;
import com.backend.academicsys.entity.Questionnaire;
import com.backend.academicsys.service.QuesStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Questionnaire")
public class QuesStuController {
    @Autowired
    private QuesStuService quesStuService;

    //学生进入评价模块，展示未完成的问卷列表
    //参数值：student_id
    //返回值：未填写的问卷列表
    @GetMapping("/student")
    public ResponseEntity<?> showQuesStu(@RequestParam long student_id){
        Date currentTime = new Date();
        List<Questionnaire> ques = quesStuService.findUndoQues(currentTime,student_id);
        return new ResponseEntity<>(ques, HttpStatus.OK);
    }

    //学生问卷填写完成，点击“提交”后，调用此接口存储问卷信息
    //参数值：问卷信息
    //返回值：提交成功/失败
    @PostMapping("/student/written")
    public ResponseEntity<?> subQuestionnaire(@RequestBody Questionnaire quesInfo){
        if(quesStuService.submitQues(quesInfo)>0){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Failed",HttpStatus.BAD_REQUEST);
        }
    }

    //老师查询问卷评价信息
    //参数值：teacher_id，course_id(可为空)
    //返回值：评价问卷列表
    @GetMapping("/teacher/search")
    public ResponseEntity<?> getQuestionnaire(@RequestParam long teacher_id, @RequestParam(required = false) Integer course_id){
        if(course_id == null){
            List<Questionnaire> quesList = quesStuService.getQues(teacher_id);
            return new ResponseEntity<>(quesList, HttpStatus.OK);
        }else {//删除response
            List<Questionnaire> quesList = quesStuService.getQuesCourse(teacher_id, course_id);
            return new ResponseEntity<>(quesList, HttpStatus.OK);
        }
    }

    //老师查看统计信息
    //参数值：teacher_id,course_id(可为空)
    //返回值：统计信息，或教师所有课程均分
    @GetMapping("/teacher/statistics")
    public ResponseEntity<?> getStatistics(@RequestParam long teacher_id,@RequestParam(required = false) Integer course_id){
        if(course_id == null){
            List<QuesGrade> gradeList = quesStuService.getGrades(teacher_id);
            return new ResponseEntity<>(gradeList,HttpStatus.OK);
        }else {
            QuesStatistics statistics = quesStuService.getStatistics(teacher_id, course_id);
            return new ResponseEntity<>(statistics, HttpStatus.OK);
        }
    }

}
