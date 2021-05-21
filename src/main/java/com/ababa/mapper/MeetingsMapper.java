package com.ababa.mapper;

import com.ababa.entity.Meetings;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface MeetingsMapper extends BaseMapper<Meetings> {

    List<Meetings> getUnavailableRooms(@Param("startTime") String startTime,
                                     @Param("endTime") String endTime,
                                     @Param("mrid") Integer mrid);

    List<Meetings> getNextMeetings(@Param("username") String username,
                                   @Param("currentTime") String currentTime);
}
