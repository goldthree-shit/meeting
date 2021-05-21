package com.ababa.controller;

import com.ababa.entity.MeetingRoom;
import com.ababa.service.MeetingRoomService;
import com.ababa.utils.JWTUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MeetingRoomController {

    @Autowired
    MeetingRoomService meetingRoomService;

    @GetMapping("/meetMeetingRooms")
    public MeetingRoom getMeetingRooms() {
        return new MeetingRoom();
    }

    @GetMapping("/meetingroom/getAll")
    public Map<String, Object> getAllMeetingRooms(HttpServletRequest request) {
        String userGroup = JWTUtils.getPayload(request, "userGroup");
        Map<String, Object> result = new HashMap<>();
        System.out.println(userGroup);
        result.put("meetingRooms", meetingRoomService.getAllMeetingRoom(userGroup));
        result.put("state", true);
       return result;
    }

    @PostMapping("/meetingroom/add")
    public Map<String, Object> addMeetingRooms(@RequestBody MeetingRoom meetingRoom
            , HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        if (!"1".equals(JWTUtils.getPayload(request, "isAdmin"))) {
            result.put("msg", "不是管理员，不具有该权限");
            result.put("state", false);
            return result;
        }
        meetingRoom.setUserGroup(JWTUtils.getPayload(request, "userGroup"));
        result.put("state", meetingRoomService.addMeetingRoom(meetingRoom));
        return result;
    }

    @PostMapping("/meetingroom/delete")
    public Map<String, Object> deleteMeetingRoom(@RequestBody String str
            ,HttpServletRequest request) {
        Integer mrid = Integer.parseInt(JSON.parseObject(str).get("mrid").toString());
        Map<String, Object> result = new HashMap<>();
        if (!"1".equals(JWTUtils.getPayload(request, "isAdmin"))) {
            result.put("msg", "不是管理员，不具有该权限");
            result.put("state", false);
            return result;
        }
        result.put("state", meetingRoomService.deleteMeetingRoom(mrid));
        return result;
    }

    @PostMapping("/meetingroom/update")
    public Map<String, Object> updateMeetingRoom(@RequestBody MeetingRoom meetingRoom
            ,HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        if (!"1".equals(JWTUtils.getPayload(request, "isAdmin"))) {
            result.put("msg", "不是管理员，不具有该权限");
            result.put("state", false);
            return result;
        }
        meetingRoom.setUserGroup(JWTUtils.getPayload(request, "userGroup"));
        result.put("state", meetingRoomService.updateMeetingRoom(meetingRoom));
        return result;
    }

    @GetMapping("/meetingroom/getAvailableRooms")
    public Map<String, Object> getAvailableRooms(String startTime, String endTime, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String userGroup = JWTUtils.getPayload(request, "userGroup");
        result.put("meetingRooms", meetingRoomService.getAvailableRooms(startTime, endTime, userGroup));
        result.put("state", true);
        return result;
    }

    @GetMapping("/meetingroom/getById")
    public  Map<String, Object> getById(Integer mrid) {
        Map<String, Object> result = new HashMap<>();
        result.put("meetingRoom", meetingRoomService.getById(mrid));
        result.put("state", true);
        return result;
    }

    @PostMapping("/meetingroom/addFiles")
    public Map<String, Object> addFiles(@RequestParam("multipartFiles") MultipartFile[] multipartFiles,
                                        @RequestParam("mrid") Integer mrid
            , HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<>();
        if (!"1".equals(JWTUtils.getPayload(request, "isAdmin"))) {
            result.put("msg", "不是管理员，不具有该权限");
            result.put("state", false);
            return result;
        }

        String userGroup = JWTUtils.getPayload(request, "userGroup");
        System.out.println(userGroup);
        System.out.println(mrid);
        result.put("state", meetingRoomService.addMeetingRoomFiles(multipartFiles,userGroup,mrid));
        return result;
    }

    @PostMapping("/meetingroom/deleteFile")
    public Map<String, Object> deleteFiles( String str, HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<>();
        if (!"1".equals(JWTUtils.getPayload(request, "isAdmin"))) {
            result.put("msg", "不是管理员，不具有该权限");
            result.put("state", false);
            return result;
        }

        Integer mrfid = Integer.parseInt(JSON.parseObject(str).get("mrfid").toString());
        result.put("state", meetingRoomService.deleteMeetingRoomFile(mrfid));
        return result;
    }

    @GetMapping("/meetingroom/getFilesById")
    public Map<String, Object> getFilesById(Integer mrid) {
        Map<String, Object> result = new HashMap<>();
        result.put("meetingRoomFiles", meetingRoomService.getFilesById(mrid));
        result.put("state", true);
        return result;
    }

}
