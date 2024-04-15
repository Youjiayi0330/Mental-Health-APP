package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.service.DoctorInfoService;
import com.example.demo.vo.DoctorInfoVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/doctorInfo")
public class DoctorInfoController {
    @Resource
    private DoctorInfoService doctorInfoService;

    //根据id获取对应医生的信息
    @GetMapping("/{id}")
    public Result<DoctorInfoVo> detail(@PathVariable("id") String id){
        DoctorInfoVo doctorInfoVo = doctorInfoService.detail(id);
        return Result.success(doctorInfoVo);
    }

    //获得所有医生信息
    @GetMapping("/all")
    public List<DoctorInfoVo> findAllDoctors(){
        return doctorInfoService.findAllDoctors();
    }
}
