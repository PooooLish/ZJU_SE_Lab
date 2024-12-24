package com.backend.academicsys.service.impl;

import static java.sql.Types.NULL;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.academicsys.entity.Result;
import com.backend.academicsys.entity.Textbook;
import com.backend.academicsys.mapper.TextbookMapper;
import com.backend.academicsys.service.TextbookService;


@Service
public class TextbookServiceImpl implements TextbookService {

    @Autowired
    private TextbookMapper textbookMapper;

    @Override
    public Result<List<Textbook>> findByCondition(Map<String, Object> condition) {
        // if (conditionStr.isEmpty()) return textbookMapper.findAll();
        // else return textbookMapper.findByCondition(conditionStr);
        if (condition == null || condition.isEmpty()) {
            return Result.success(textbookMapper.findAll());
        }
        else {
            return Result.success(textbookMapper.findByCondition(condition));
        }
    }

    @Override
    public Result<String> delTextbook(Textbook textbook) {
        int textbookId = textbook.getTextbookId();
        if (textbookId == NULL) {
            System.out.printf("Textbook with id %d can not deleted since there is no such textbook.\n", textbookId);
            return Result.fail("删除失败，请检查 Textbook_id 是否存在");
        }
        // 检查是否存在该教材
        if (textbookMapper.findById(textbookId) == null) {
            System.out.printf("Textbook with id %d can not deleted since there is no such textbook.\n", textbookId);
            return Result.fail("删除失败，请检查 Textbook_id 是否存在");
        }
        if (textbookMapper.deleteById(textbookId)) {
            System.out.printf("Textbook with id %d is deleted successfully.\n", textbookId);
            return Result.success("删除成功");
        }
        else {
            System.out.printf("Textbook with id %d can not deleted since there is no such textbook.\n", textbookId);
            return Result.fail("删除失败");
        }
    }

    @Override
    public Result<String> modTextbook(Textbook textbook) {
        // 检查是否存在该教材
        int textbookId = textbook.getTextbookId();
        if (textbookId == NULL) {
            System.out.printf("Textbook with id %d can not updated since there is no such textbook.\n", textbookId);
            return Result.fail("修改失败，请检查 Textbook_id 是否存在");
        }
        if (textbookMapper.findById(textbookId) == null) {
            System.out.printf("Textbook with id %d can not updated since there is no such textbook.\n", textbookId);
            return Result.fail("修改失败，请检查 Textbook_id 是否存在");
        }
        if (textbookMapper.modTextbook(textbook)) {
            System.out.printf("Textbook with id %d is updated successfully.\n", textbookId);
            return Result.success("修改成功");
        }
        else {
            System.out.printf("Textbook with id %d can not updated since there is no such textbook.\n", textbookId);
            return Result.fail("修改失败");
        }
    }

    @Override
    public Result<String> createTextbook(Textbook textbook) {
        int textbookId = textbook.getTextbookId();
        if (textbookId == NULL) {
            System.out.printf("Textbook with id %d can not created since ID is NULL.\n", textbookId);
            return Result.fail("创建失败，请检查 Textbook_id 是否存在");
        }
        if (textbookMapper.findById(textbookId) != null) {
            System.out.printf("Textbook with id %d can not created since there has been the one with the same id.\n", textbookId);
            return Result.fail("创建失败，请检查 Textbook_id 是否存在");
        }
        if (textbookMapper.createTextbook(textbook)) {
            System.out.printf("Textbook with id %d is created successfully.\n", textbookId);
            return Result.success("创建成功");
        }
        else {
            System.out.printf("Textbook with id %d can not created since there has been the one with the same id.\n", textbookId);
            return Result.fail("创建失败");
        }
    }
}