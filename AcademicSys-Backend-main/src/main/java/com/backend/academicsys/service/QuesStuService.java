package com.backend.academicsys.service;

import com.backend.academicsys.entity.QuesGrade;
import com.backend.academicsys.entity.QuesStatistics;
import com.backend.academicsys.entity.Questionnaire;

import java.util.Date;
import java.util.List;

public interface QuesStuService {
    List<Questionnaire> findUndoQues(Date time, long student_id);
    int submitQues(Questionnaire quesInfo);
    QuesStatistics getStatistics(long teacher_id,int course_id);
    List<Questionnaire> getQuesCourse(long teacher_id, int course_id);
    List<Questionnaire> getQues(long teacher_id);
    List<QuesGrade> getGrades(long teacher_id);
}