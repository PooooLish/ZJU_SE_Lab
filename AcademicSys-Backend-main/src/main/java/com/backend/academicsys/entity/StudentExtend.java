package com.backend.academicsys.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentExtend extends User {
    private String college;
    private String major;
    private String className;
    private Integer duration;
    private String schoolingType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date admissionTime;
    private Date expectedGraduation;
    private String currentEducation;
    private String gainedEducation;
    private String graduatedSchool;
    private String currentRanking;
    private Float currentGrades;
}
