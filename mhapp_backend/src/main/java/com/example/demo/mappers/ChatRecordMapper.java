package com.example.demo.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRecordMapper extends BaseMapper<ChatRecord> {
    List<ChatRecord> getChatRecord(@Param("from") String from,
                                   @Param("to") String to);

    List<ChatRecord> getChatRecordByPersonId(@Param("id") String personId);

    void setOneFriendHasRead(@Param("from") String from,
                             @Param("to") String to);

    void setNewHasReadById(@Param("id") Integer id);
}
