package com.backend.academicsys.controller;

import com.backend.academicsys.entity.Forum_post;
import lombok.Data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
public class PostResponse {
    private Long postId;
    private String content;
    private String teacher;
    private String course;
    private Integer like_count;
    private Integer dislike_count;
    private Integer favor_count;
    private Integer grade;
    private String post_time;
    private boolean liked;
    private boolean disliked;
    private boolean favored;

    public PostResponse(Forum_post forum_post, String teacher, String course, boolean liked, boolean disliked, boolean favored) {
        this.postId = (long) forum_post.getPost_id();
        this.content = forum_post.getContent();
        this.teacher = teacher;
        this.course = course;
        this.like_count = forum_post.getLike_count();
        this.dislike_count = forum_post.getDislike_count();
        this.favor_count = forum_post.getFavor_count();
        this.grade = forum_post.getGrade();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.post_time = sdf.format(forum_post.getPost_time());
        this.liked = liked;
        this.disliked = disliked;
        this.favored = favored;
    }
}
