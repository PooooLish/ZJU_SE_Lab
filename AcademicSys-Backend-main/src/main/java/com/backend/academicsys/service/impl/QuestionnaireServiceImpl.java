package com.backend.academicsys.service.impl;

import com.backend.academicsys.entity.Questionnaire;
import com.backend.academicsys.mapper.QuestionnaireMapper;
import com.backend.academicsys.service.QuestionnaireService;
import com.backend.academicsys.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionnaireServiceImpl  implements QuestionnaireService {
    @Autowired
    private SensitiveWordService sensitiveWordService;
    @Autowired
    private QuestionnaireMapper questionnaireMapper;
    @Override
    public boolean issue(String start_time, String end_time, String course_id ) throws ParseException {
        // 将字符串转换为 Date 类型
        Date startTime = parseDate(start_time);
        Date endTime = parseDate(end_time);
        int rows=0;
        int courseId=Integer.parseInt(course_id);
        Long teacherId=questionnaireMapper.getTeacherByCourseId(courseId);
        List<Long> students= questionnaireMapper.getStudentsByCourseId(courseId);
        for(Long student:students){
            rows+=questionnaireMapper.insertQuestionnaire(startTime, endTime, student, teacherId, courseId);
        }
        return rows > 0;
    }

    // 将字符串日期转换为 Date 类型的方法
    private static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date parsedDate =dateFormat.parse(dateString);
            return new Date(parsedDate.getTime());
        } catch (ParseException e) {
            throw new ParseException("日期格式错误", 0);
        }
    }

    //获取含有敏感词的问卷
    @Override
    public List<Questionnaire> getSensitiveQuestionnaire(){
        //获取所有帖子
        List<Questionnaire> allQuestionnaires = questionnaireMapper.getAllQuestionnaires();
        List<Questionnaire> quesWithSensitiveWords = new ArrayList<>();

        for (Questionnaire questionnaire : allQuestionnaires) {
            if (containsSensitiveWord(String.valueOf(questionnaire.getQues_comment()))) {
                quesWithSensitiveWords.add(questionnaire);
            }
        }
        for (Questionnaire questionnaire : quesWithSensitiveWords) {
            questionnaire.setCourse_name(questionnaireMapper.getCourseName(questionnaire.getCourse_id()));
            Long stuID=questionnaire.getStudent_id();
            Long teaID=questionnaire.getTeacher_id();
            questionnaire.setStudent_name(questionnaireMapper.getStuName(stuID));
            questionnaire.setTeacher_name(questionnaireMapper.getTeacherName(teaID));
        }

        return quesWithSensitiveWords;
    }

    //判断内容是否有敏感词
    private boolean containsSensitiveWord(String content) {
        // 调用敏感词服务来检查内容是否包含敏感词
        return sensitiveWordService.containsSensitiveWord(content);
    }

    //删除问卷
    @Override
    public boolean deleteQuestionnaire(int quesId){
        return questionnaireMapper.deleteQuestionnaire(quesId)>0;
    }

    //获取所有问卷
    @Override
    public List<Questionnaire> getAllQuestionnaire() {
        List<Questionnaire> allQuestionnaires = questionnaireMapper.getAllQuestionnaires();
        for (Questionnaire questionnaire : allQuestionnaires) {
            questionnaire.setCourse_name(questionnaireMapper.getCourseName(questionnaire.getCourse_id()));
            Long stuID=questionnaire.getStudent_id();
            Long teaID=questionnaire.getTeacher_id();
            questionnaire.setStudent_name(questionnaireMapper.getStuName(stuID));
            questionnaire.setTeacher_name(questionnaireMapper.getTeacherName(teaID));
        }
        return allQuestionnaires;
    }

    //获取所有结束的问卷
    @Override
    public List<Questionnaire> getAllEndQuestionnaire(){
        List<Questionnaire> allQuestionnaires = questionnaireMapper.getAllQuestionnaires();
        List<Questionnaire> endQuestionnaires = new ArrayList<>();
        for (Questionnaire questionnaire : allQuestionnaires) {
            if (questionnaire.getQues_end_date().before(new Date(System.currentTimeMillis()))) {
                endQuestionnaires.add(questionnaire);
            }
        }
        for (Questionnaire questionnaire : endQuestionnaires) {
            questionnaire.setCourse_name(questionnaireMapper.getCourseName(questionnaire.getCourse_id()));
            Long stuID=questionnaire.getStudent_id();
            Long teaID=questionnaire.getTeacher_id();
            questionnaire.setStudent_name(questionnaireMapper.getStuName(stuID));
            questionnaire.setTeacher_name(questionnaireMapper.getTeacherName(teaID));
        }
        return endQuestionnaires;
    }

    //获取无效问卷
    @Override
    public List<Questionnaire> getAllInvalidQuestionnaire(){
        List<Questionnaire> allQuestionnaires = getAllEndQuestionnaire();
        List<Questionnaire> invalidQuestionnaires = new ArrayList<>();
        for (Questionnaire questionnaire : allQuestionnaires) {
            if (questionnaire.getQues_status() == 0) {
                invalidQuestionnaires.add(questionnaire);
            }
        }
        for (Questionnaire questionnaire : invalidQuestionnaires) {
            questionnaire.setCourse_name(questionnaireMapper.getCourseName(questionnaire.getCourse_id()));
            Long stuID=questionnaire.getStudent_id();
            Long teaID=questionnaire.getTeacher_id();
            questionnaire.setStudent_name(questionnaireMapper.getStuName(stuID));
            questionnaire.setTeacher_name(questionnaireMapper.getTeacherName(teaID));
        }
        return invalidQuestionnaires;
    }

    public boolean deleteAllInvalidQuestionnaire(){
        List<Questionnaire> invalidQuestionnaires = getAllInvalidQuestionnaire();
        for (Questionnaire questionnaire : invalidQuestionnaires) {
            questionnaireMapper.deleteQuestionnaire(questionnaire.getQues_id());
        }
        return true;
    }

}
