package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import com.example.demo.common.Result;
import com.example.demo.entity.DoctorPrivacy;
import com.example.demo.service.DoctorPrivacyService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/doctorPrivacy")
public class DoctorPrivacyController {
    private static final String BASE_PATH = "D:/OORPhoto";

    @Resource
    DoctorPrivacyService doctorPrivacyService;

    @PostMapping
    public Result add(MultipartFile creditPhoto, String id, String realName, String creditId) throws IOException {
        String creditFileName = id + System.currentTimeMillis() + ".jpg";
        String credit_location = BASE_PATH + "/creditCard/" + creditFileName;
        FileUtil.writeBytes(creditPhoto.getBytes(),credit_location);
//        String certifyFileName = id + System.currentTimeMillis() + ".jpg";
//        String certify_location = BASE_PATH + "/doctorCertify/" + certifyFileName;
//        FileUtil.writeBytes(certifyPhoto.getBytes(), certify_location);
        DoctorPrivacy doctorPrivacy = new DoctorPrivacy();
        doctorPrivacy.setId(id);
        doctorPrivacy.setRealName(realName);
        doctorPrivacy.setCreditId(creditId);
        doctorPrivacy.setCreditPhoto(creditFileName);
        //doctorPrivacy.setCertification(certifyFileName);
        doctorPrivacyService.add(doctorPrivacy);
        return Result.success();
    }

    @PostMapping("/addCertification")
    public Result addCertification(MultipartFile certifyPhoto, String id) throws IOException {
        String certifyFileName = id + System.currentTimeMillis() + ".jpg";
        String certify_location = BASE_PATH + "/doctorCertify/" + certifyFileName;
        FileUtil.writeBytes(certifyPhoto.getBytes(), certify_location);
        doctorPrivacyService.addCertification(certifyFileName,id);
        return Result.success();
    }

    @GetMapping("/findById/{id}")
    public Result<DoctorPrivacy> findById(@PathVariable(value = "id") String id){
        DoctorPrivacy doctorPrivacy = doctorPrivacyService.findById(id);
        return Result.success(doctorPrivacy);
    }
}
