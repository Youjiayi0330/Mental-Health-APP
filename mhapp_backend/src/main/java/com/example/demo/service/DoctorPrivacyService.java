package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.DoctorPrivacy;

public interface DoctorPrivacyService extends IService<DoctorPrivacy> {
    void add(DoctorPrivacy doctorPrivacy);

    void addCertification(String certifyFileName, String id);

    DoctorPrivacy findById(String id);
}
