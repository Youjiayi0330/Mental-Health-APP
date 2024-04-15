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
@TableName(value = "doctor_intro")
public class DoctorInfo {
    @TableId(value = "id")
    private String id;

    @TableField(value = "sign")
    private String sign;

    @TableField(value = "intro")
    private String intro;
}
