package com.ababa.mapper;

import com.ababa.entity.Participants;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ParticipantsMapper extends BaseMapper<Participants> {
}
