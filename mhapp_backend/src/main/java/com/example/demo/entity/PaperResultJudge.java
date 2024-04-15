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
@TableName(value = "paper_result_judge")
public class PaperResultJudge {
    @TableId(value = "id")
    private String id;

    //@TableField(value = "value_judge")
    private Integer valueJudge;

    //@TableField(value = "description")
    private String description;

    //@TableField(value = "paper_id")
    private String paperId;
}
