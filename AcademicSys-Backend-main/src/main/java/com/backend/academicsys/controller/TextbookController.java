package com.backend.academicsys.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.academicsys.entity.Result;
import com.backend.academicsys.entity.Textbook;
import com.backend.academicsys.service.TextbookService;
import com.backend.academicsys.utils.ThreadLocalUtil;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/Textbook")
public class TextbookController {

    @Resource
    private TextbookService TextbookService;

    @PostMapping("/getTextbook")
    public Object getTextbook(@RequestBody Map<String, Object> condition) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        if ((int) (schoolId / 1e10) != 1) {
            return Result.fail("只有管理员可以查看教材信息");
        }
        return TextbookService.findByCondition(condition);
    }

    @PostMapping("/delTextbook")
    public Object delTextbook(@RequestBody Textbook textbook) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        if ((int) (schoolId / 1e10) != 1) {
            return Result.fail("只有管理员可以删除教材信息");
        }
        return TextbookService.delTextbook(textbook);
    }

    @PostMapping("/createTextbook")
    public Object createTextbook(@RequestBody Textbook textbook) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        if ((int) (schoolId / 1e10) != 1) {
            return Result.fail("只有管理员可以创建教材信息");
        }
        return TextbookService.createTextbook(textbook);
    }

    // 现在要求修改的 textbook 必须传入完整属性（相当于直接覆盖本来的 textbook，待改善
    @PostMapping("/modTextbook")
    public Object modTextbook(@RequestBody Textbook textbook) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        if ((int) (schoolId / 1e10) != 1) {
            return Result.fail("只有管理员可以修改教材信息");
        }
        return TextbookService.modTextbook(textbook);
    }
}
