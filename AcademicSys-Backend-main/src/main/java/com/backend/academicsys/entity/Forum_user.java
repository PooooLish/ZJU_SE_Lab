package com.backend.academicsys.entity;

import lombok.Data;

@Data
public class Forum_user {

    private Integer forum_id;
    private Long school_id;
    private java.sql.Timestamp reg_time;

}
