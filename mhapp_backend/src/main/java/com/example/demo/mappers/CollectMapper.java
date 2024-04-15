package com.example.demo.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.CollectInfo;
import com.example.demo.entity.DoctorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectMapper extends BaseMapper<CollectInfo> {
    List<CollectInfo> findIsCollect(@Param("passageId") Long passageId, @Param("userId") String userId);

    void deleteByTwoId(@Param("passageId") Long passageId, @Param("userId") String userId);

    List<CollectInfo> findByUserId(@Param("userId") String userId);

    Integer findByPassageId(@Param("passageId") Long passageId);
}
