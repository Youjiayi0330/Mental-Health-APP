package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "answer")
public class Answer {
    @TableField(value = "id")
    private String id;

    @TableField(value = "answer")
    private String answer;

    @TableField(value = "value")
    private Integer value;

    @TableField(value = "question_id")
    private String questionId;

}
