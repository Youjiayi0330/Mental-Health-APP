package com.example.mentalhealth.netty.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatRecord {
    private Integer id;
    private String personId,friendId;
    private Integer hasRead;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String message;

    public ChatRecord(String personId, String friendId, Integer hasRead, Date createTime, String message) {
        this.personId = personId;
        this.friendId = friendId;
        this.hasRead = hasRead;
        this.createTime = createTime;
        this.message = message;
    }

    public ChatRecord() {
    }

    public Integer getId() {
        return id;
    }

    public String getPersonId() {
        return personId;
    }

    public String getFriendId() {
        return friendId;
    }

    public Integer getHasRead() {
        return hasRead;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getMessage() {
        return message;
    }
}
