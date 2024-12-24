package com.backend.academicsys.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.backend.academicsys.service.TokenService;
import org.springframework.stereotype.Service;
import com.backend.academicsys.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String getToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getUserId()); // token 里面存一下 user_id，之后在 PassToken 中用于验证是不是管理员
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
                .sign(Algorithm.HMAC256(user.getPassword())); // 把密码加密作为签名
    }
}
