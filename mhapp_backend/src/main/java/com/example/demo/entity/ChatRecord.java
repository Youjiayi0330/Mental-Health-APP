package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "chat_record")
@ToString
public class ChatRecord {
    @TableId(value="id",type = IdType.AUTO)
    private Long id;

    @TableField(value="personId")
    private String personId;

    @TableField(value="friendId")
    private String friendId;

    @TableField(value="createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @TableField(value="hasRead")
    private Integer hasRead;

    @TableField(value="message")
    private String message;

    public ChatRecord(String personId, String friendId, Integer hasRead, Date createTime, String message) {
        this.personId = personId;
        this.friendId = friendId;
        this.hasRead = hasRead;
        this.createTime = createTime;
        this.message = message;
    }

}
