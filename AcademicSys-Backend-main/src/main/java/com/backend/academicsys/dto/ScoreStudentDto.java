package com.backend.academicsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreStudentDto {
    public String year;
    private String semester;
    private String course_name;
    private String category;
    private double grade;
    private double ord_grade;
    private double exam_grade;
    private int is_pass;

    public ScoreStudentDto(String semesterId, String course_name, String category, double grade, double ord_grade, double exam_grade, int is_pass) {
        String year = semesterId.substring(0, 4);
        String semester = semesterId.substring(4);
        this.year = year;
        this.semester = semester;
        this.course_name = course_name;
        this.category = category;
        this.grade = grade;
        this.ord_grade = ord_grade;
        this.exam_grade = exam_grade;
        this.is_pass = is_pass;
    }

}
