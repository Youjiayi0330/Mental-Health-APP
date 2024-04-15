package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ChatRecord;
import com.example.demo.mappers.ChatRecordMapper;
import com.example.demo.service.ChatRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordMapper, ChatRecord> implements ChatRecordService {
    @Resource
    ChatRecordMapper chatRecordMapper;

    public List<ChatRecord> getChatRecord(String fromUserId, String toUserId){
        //获取对应的聊天记录
        List<ChatRecord> data = chatRecordMapper.getChatRecord(fromUserId, toUserId);
        return data;
    }

    public void setHasRead(String fromUserId, String toUserId){
        //设置对方消息为已读
        chatRecordMapper.setOneFriendHasRead(toUserId,fromUserId);
    }

    public void setNewHasRead(Integer ChatRecordId){
        chatRecordMapper.setNewHasReadById(ChatRecordId);
    }

    public void addChatRecord(ChatRecord chatRecord){
        chatRecordMapper.insert(chatRecord);
    }
}
