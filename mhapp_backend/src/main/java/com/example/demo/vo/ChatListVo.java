package com.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ChatListVo {
    private String personId;
    private String personName;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer hasRead;

    public ChatListVo(){

    }

    public ChatListVo(String personId, String personName, String message, Date createTime, Integer hasRead){
        this.personId = personId;
        this.personName = personName;
        this.message = message;
        this.createTime = createTime;
        this.hasRead = hasRead;
    }
}
