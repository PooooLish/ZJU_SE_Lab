package com.backend.academicsys.controller;

import com.backend.academicsys.entity.Questionnaire;
import com.backend.academicsys.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/QuestionnaireManagement")
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    //管理员发放问卷
    //前端应该在管理员点击发放问卷时调用此接口
    //参数：开始时间、结束时间、课程id
    @PostMapping("/issueQuestionnaire")
    public ResponseEntity<?> issueQuestionnaire(@RequestBody Map<String, String> body) throws ParseException {
        String startTime = body.get("startTime");
        String endTime = body.get("endTime");
        String courseId = body.get("courseId");

        if(questionnaireService.issue(startTime, endTime, courseId))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>("fail to issue, check if information is correct", HttpStatus.BAD_REQUEST);
    }

    //获取含有敏感词的问卷
    //前端应该在管理员点击获取含有敏感词的问卷时调用此接口
    @GetMapping("/getSensitiveQuestionnaire")
    public ResponseEntity<?> getSensitiveQuestionnaire() {
        List<Questionnaire> questionnaires=questionnaireService.getSensitiveQuestionnaire();
        QuestionnaireResponse response = new QuestionnaireResponse();
        response.setQuestionnaires(questionnaires);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //管理员删除问卷
    //前端应该在管理员点击删除问卷时调用此接口
    //参数：问卷id
    @PostMapping("/deleteQuestionnaire")
    public ResponseEntity<?> deleteQuestionnaire(@RequestBody Map<String, Integer>body) {
        int quesID=body.get("quesID");
        if(questionnaireService.deleteQuestionnaire(quesID))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>("fail to delete, check if information is correct", HttpStatus.BAD_REQUEST);
    }

    //管理员获取所有问卷
    //前端应该在管理员点击获取所有问卷时调用此接口
    @GetMapping("/getAllQuestionnaire")
    public ResponseEntity<?> getAllQuestionnaire() {
        List<Questionnaire> questionnaires=questionnaireService.getAllQuestionnaire();
        QuestionnaireResponse response = new QuestionnaireResponse();
        response.setQuestionnaires(questionnaires);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //管理员获取所有结束的问卷
    //前端应该在管理员点击获取所有结束的问卷时调用此接口
    @GetMapping("/getAllEndQuestionnaire")
    public ResponseEntity<?> getAllEndQuestionnaire() {
        List<Questionnaire> questionnaires=questionnaireService.getAllEndQuestionnaire();
        QuestionnaireResponse response = new QuestionnaireResponse();
        response.setQuestionnaires(questionnaires);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    //管理员获取所有无效的问卷
//    //前端应该在管理员点击获取所有无效的问卷时调用此接口
    @GetMapping("/getAllInvalidQuestionnaire")
    public ResponseEntity<?> getAllInvalidQuestionnaire() {
        List<Questionnaire> questionnaires=questionnaireService.getAllInvalidQuestionnaire();
        QuestionnaireResponse response = new QuestionnaireResponse();
        response.setQuestionnaires(questionnaires);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //管理员删除所有无效的问卷
    //前端应该在管理员点击删除所有无效的问卷时调用此接口
    @PostMapping("/deleteAllInvalidQuestionnaire")
    public ResponseEntity<?> deleteAllInvalidQuestionnaire() {
        if(questionnaireService.deleteAllInvalidQuestionnaire())
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>("fail to delete, check if information is correct", HttpStatus.BAD_REQUEST);
    }
}
