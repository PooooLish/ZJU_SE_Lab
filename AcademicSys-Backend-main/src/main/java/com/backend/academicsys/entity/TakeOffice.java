package com.backend.academicsys.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TakeOffice {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long schoolId;
    private String department;
    private String workPosition;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
}
