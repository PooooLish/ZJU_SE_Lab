package com.backend.academicsys.service.impl;

import com.backend.academicsys.entity.QuesGrade;
import com.backend.academicsys.entity.QuesStatistics;
import com.backend.academicsys.entity.Questionnaire;
import com.backend.academicsys.mapper.QuesStuMapper;
import com.backend.academicsys.service.QuesStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuesStuServiceImpl implements QuesStuService {
    @Autowired
    private QuesStuMapper quesStuMapper;

    @Override
    public List<Questionnaire> findUndoQues(Date time, long student_id){
        List<Questionnaire> quesList = quesStuMapper.getUndoQues(time,student_id);
        for(Questionnaire ques : quesList) {
            ques.setCourse_name(quesStuMapper.getCourseName(ques.getQues_id()));
            ques.setTeacher_name(quesStuMapper.getTeacherName(ques.getTeacher_id()));
            ques.setStudent_name(quesStuMapper.getStuName(ques.getQues_id()));
        }
        return quesList;
    }

    @Override
    public int submitQues(Questionnaire quesInfo){
        int ques_status = 1;
        int aspect_1 = quesInfo.getQues_aspect1();
        int aspect_2 = quesInfo.getQues_aspect2();
        int aspect_3 = quesInfo.getQues_aspect3();
        int aspect_4 = quesInfo.getQues_aspect4();
        double grade = (double) (aspect_1 + aspect_2 + aspect_3 + aspect_4) / 4;
        String ques_comment = quesInfo.getQues_comment();
        long student_id = quesInfo.getStudent_id();
        long teacher_id = quesInfo.getTeacher_id();
        int course_id = quesInfo.getCourse_id();
        return quesStuMapper.addQues(ques_status, aspect_1, aspect_2, aspect_3, aspect_4, grade, ques_comment, student_id, teacher_id, course_id);
    }

    @Override
    public List<Questionnaire> getQuesCourse(long teacher_id, int course_id){
        List<Questionnaire> quesList = quesStuMapper.getSelectQues(teacher_id,course_id);
        for(Questionnaire ques : quesList) {
            ques.setCourse_name(quesStuMapper.getCourseName(ques.getQues_id()));
            ques.setTeacher_name(quesStuMapper.getTeacherName(ques.getTeacher_id()));
            ques.setStudent_name(quesStuMapper.getStuName(ques.getQues_id()));
        }
        return quesList;
    }

    @Override
    public List<Questionnaire> getQues(long teacher_id){
        List<Questionnaire> quesList = quesStuMapper.getQues(teacher_id);
        for(Questionnaire ques : quesList) {
            ques.setCourse_name(quesStuMapper.getCourseName(ques.getQues_id()));
            ques.setTeacher_name(quesStuMapper.getTeacherName(ques.getTeacher_id()));
            ques.setStudent_name(quesStuMapper.getStuName(ques.getQues_id()));
        }
        return quesList;
    }

    @Override
    public QuesStatistics getStatistics(long teacher_id,int course_id){
        List<Questionnaire> queList = quesStuMapper.getSelectQues(teacher_id,course_id);
        QuesStatistics quesStats = new QuesStatistics();
        int n = queList.size();
        for (Questionnaire questionnaire : queList) {
            quesStats.average1 += questionnaire.getQues_aspect1();
            quesStats.average2 += questionnaire.getQues_aspect2();
            quesStats.average3 += questionnaire.getQues_aspect3();
            quesStats.average4 += questionnaire.getQues_aspect4();
            quesStats.grade += questionnaire.getQues_grade();
            if(questionnaire.getQues_grade() >=4.0){
                quesStats.num5++;
            }else if(questionnaire.getQues_grade() >=3.0){
                quesStats.num4++;
            }else if(questionnaire.getQues_grade() >=2.0){
                quesStats.num3++;
            }else if(questionnaire.getQues_grade() >=1.0){
                quesStats.num2++;
            }else if(questionnaire.getQues_grade() >=0.0) {
                quesStats.num1++;
            }
        }
        quesStats.average1 /= n;
        quesStats.average2 /= n;
        quesStats.average3 /= n;
        quesStats.average4 /= n;
        quesStats.grade /= n;
        quesStats.teacher_id = (long) teacher_id;
        quesStats.course_id = course_id;
        return quesStats;
    }

    @Override
    public List<QuesGrade> getGrades(long teacher_id) {
        List<QuesGrade> quesGradesList = new ArrayList<>();
        List<Integer> courseList = quesStuMapper.getCourseTeach(teacher_id);
        for (Integer course_id : courseList) {
            QuesGrade tmp = new QuesGrade();
            List<Questionnaire> queList = quesStuMapper.getSelectQues(teacher_id,course_id);
            int n = queList.size();
            double gradeCal = 0.0;
            for(Questionnaire questionnaire : queList) {
                gradeCal += questionnaire.getQues_grade();
            }
            gradeCal /= n;
            tmp.setGrade(gradeCal);
            tmp.setCourse_id(course_id);
            tmp.setCourse_name(quesStuMapper.courseNameGet(course_id));
            quesGradesList.add(tmp);
        }
        return quesGradesList;
    }
}
