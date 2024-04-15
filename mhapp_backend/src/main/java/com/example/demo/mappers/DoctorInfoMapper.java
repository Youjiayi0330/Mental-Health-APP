package com.example.demo.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.DoctorInfo;
import com.example.demo.vo.DoctorInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DoctorInfoMapper extends BaseMapper<DoctorInfo> {
    List<DoctorInfoVo> findAllDoctors();

    DoctorInfoVo detail(@Param("id") String id);
}
