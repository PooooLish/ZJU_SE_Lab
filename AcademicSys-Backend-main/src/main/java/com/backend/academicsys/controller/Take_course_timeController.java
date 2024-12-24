package com.backend.academicsys.controller;

import com.backend.academicsys.entity.Take_course_time;
import com.backend.academicsys.mapper.Take_course_timeMapper;
import com.backend.academicsys.mapper.CourseMapper;
import com.backend.academicsys.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin
public class Take_course_timeController {
    @Autowired
    private Take_course_timeMapper take_course_timeMapper;

    Boolean timeCheck(String start, String end){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, formatter);

        return startDateTime.isBefore(endDateTime);
    }//若合法则返回true

    @PostMapping("/admin/addCourseTime")
    Result addCourseTime(@RequestBody Take_course_time take_course_time) {
        int turn = take_course_time.getTurn();
        String start_time = take_course_time.getStart_time();
        String end_time = take_course_time.getEnd_time();
        if(!timeCheck(start_time, end_time)) return Result.error().message("时间不合法");

        int count = take_course_timeMapper.insert_time(turn, start_time, end_time);
        if (count == 1) {
            return Result.ok().message("添加成功");
        } else {
            return Result.error().message("添加失败");
        }
    }

    

}
