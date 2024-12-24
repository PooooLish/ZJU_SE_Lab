package com.backend.academicsys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course_teacher {
    private int courseTeacherId;
    private Long schoolId;
    private int courseId;
    private Boolean taOrTc;
}
