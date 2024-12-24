package com.backend.academicsys.service;

import java.util.List;
import java.util.Map;

import com.backend.academicsys.entity.Result;
import com.backend.academicsys.entity.Textbook;

public interface TextbookService {

    Result<String> delTextbook(Textbook textbook);

    Result<String> modTextbook(Textbook textbook);

    Result<String> createTextbook(Textbook textbook);

    Result<List<Textbook>> findByCondition(Map<String, Object> condition);
}