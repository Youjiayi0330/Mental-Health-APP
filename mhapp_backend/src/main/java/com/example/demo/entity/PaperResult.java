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
@TableName(value = "paper_result")
public class PaperResult {
    @TableId(value = "id")
    private String id;

    @TableField(value = "userId")
    private String userId;

    @TableField(value = "paperId")
    private String paperId;

    @TableField(value = "paperScore")
    private Integer paperScore;

    @TableField(value = "description")
    private String description;
}
