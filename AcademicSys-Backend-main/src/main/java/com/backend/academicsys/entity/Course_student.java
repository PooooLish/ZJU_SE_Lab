package com.backend.academicsys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course_student {// 不用驼峰命名法 group 4 的代码会跑不起来, 这个是 Mapper 返回数据命名问题
    private int courseStudentId;
    private Long studentId;
    private int courseId;
    private Date enrollDate;
    private String status;
    private double grade;
    private double ordGrade;
    private double examGrade;
    private int isPass;
    private String feedback;
    private int retake;
    private double retakeGrade;


    // 部分参数构造函数
    public Course_student(int courseId, Long studentId, Date enrollDate, String status, int retake, double retakeGrade) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.enrollDate = enrollDate;
        this.status = status;
        this.retake = retake;
        this.retakeGrade = retakeGrade;

        // 设置其他字段的默认值
        this.courseStudentId = 0;
        this.grade = 0.0;
        this.ordGrade = 0.0;
        this.examGrade = 0.0;
        this.isPass = 0;
        // this.feedback = new Text("");
    }

}