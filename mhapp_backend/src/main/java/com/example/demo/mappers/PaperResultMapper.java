package com.example.demo.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.PaperResult;
import com.example.demo.vo.PaperResultVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperResultMapper extends BaseMapper<PaperResult> {
    List<PaperResultVo> findByUserId(@Param("userId") String userId);

    void updateById(@Param("id") String id, @Param("paperScore") Integer paperScore, @Param("description") String description);
}
