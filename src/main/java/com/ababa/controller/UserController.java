package com.ababa.controller;

import com.ababa.entity.User;
import com.ababa.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(description = "用户的api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user/login")
    @ApiOperation("登录")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            result = userService.login(user);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("state", false);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @PostMapping("/user/register")
    @ApiOperation("注册")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            result = userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("state",false);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @GetMapping("/user/getMsg")
    public Map<String, Object> getMsg(@RequestParam("username") String username) {
        Map<String, Object> result = new HashMap<>();
        result.put("user", userService.getUser(username));
        result.put("state",true);

        return result;
    }


    @GetMapping("/getUsers")
    public User getUser() {
        return new User();
    }
}
