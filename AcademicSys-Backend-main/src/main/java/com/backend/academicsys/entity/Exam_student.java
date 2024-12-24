package com.backend.academicsys.entity;

import java.text.DecimalFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam_student {
    private Long examStudentId;
    private Long examId;
    private Long studentId;
    private String examLocation;
    private String examSit;
    private String examStatus;
    private double examGrade;
    private Boolean deferOrResit;
    private double deferResitGrade;
}
