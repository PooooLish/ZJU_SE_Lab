package com.backend.academicsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreDto {
    // private Long teacherId,
    private String courseName;
    private Long studentId;
    private double grade;
    private double ordGrade;
    private double examGrade;
    private double retakeGrade;
}
