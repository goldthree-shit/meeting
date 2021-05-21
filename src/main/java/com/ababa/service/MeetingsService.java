package com.ababa.service;

import com.ababa.entity.MeetingRecoding;
import com.ababa.entity.Meetings;
import com.ababa.entity.Participants;

import java.util.List;
import java.util.Map;

public interface MeetingsService {
    Integer addMeeting(Meetings meeting);
    List<Meetings> getNextMeetings(String username);
    Boolean deleteMeetings(Integer meid);
    Boolean addPerson(Participants participant);
    List<Meetings> getAllMeetings(String username);
    Boolean addMeetingRecoding(MeetingRecoding meetingRecoding);
    MeetingRecoding getRecodingMeeting(Integer meid);
    Boolean updateMeetingRecoding(MeetingRecoding meetingRecoding);
}
