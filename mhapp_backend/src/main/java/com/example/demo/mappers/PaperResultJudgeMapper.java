package com.example.demo.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.PaperResultJudge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperResultJudgeMapper extends BaseMapper<PaperResultJudge> {
    List<PaperResultJudge> getPaperJudge(@Param("paperId") String paperId);

}
