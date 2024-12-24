package com.backend.academicsys.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course { // 不用驼峰命名法 group 4 的代码会跑不起来, 这个是 Mapper 返回数据命名问题
    @TableId
    private int courseId;
    private String courseName;
    private float credit;
    private String courseDepartment;
    private String category;
    private String courseStartTime;
    private String courseEndTime;
    private String location;
    private String semester;
    private int maxStudents;
    private int numStudents;
    private String testStartTime;
    private String testEndTime;
    private String gradeCal;
    private String target;
    private String description;
    // 下面储存在数据库表里没有定义的内容，即一个 List<Map<String, Object>> 类型的数据
    private Object teachers;
    private Object tas;

    public Course(int courseId, String courseName, float credit, String courseDepartment, String category, String courseStartTime, String courseEndTime, String location, String semester, int maxStudents, int numStudents, String testStartTime, String testEndTime, String gradeCal, String target, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.courseDepartment = courseDepartment;
        this.category = category;
        this.courseStartTime = courseStartTime;
        this.courseEndTime = courseEndTime;
        this.location = location;
        this.semester = semester;
        this.maxStudents = maxStudents;
        this.numStudents = numStudents;
        this.testStartTime = testStartTime;
        this.testEndTime = testEndTime;
        this.gradeCal = gradeCal;
        this.target = target;
        this.description = description;
    }
}


