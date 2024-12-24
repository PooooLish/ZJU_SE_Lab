package com.backend.academicsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseNameAndSemester {
    private String semester;
    private String course_name;
    void getcourse_name(String course_name){
        this.course_name = course_name;
    }
}
