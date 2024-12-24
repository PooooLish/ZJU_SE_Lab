package com.backend.academicsys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TeacherPublic {
    @JsonIgnore
    private Long schoolId;
    private String userName;
    private String gender;
    private String phone;
    private String email;
    private String country;
    private String nation;
    private String politicalRole;
    private String userPhoto;
    private String jobTitle;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date admissionTime;
    private String education;
    private String pastEducation;
    private String pastWork;
    private String research;
    private String teaching;
}
