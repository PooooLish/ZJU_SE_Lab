package com.backend.academicsys.entity;

import lombok.Data;

@Data
public class QuesGrade {
    private double grade;
    private int course_id;
    private String course_name;

}
