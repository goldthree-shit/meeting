package com.ababa.utils;

import com.ababa.entity.Meetings;
import com.ababa.entity.Participants;
import com.ababa.mapper.MeetingsMapper;
import com.ababa.mapper.ParticipantsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class EmailUtils {
    @Autowired
    private  JavaMailSender mailSender;
    @Autowired
    private  MeetingsMapper meetingsMapper;
    @Autowired
    private  ParticipantsMapper participantsMapper;
    @Value("${spring.mail.username}")
    private  String emailFrom;

    public  Boolean sendEmail(Meetings meetings) {

        try {
            List<Participants> participants = meetings.getParticipants();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startTime = dateFormat.format(meetings.getStartTime());
            String endTime = dateFormat.format(meetings.getEndTime());

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("开会通知");
            mailMessage.setFrom(emailFrom);
            participants.forEach(
                    (K)-> {
                        String text = K.getName()+":"
                                        + "由" + meetings.getUsername() + "预约的会议将在"
                                        + meetings.getDetailedLocation() + meetings.getRoomName() + "召开"
                                        + ", 会议的预计时间为" + startTime + "---" + endTime
                                        + ", 会议的主题是关于" + meetings.getMeetingName()
                                        + ", 会议的详细信息是" + meetings.getMeetingDescription()
                                        + ", 请准时参加会议，有事请请假！"
                                        + "会议结束后，可在http://106.14.76.27:39000/meeting/getRecodingMeeting?meid="
                                        + meetings.getMeid() +"   查看会议记录";
                        mailMessage.setText(text);
                        mailMessage.setTo(K.getEmail());
                        mailSender.send(mailMessage);
                    }
            );
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public  Boolean sendEmail(Participants participants) {
        System.out.println(participants.getMeid());
        Meetings meetings = meetingsMapper.selectById(participants.getMeid());
        meetings.setParticipants(Arrays.asList(participants));
        System.out.println(meetings);
        return this.sendEmail(meetings);
    }

    public  Boolean deleteEmail(Integer meid) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("会议已取消");
        mailMessage.setFrom(emailFrom);
        Meetings meetings = meetingsMapper.selectById(meid);
        Map<String, Object> map = new HashMap<>();
        map.put("meid", meid);
        List<Participants> participants = participantsMapper.selectByMap(map);
        try {
            participants.forEach(
                    (K)-> {
                        DateFormat dateFormat = new SimpleDateFormat();
                        String startTime = dateFormat.format(meetings.getStartTime());
                        String text = K.getName()+":"+"与"+startTime+"开始，在" + meetings.getDetailedLocation()
                                + "举行，由" + meetings.getUsername() + "预约的会议已取消。请即使更改行程安排。";
                        mailMessage.setText(text);
                        mailMessage.setTo(K.getEmail());
                        mailSender.send(mailMessage);
                    }
            );
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
