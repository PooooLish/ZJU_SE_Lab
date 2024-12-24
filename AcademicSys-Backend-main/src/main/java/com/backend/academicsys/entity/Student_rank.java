package com.backend.academicsys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student_rank {
    private Long studentId;
    private Boolean semOrYear;
    private String periodTime;
    private String periodRank;
    private double periodGrades;
}