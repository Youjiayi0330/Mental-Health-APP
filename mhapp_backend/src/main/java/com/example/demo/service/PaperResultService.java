package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.PaperResult;
import com.example.demo.vo.PaperResultVo;

import java.util.List;

public interface PaperResultService extends IService<PaperResult> {
    void add(String id, String userId, String paperId, Integer paperScore, String description);

    List<PaperResultVo> findByUserId(String userId);

    void updateById(String id, Integer paperScore, String description);
}
