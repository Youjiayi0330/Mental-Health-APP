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
@TableName(value = "doctor_privacy")
public class DoctorPrivacy {
    @TableId(value = "id")
    private String id;

    @TableField(value = "realName")
    private String realName;

    @TableField(value = "creditId")
    private String creditId;

    @TableField(value = "creditPhoto")
    private String creditPhoto;

    @TableField(value = "certification")
    private String certification;
}
