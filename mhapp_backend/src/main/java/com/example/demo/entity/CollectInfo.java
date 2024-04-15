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
@TableName(value = "collect_passage")
public class CollectInfo {
    @TableId(value = "id")
    private String id;

    @TableField(value = "passageId")
    private Long passageId;

    @TableField(value = "userId")
    private String userId;
}
