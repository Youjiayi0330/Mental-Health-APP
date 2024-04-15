package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "note")
public class NoteInfo {
    @TableId(value="id")
    private String id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "userId")
    private String userId;

    @TableField(value = "time")
    private String time;

    @TableField(value = "content")
    private String content;

    @TableField(value = "coverId")
    private String coverId;

}
