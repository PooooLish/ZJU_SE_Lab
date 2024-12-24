`./service` 下放置服务层函数接口

例如：
~~~java
package com.backend.academicsys.service;

import com.backend.academicsys.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface UserService {
    User findUserByUsername(String username);

    void addUser(String username, String password, String email, String phone);

    User findUserByEmail(String email);

    User findUserByPhone(String phone);
    
    //...
}
~~~
