package com.backend.academicsys.controller;

import com.backend.academicsys.entity.Course_change;
import com.backend.academicsys.mapper.Course_changeMapper;
import com.backend.academicsys.service.Course_changeService;
import com.backend.academicsys.utils.Result;
import com.backend.academicsys.utils.ThreadLocalUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class Course_changeController {
    @Autowired
    private Course_changeService course_changeService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    Long get_school_id(){
        Map <String, Object> map  = ThreadLocalUtil.get();
        return (Long)map.get("id");
    }

    @PostMapping("/usr/student/lastSelect")
    Result lastSelect(@RequestBody Course_change courseChange){
        int course_id = courseChange.getCourse_id();
        String reason = courseChange.getChange_reason();
        Long student_id = get_school_id();

        Date currentDate = new Date();
        Course_change new_record = new Course_change(course_id,student_id, currentDate, "补选", reason, "",
                "待审批",  10240114514L, new Date(), "待审批");
        course_changeService.insertCourseChange(new_record);
        return Result.ok();
    }

    @GetMapping("/admin/pendingApprovals")
    Result getPendingApprovals() {
        // 查询所有 new_status 为 "待审批" 的记录
        List<Course_change> pendingApprovals = course_changeService.findAllStudentChange();
        System.out.println(course_changeService.findAllStudentChange());
        return Result.ok().data("pendingApprovals", pendingApprovals);
    }

    @PostMapping("/admin/approveChange")
    Result approveChange(@RequestBody Map<String, Integer> payload) {
        Integer change_id = payload.get("change_id");
        course_changeService.updateCourseChange(change_id);
        return Result.ok().message("批准成功");

    }

}
