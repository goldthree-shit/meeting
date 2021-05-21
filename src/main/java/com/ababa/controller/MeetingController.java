package com.ababa.controller;

import com.ababa.entity.MeetingRecoding;
import com.ababa.entity.Meetings;
import com.ababa.entity.Participants;
import com.ababa.service.MeetingsService;
import com.ababa.utils.JWTUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MeetingController {

    @Autowired
    MeetingsService meetingsService;

    @GetMapping("/getMeetings")
    public Meetings getMeetings() {
        return new Meetings();
    }

    @PostMapping("/meeting/bookMeeting")
    public Map<String, Object> bookMeeting(@RequestBody Meetings meeting, HttpServletRequest request) {
        String userGroup = JWTUtils.getPayload(request, "userGroup");
        String username = JWTUtils.getPayload(request, "username");
        meeting.setUserGroup(userGroup);
        meeting.setUsername(username);
        Map<String, Object> result = new HashMap<>();
        Integer meid = meetingsService.addMeeting(meeting);
        if ( meid == 0) {
            result.put("state", false);
        }
        result.put("state", true);
        result.put("meid", meid);
        result.put("username", username);
        return result;
    }

    @GetMapping("/meeting/getNextMeetings")
    public Map<String, Object> getNextMeetings(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String username = JWTUtils.getPayload(request, "username");
        result.put("meetings", meetingsService.getNextMeetings(username));
        result.put("state", true);
        return result;
    }

    @GetMapping("/meeting/getAllMeetings")
    public Map<String, Object> getAllMeetings(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String username = JWTUtils.getPayload(request, "username");
        result.put("meetings", meetingsService.getAllMeetings(username));
        result.put("state", true);
        return result;
    }

    @PostMapping("/meeting/delete")
    public Map<String, Object> deleteMeeting(@RequestBody String str) {
        Map<String, Object> result = new HashMap<>();
        Integer meid = Integer.parseInt(JSON.parseObject(str).get("meid").toString());
        result.put("state", meetingsService.deleteMeetings(meid));
        return result;
    }

    @GetMapping("/meeting/addParticipant")
    public Map<String, Object> addParticipant(Participants participant) {
        Map<String, Object> result = new HashMap<>();
        result.put("state", meetingsService.addPerson(participant));
        return result;
    }

    @PostMapping("/meeting/addParticipantList")
    public Map<String, Object> addParticipantList(@RequestBody List<Participants> participants) {
        Map<String, Object> result = new HashMap<>();
        try {
            participants.forEach((K)->meetingsService.addPerson(K));
            result.put("state", true);
        } catch (Exception e) {
            result.put("state",false);
        }
        return result;
    }


    @PostMapping("/meeting/addRecodingMeeting")
    public Map<String, Object> recodingMeeting(@RequestBody MeetingRecoding meetingRecoding) {
        Map<String, Object> result = new HashMap<>();
        result.put("state", meetingsService.addMeetingRecoding(meetingRecoding));
        return result;
    }

    @GetMapping("/meeting/getRecodingMeeting")
    public Map<String, Object> getRecodingMeeting(Integer meid) {
        Map<String, Object> result = new HashMap<>();
        result.put("meetingRecoding", meetingsService.getRecodingMeeting(meid));
        result.put("state", true);
        return result;
    }


    @PostMapping("/meeting/updateRecodingMeeting")
    public Map<String, Object> updateRecodingMeeting(@RequestBody MeetingRecoding recoding) {
        System.out.println(recoding);
        Map<String, Object> result = new HashMap<>();
        result.put("state", meetingsService.updateMeetingRecoding(recoding));
        return result;
    }
}
