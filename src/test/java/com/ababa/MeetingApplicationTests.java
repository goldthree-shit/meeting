package com.ababa;

import com.ababa.entity.MeetingRoom;
import com.ababa.entity.Meetings;
import com.ababa.entity.Participants;
import com.ababa.entity.User;
import com.ababa.mapper.MeetingRoomMapper;
import com.ababa.mapper.UserMapper;
import com.ababa.utils.EmailUtils;
import com.ababa.utils.JWTUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@SpringBootTest
class MeetingApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    private  JavaMailSender mailSender;
    @Autowired
    EmailUtils emailUtils;
    @Autowired
    MeetingRoomMapper meetingRoomMapper;
    @Test
    void contextLoads() {
        Meetings meetings = new Meetings();
        meetings.setMeid(1);
        List<Participants> list = new ArrayList<>();
        list.add(new Participants(1,1,"hx","1833756034@qq.com","13832756672",null,null,null,null));
        meetings.setParticipants(list);
        meetings.setRoomName("111");
        meetings.setDetailedLocation("222222");
        meetings.setUsername("aaa");
        meetings.setUserGroup("FZU");
        meetings.setStartTime(new Date());
        meetings.setEndTime(new Date());
        meetings.setMeetingName("ccc");
        emailUtils.sendEmail(meetings);
    }




}
