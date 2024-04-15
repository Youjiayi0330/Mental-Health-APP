package com.example.demo.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Passage;
import com.example.demo.vo.PassageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PassageMapper extends BaseMapper<Passage> {
    List<PassageVo> findAllPassages();

    List<Passage> findDraft();

    List<Passage> findSended();

    List<Passage> findByDraftName(@Param("ptitle") String ptitle);

    List<PassageVo> findBySendedName(@Param("ptitle") String ptitle);
}
