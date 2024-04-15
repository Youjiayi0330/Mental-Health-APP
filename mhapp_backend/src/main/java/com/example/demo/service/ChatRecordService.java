package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.ChatRecord;

import java.util.List;

public interface ChatRecordService extends IService<ChatRecord> {
    List<ChatRecord> getChatRecord(String fromUserId, String toUserId);

    void setHasRead(String fromUserId, String toUserId);

    void setNewHasRead(Integer ChatRecordId);

    void addChatRecord(ChatRecord chatRecord);

}
