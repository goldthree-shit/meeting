package com.ababa.service;

import com.ababa.entity.MeetingRecoding;
import com.ababa.entity.MeetingRoom;
import com.ababa.entity.Meetings;
import com.ababa.entity.Participants;
import com.ababa.mapper.MeetingRecodingMapper;
import com.ababa.mapper.MeetingRoomMapper;
import com.ababa.mapper.MeetingsMapper;
import com.ababa.mapper.ParticipantsMapper;
import com.ababa.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MeetingsServiceImpl implements MeetingsService {

    @Autowired
    MeetingsMapper meetingsMapper;
    @Autowired
    ParticipantsMapper participantsMapper;
    @Autowired
    MeetingRoomMapper meetingRoomMapper;
    @Autowired
    MeetingRecodingMapper meetingRecodingMapper;
    @Autowired
    EmailUtils emailUtils;
    /**
     * 添加会议，获取到会议的详细地址，然后保存
     * 参会成员是个List，遍历然后依次保存
     * @param meeting
     * @return
     */
    @Override
    public Integer addMeeting(Meetings meeting) {
        Integer mrid = meeting.getMrid();
        MeetingRoom meetingRoom = meetingRoomMapper.selectById(mrid);
        String detailedLocation = meetingRoom.getLocation() + meetingRoom.getFloor() + meetingRoom.getPlateNum();
        meeting.setDetailedLocation(detailedLocation);
        meeting.setRoomName(meetingRoom.getRoomName());

        if(meetingsMapper.insert(meeting) != 1) {
            return 0;
        }
        List<Participants> participants = meeting.getParticipants();
        try {
            participants.forEach(
                    (K)->{
                        K.setMeid(meeting.getMeid());
                        participantsMapper.insert(K);
                    }
            );

           if(emailUtils.sendEmail(meeting)) return 0;
        } catch (Exception e) {
            return 0;
        }
        return meeting.getMeid();

    }

    /**
     * 取消会议
     * @param meid
     * @return
     */
    @Override
    public Boolean deleteMeetings(Integer meid) {
        Map<String, Object> map = new HashMap<>();
        map.put("meid", meid);
        emailUtils.deleteEmail(meid);
        participantsMapper.deleteByMap(map);
        return    meetingsMapper.deleteById(meid) == 1 ? true : false;
    }

    /**
     * 添加单个会议成员
     * @param participant
     * @return
     */
    @Override
    public Boolean addPerson(Participants participant) {
       return participantsMapper.insert(participant) == 1 &&
        emailUtils.sendEmail(participant)? true: false;
    }
    /**
     * 获取待参加的会议
     * @param username
     * @return
     */
    @Override
    public List<Meetings> getNextMeetings(String username) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentTime = simpleDateFormat.format(date);
        List<Meetings> everyMeetings = meetingsMapper.getNextMeetings(username, currentTime);
        everyMeetings.forEach(
                (K)->{
                    Map<String, Object> map = new HashMap<>();
                    map.put("meid", K.getMeid());
                    List<Participants> participants = participantsMapper.selectByMap(map);
                    K.setParticipants(participants);
                    map.clear();
                }
        );
        return everyMeetings;
    }
    /**
     * 获取全部会议
     * @param username
     * @return
     */
    @Override
    public List<Meetings> getAllMeetings(String username) {
        Map<String, Object> sMap = new HashMap<>();
        sMap.put("username", username);
        List<Meetings> everyMeetings = meetingsMapper.selectByMap(sMap);
        everyMeetings.forEach(
                (K)->{
                    Map<String, Object> map = new HashMap<>();
                    map.put("meid", K.getMeid());
                    List<Participants> participants = participantsMapper.selectByMap(map);
                    K.setParticipants(participants);
                    map.clear();
                }
        );
        return everyMeetings;
    }

    /**
     * 添加会议记录
     * @param meetingRecoding
     * @return
     */
    @Override
    public Boolean addMeetingRecoding(MeetingRecoding meetingRecoding) {
        return meetingRecodingMapper.insert(meetingRecoding) == 1 ? true : false;
    }

    /**
     * 获取会议记录
     * @param meid
     * @return
     */
    @Override
    public MeetingRecoding getRecodingMeeting(Integer meid) {
        Map<String,Object> map = new HashMap<>();
        map.put("meid", meid);
        return meetingRecodingMapper.selectByMap(map).get(0);
    }

    /**
     * 更新会议记录
     * @param meetingRecoding
     * @return
     */
    @Override
    public Boolean updateMeetingRecoding(MeetingRecoding meetingRecoding) {
        return meetingRecodingMapper.updateById(meetingRecoding) == 1? true : false;
    }
}
