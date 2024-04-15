package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.DoctorInfo;
import com.example.demo.vo.DoctorInfoVo;

import java.util.List;

public interface DoctorInfoService extends IService<DoctorInfo> {
    List<DoctorInfoVo> findAllDoctors();

    DoctorInfoVo detail(String id);
}
