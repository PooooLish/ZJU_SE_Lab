package com.backend.academicsys.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Questionnaire {
    private int ques_id;
    private Date ques_start_date;
    private Date ques_end_date;
    private int ques_status;
    private int ques_aspect1;
    private int ques_aspect2;
    private int ques_aspect3;
    private int ques_aspect4;
    private double ques_grade;
    private String ques_comment;
    private Long student_id;
    private Long teacher_id;
    private int course_id;
    private String course_name;
    private String teacher_name;
    private String student_name;
}
