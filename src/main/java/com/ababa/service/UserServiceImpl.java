package com.ababa.service;

import com.ababa.entity.User;
import com.ababa.mapper.UserMapper;
import com.ababa.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public Map<String, Object> login(User user)  {
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        List<User> users = userMapper.selectByMap(map);
        if(users.isEmpty()) {
            map.clear();
            map.put("state", "false");
            map.put("msg", "用户名不存在");
            return map;
        }
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        map.remove("password");
        if(password.equals(users.get(0).getPassword())) {
            user = users.get(0);
            HashMap<String, String> tokenMap = new HashMap<>();
            tokenMap.put("uid", user.getUid().toString());
            tokenMap.put("username", user.getUsername());
            tokenMap.put("isAdmin", user.getIsAdmin().toString());
            tokenMap.put("userGroup", user.getUserGroup());
            String token = JWTUtils.getToken(tokenMap);

            map.put("isAdmin", user.getIsAdmin());
            map.put("username", user.getUsername());
            map.put("userGroup", user.getUserGroup());
            map.put("state", "true");
            map.put("msg","登陆成功");
            map.put("token", token);
            return map;
        }
        map.clear();
        map.put("state", false);
        map.put("msg","密码错误");
        return map;
    }

    @Override
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();
        String username = user.getUsername();
        map.put("username", username);
        if (!userMapper.selectByMap(map).isEmpty()) {
            map.clear();
            map.put("state", "false");
            map.put("msg", "该用户名已存在");
            return map;
        }
        // 保存用户
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        map.clear();

        HashMap<String, String> tokenMap = new HashMap<>();
        tokenMap.put("uid", user.getUid().toString());
        tokenMap.put("username",username);
        tokenMap.put("isAdmin", user.getIsAdmin().toString());
        tokenMap.put("userGroup", user.getUserGroup());
        String token = JWTUtils.getToken(tokenMap);

        map.put("isAdmin", user.getIsAdmin());
        map.put("username", user.getUsername());
        map.put("userGroup", user.getUserGroup());
        map.put("state",true);
        map.put("msg", "注册成功");
        map.put("token", token);
       return map;
    }

    @Override
    public User getUser(String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        User user = userMapper.selectByMap(map).get(0);
        user.setPassword(null);
        return user;
    }
}
