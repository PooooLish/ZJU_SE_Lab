package com.backend.academicsys.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExamTeacherDto {
    private String year;
    private String term;
    private String course;
    private String time;
    private String location;

    public ExamTeacherDto(String semesterId, String course, LocalDate date, LocalTime start_time,  LocalTime end_time, String location){
        String year = semesterId.substring(0, 4);
        String term = semesterId.substring(4);
        this.year = year;
        this.term = term;
        this.course = course;
        this.time = date.toString() + " " + start_time.toString() + "-" + end_time.toString();
        this.location = location;
    }
}
