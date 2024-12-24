package com.backend.academicsys.controller;

import com.backend.academicsys.entity.Questionnaire;
import lombok.Data;

import java.util.List;

@Data
public class QuestionnaireResponse {
    private List<Questionnaire> questionnaires;
    
}
