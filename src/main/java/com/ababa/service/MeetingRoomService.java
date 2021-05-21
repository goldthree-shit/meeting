package com.ababa.service;

import com.ababa.entity.MeetingRoom;
import com.ababa.entity.MeetingRoomFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MeetingRoomService {

    List<MeetingRoom> getAllMeetingRoom(String group);
    List<MeetingRoom> getAvailableRooms(String startTime, String endTime, String group);
    Boolean addMeetingRoom(MeetingRoom meetingRoom);
    Boolean deleteMeetingRoom(Integer id);
    Boolean updateMeetingRoom(MeetingRoom meetingRoom);
    MeetingRoom getById(Integer id);
    Boolean addMeetingRoomFiles(MultipartFile[] files, String userGroup, Integer mrid) throws IOException;
    Boolean deleteMeetingRoomFile(Integer mrfid);
    List<MeetingRoomFile> getFilesById(Integer mrid);
}
