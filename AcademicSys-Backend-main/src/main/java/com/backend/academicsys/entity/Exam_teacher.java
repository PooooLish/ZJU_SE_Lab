package com.backend.academicsys.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exam_teacher {
    private Long examTeacherId;
    private Long examId;
    private Long schoolId;
    private String examRole;
}
