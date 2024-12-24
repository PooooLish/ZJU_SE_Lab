package com.backend.academicsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SemesterIdDto {
    private String year;
    private String semester;
}
