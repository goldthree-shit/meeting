package com.ababa.service;

import com.ababa.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface UserService {
    Map<String, Object> login(User user);
    Map<String, Object> register(User user);
    User getUser(String username);
}
