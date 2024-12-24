package com.backend.academicsys.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
public class TeacherExtend extends User{
    private String jobTitle;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date admissionTime;
    private String education;
    private String pastEducation;
    private String pastWork;
    private String research;
    private String teaching;
}
