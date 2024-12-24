package com.backend.academicsys.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Forum_opt {

    private int forum_opt_id;
    private int forum_id;
    private int post_id;
    private Timestamp opt_time;
    private String opt_type;

}
