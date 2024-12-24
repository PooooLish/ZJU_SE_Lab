package com.backend.academicsys.dto;

import java.text.DecimalFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseStudentDto {
    private Long student_id;
    private double grade;
    private double ord_grade;
    private double exam_grade;
    private int is_pass;
    private Boolean defer;
    private Boolean resit;
    private int retake;
}
