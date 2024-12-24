package com.backend.academicsys.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@Service
public class SensitiveWordService {
    private final ResourceLoader resourceLoader;

    @Getter
    private Set<String> sensitiveWords = new HashSet<>();

    public SensitiveWordService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void loadSensitiveWords() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:sensitive-words.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sensitiveWords.add(line.trim());
            }
        }
    }

    public boolean containsSensitiveWord(String content) {
        for (String word : sensitiveWords) {
            if (content.contains(word)) {
                return true;
            }
        }
        return false;
    }

}
