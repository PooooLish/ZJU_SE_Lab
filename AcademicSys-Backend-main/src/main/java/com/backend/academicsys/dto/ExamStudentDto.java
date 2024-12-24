package com.backend.academicsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExamStudentDto {
    private String year;
    private String semester;
    private String course_name;
    private String time;
    private String place;
    private String seat;

    public ExamStudentDto(String semesterId, String course_name, String time, String place, String seat) {
        String year = semesterId.substring(0, 4);
        String semester = semesterId.substring(4);
        this.year = year;
        this.semester = semester;
        this.course_name = course_name;
        this.time = time;
        this.place = place;
        this.seat = seat;
    }
}
