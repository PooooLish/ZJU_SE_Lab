package com.backend.academicsys.dto;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankDto {
    private String year;
    private String semester;
    private double current_grade;
    private Integer current_rank;

    public RankDto(String periodTime, double periodGrades, Integer periodRank) {
        this.year = periodTime.substring(0, 4);
        this.semester = periodTime.substring(4);
        this.current_grade = periodGrades;
        this.current_rank = periodRank;
    }

    public void setPeriodTime(String periodTime) {
        this.year = periodTime.substring(0, 4);
        this.semester = periodTime.substring(4);
    }

    public void setPeriodGrades(double periodGrades) {
        this.current_grade = periodGrades;
    }

    public void setPeriodRank(Integer periodRank) {
        this.current_rank = periodRank;
    }
}
