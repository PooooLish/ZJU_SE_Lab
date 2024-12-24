package com.backend.academicsys.controller;

import lombok.Data;

@Data
public class ForumResponse {
    private String content;
    private String nickname;
    private String school_id;
    private Integer isStudent;
    private java.sql.Timestamp reg_time;
    private Integer forum_id;
}
