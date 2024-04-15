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
@TableName(value = "question")
public class Question {
    @TableId(value = "id")
    private String id;

    @TableField(value = "questionTitle")
    private String questionTitle;

    @TableField(value = "paperId")
    private String paperId;
}
