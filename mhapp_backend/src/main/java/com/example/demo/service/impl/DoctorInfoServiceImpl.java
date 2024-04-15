package com.example.demo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.DoctorInfo;
import com.example.demo.mappers.DoctorInfoMapper;
import com.example.demo.service.DoctorInfoService;
import com.example.demo.vo.DoctorInfoVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DoctorInfoServiceImpl extends ServiceImpl<DoctorInfoMapper, DoctorInfo> implements DoctorInfoService {
    @Resource
    DoctorInfoMapper doctorInfoMapper;

    public List<DoctorInfoVo> findAllDoctors(){
        return doctorInfoMapper.findAllDoctors();
    }

    public DoctorInfoVo detail(String id){
        return doctorInfoMapper.detail(id);
    }
}
