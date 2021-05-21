package com.ababa.service;

import com.ababa.entity.MeetingRoom;
import com.ababa.entity.MeetingRoomFile;
import com.ababa.entity.Meetings;
import com.ababa.mapper.MeetingRoomFileMapper;
import com.ababa.mapper.MeetingRoomMapper;
import com.ababa.mapper.MeetingsMapper;
import com.ababa.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    @Autowired
    MeetingRoomMapper meetingRoomMapper;
    @Autowired
    MeetingsMapper meetingsMapper;
    @Autowired
    MeetingRoomFileMapper meetingRoomFileMapper;
    @Value("${user.file.path}")
    private String UPLOAD_PATH;


   @Override
    public List<MeetingRoom> getAllMeetingRoom(String group) {
       Map<String, Object> select = new HashMap<>();
       select.put("user_group", group);
       List<MeetingRoom> meetingRooms = meetingRoomMapper.selectByMap(select);
       meetingRooms.forEach(
               (k)->{
                   select.clear();
                   select.put("mrid", k.getMrid());
                   List<MeetingRoomFile> meetingRoomFiles = meetingRoomFileMapper.selectByMap(select);
                   k.setMeetingRoomFiles(meetingRoomFiles);
               }
       );
       return meetingRooms;
   }

    @Override
    public Boolean  addMeetingRoom(MeetingRoom meetingRoom) {
        return   meetingRoomMapper.insert(meetingRoom) == 1 ? true : false;
    }

    @Override
    public Boolean deleteMeetingRoom(Integer id) {
       Map<String, Object> map = new HashMap<>();
       map.put("mrid", id);
       meetingRoomFileMapper.deleteByMap(map);
       return meetingRoomMapper.deleteById(id) == 1 ? true : false;
    }

    @Override
    public Boolean updateMeetingRoom(MeetingRoom meetingRoom) {
        return meetingRoomMapper.updateById(meetingRoom) == 1? true : false;
    }

    @Override
    public List<MeetingRoom> getAvailableRooms(String startTime, String endTime, String group) {
        List<MeetingRoom> meetingRooms = this.getAllMeetingRoom(group);
        Iterator<MeetingRoom> iterator = meetingRooms.iterator();
        while (iterator.hasNext()) {
            MeetingRoom meetingRoom = iterator.next();
            List<Meetings> availableRooms = meetingsMapper.getUnavailableRooms(startTime, endTime, meetingRoom.getMrid());
            if(!availableRooms.isEmpty()) {
                iterator.remove();
            }
        }
        Map<String, Object> select = new HashMap<>();
        meetingRooms.forEach(
                (k)->{
                    select.clear();
                    select.put("mrid", k.getMrid());
                    List<MeetingRoomFile> meetingRoomFiles = meetingRoomFileMapper.selectByMap(select);
                    k.setMeetingRoomFiles(meetingRoomFiles);
                }
        );
        return meetingRooms;
    }

    @Override
    public MeetingRoom getById(Integer id) {
        MeetingRoom meetingRoom = meetingRoomMapper.selectById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("mrid", id);
        List<MeetingRoomFile> meetingRoomFiles = meetingRoomFileMapper.selectByMap(map);
        meetingRoom.setMeetingRoomFiles(meetingRoomFiles);
        return meetingRoom;
    }

    @Override
    public Boolean addMeetingRoomFiles(MultipartFile[] files, String userGroup, Integer mrid) throws IOException {
        MeetingRoomFile meetingRoomFile = new MeetingRoomFile();
        for (MultipartFile file : files) {
            // 判断文件是否有内容
            if (file.isEmpty()) {
                System.out.println("该文件无任何内容!!!");
                return false;
            }
            String fileName = FileNameUtils.getFileName(file);
            Path directory = Paths.get(UPLOAD_PATH + userGroup);
            // 判断目录是否存在，不存在创建
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            try {
                InputStream inputStream = file.getInputStream();
                Files.copy(inputStream, directory.resolve(fileName));
                meetingRoomFile.setMrid(mrid);
                meetingRoomFile.setUrl("106.14.76.27:39000"+"/file/"+userGroup+"/"+fileName);
                meetingRoomFileMapper.insert(meetingRoomFile);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean deleteMeetingRoomFile(Integer mrfid) {
        return meetingRoomFileMapper.deleteById(mrfid) == 1 ? true : false;
    }

    @Override
    public List<MeetingRoomFile> getFilesById(Integer mrid) {
       Map<String, Object> map = new HashMap<>();
       map.put("mrid", mrid);

        return meetingRoomFileMapper.selectByMap(map);
    }
}
