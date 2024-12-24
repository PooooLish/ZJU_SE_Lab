package com.backend.academicsys.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Forum_post {

    private int post_id;
    private int forum_id;
    private Timestamp post_time;
    private Long teacher_id;
    private Integer course_id;
    private int grade;
    private String content;
    private int like_count;
    private int dislike_count;
    private int favor_count;
    private boolean is_top;

    public boolean getIs_top() {
        return is_top;
    }
}