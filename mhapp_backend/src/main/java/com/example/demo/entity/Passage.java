package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "passage")
public class Passage {
    @TableId(value="pid",type = IdType.AUTO)
    private Long pid;

    @TableField(value="ptitle")
    private String ptitle;

    @TableField(value="ptime")
    private String ptime;

    @TableField(value="pcontent")
    private String pcontent;

    @TableField(value="pstatus")
    private Integer pstatus;

    @TableField(value="fileName")
    private String fileName;
}
