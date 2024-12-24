package com.backend.academicsys.service;

import com.backend.academicsys.entity.Questionnaire;

import java.text.ParseException;
import java.util.List;

public interface QuestionnaireService {
    boolean issue(String startTime, String endTime, String courseId) throws ParseException;

    List<Questionnaire> getSensitiveQuestionnaire();

    boolean deleteQuestionnaire(int quesId);

    List<Questionnaire> getAllQuestionnaire();

    List<Questionnaire> getAllEndQuestionnaire();

    List<Questionnaire> getAllInvalidQuestionnaire();

    boolean deleteAllInvalidQuestionnaire();

}
