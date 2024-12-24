package com.backend.academicsys.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam {
    private long examId;
    private long courseId;
    private String examType;
    private LocalDate examDate;
    private LocalTime examStartTime;
    private LocalTime examEndTime;
    private String examLocation;
    private int duration;
    private String examFormat;
    private int totalMarks;
    private int passMarks;
    private String additionalInfo;

    public Exam(long courseId, String examType, LocalDate examDate, LocalTime examStartTime, LocalTime examEndTime, String examLocation, int duration, int totalMarks, String examFormat, int passMarks, String additionalInfo) {
        this.courseId = courseId;
        this.examType = examType;
        this.examDate = examDate;
        this.examStartTime = examStartTime;
        this.examEndTime = examEndTime;
        this.examLocation = examLocation;
        this.duration = duration;
        this.totalMarks = totalMarks;
        this.examFormat = examFormat;
        this.passMarks = passMarks;
        this.additionalInfo = additionalInfo;
    }
}
