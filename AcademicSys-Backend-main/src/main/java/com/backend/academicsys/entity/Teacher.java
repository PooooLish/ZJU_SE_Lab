package com.backend.academicsys.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Teacher {
    private Long schoolId;
    private String jobTitle;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date admissionTime;
    private String education;
    private String pastEducation;
    private String pastWork;
    private String research;
    private String teaching;
}
