package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "note_comment")
public class NoteComment {
    @TableId(value="id")
    private String id;

    @TableField(value = "noteId")
    private String noteId;

    @TableField(value = "userId")
    private String userId;

    @TableField(value = "time")
    private String time;

    @TableField(value = "content")
    private String content;

}
