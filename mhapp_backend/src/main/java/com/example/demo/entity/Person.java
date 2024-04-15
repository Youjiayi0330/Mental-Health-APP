package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "userInfo")
public class Person {
    @TableId(value="id")
    private String id;

    @TableField(value="nickname")
    private String nickname;

    @TableField(value="pwd")
    private String pwd;

    @TableField(value="identity")
    private Integer identity;

    @TableField(value = "status")
    private Integer status;
}
