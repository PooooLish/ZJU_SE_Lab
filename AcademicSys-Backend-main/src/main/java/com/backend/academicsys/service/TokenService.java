package com.backend.academicsys.service;

import com.backend.academicsys.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    public String getToken(User user);
}
