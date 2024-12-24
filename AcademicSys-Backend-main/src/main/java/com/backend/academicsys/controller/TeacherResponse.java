package com.backend.academicsys.controller;

import com.backend.academicsys.entity.Teacher;
import lombok.Data;

import java.util.Date;

@Data
public class TeacherResponse {
    private Long school_id;
    private String teacher_name;
    private String job_title;
    private Date admission_time;
    private String education;
    private String past_education;
    private String past_work;
    private String research;
    private String teaching;

    public TeacherResponse(Teacher teacher, String teacher_name) {
        this.school_id = teacher.getSchoolId();
        this.teacher_name = teacher_name;
        this.job_title = teacher.getJobTitle();
        this.admission_time = teacher.getAdmissionTime();
        this.education = teacher.getEducation();
        this.past_education = teacher.getPastEducation();
        this.past_work = teacher.getPastWork();
        this.research = teacher.getResearch();
        this.teaching = teacher.getTeaching();
    }

}
