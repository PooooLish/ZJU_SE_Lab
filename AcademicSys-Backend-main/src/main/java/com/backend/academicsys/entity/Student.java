package com.backend.academicsys.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private Long schoolId;
    private String college;
    private String className;
    private String major;
    private Integer duration;
    private String schoolingType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate admissionTime;
    private LocalDate expectedGraduation;
    private String currentEducation;
    private String gainedEducation;
    private String graduatedSchool;
    private String currentRanking;
    private double currentGrades;
}

