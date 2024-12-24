package com.backend.academicsys.dto;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentRankDto {
    private Long schoolId;
    private String college;
    private String major;
    private String className;
    private String currentRanking;
    private double currentGrades;
}
