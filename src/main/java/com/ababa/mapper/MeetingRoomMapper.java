package com.ababa.mapper;

import com.ababa.entity.MeetingRoom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MeetingRoomMapper extends BaseMapper<MeetingRoom> {

}
