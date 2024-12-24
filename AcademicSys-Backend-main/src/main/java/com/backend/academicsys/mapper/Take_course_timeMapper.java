package com.backend.academicsys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import com.backend.academicsys.entity.Take_course_time;

@Mapper
public interface Take_course_timeMapper extends BaseMapper<Take_course_time> {
    @Insert("INSERT INTO take_course_time (turn, start_time, end_time)"
    + "VALUES(#{turn}, #{start_time}, #{end_time})")
    int insert_time(int turn, String start_time, String end_time);
}
