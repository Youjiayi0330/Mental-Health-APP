package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.DoctorPrivacy;
import com.example.demo.mappers.DoctorPrivacyMapper;
import com.example.demo.service.DoctorPrivacyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DoctorPrivacyServiceImpl extends ServiceImpl<DoctorPrivacyMapper, DoctorPrivacy> implements DoctorPrivacyService {
    @Resource
    DoctorPrivacyMapper doctorPrivacyMapper;

    @Override
    public void add(DoctorPrivacy doctorPrivacy) {
        DoctorPrivacy doctorPrivacy1 = doctorPrivacyMapper.selectById(doctorPrivacy.getId());
        if(doctorPrivacy1 == null){
            doctorPrivacyMapper.insert(doctorPrivacy);
        } else {
            String certifyName = doctorPrivacy1.getCertification();
            doctorPrivacy.setCertification(certifyName);
            doctorPrivacyMapper.updateById(doctorPrivacy);
        }

    }

    @Override
    public void addCertification(String certifyFileName, String id) {
        DoctorPrivacy doctorPrivacy = doctorPrivacyMapper.selectById(id);
        if(doctorPrivacy == null){
            DoctorPrivacy doctorPrivacy1 = new DoctorPrivacy();
            doctorPrivacy1.setId(id);
            doctorPrivacy1.setCertification(certifyFileName);
            doctorPrivacyMapper.insert(doctorPrivacy1);
        } else {
            doctorPrivacy.setCertification(certifyFileName);
            doctorPrivacyMapper.updateById(doctorPrivacy);
        }

    }

    @Override
    public DoctorPrivacy findById(String id) {
        DoctorPrivacy doctorPrivacy = doctorPrivacyMapper.selectById(id);
        return doctorPrivacy;
    }
}
