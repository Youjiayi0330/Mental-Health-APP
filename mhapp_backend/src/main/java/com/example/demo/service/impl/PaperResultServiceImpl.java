package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.PaperResult;
import com.example.demo.mappers.PaperResultMapper;
import com.example.demo.service.PaperResultService;
import com.example.demo.vo.PaperResultVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaperResultServiceImpl extends ServiceImpl<PaperResultMapper, PaperResult> implements PaperResultService {
    @Resource
    PaperResultMapper paperResultMapper;

    @Override
    public void add(String id, String userId, String paperId, Integer paperScore, String description) {
        PaperResult paperResult = new PaperResult();
        paperResult.setId(id);
        paperResult.setUserId(userId);
        paperResult.setPaperId(paperId);
        paperResult.setPaperScore(paperScore);
        paperResult.setDescription(description);
        paperResultMapper.insert(paperResult);
    }

    @Override
    public List<PaperResultVo> findByUserId(String userId) {
        return paperResultMapper.findByUserId(userId);
    }

    @Override
    public void updateById(String id, Integer paperScore, String description) {
        paperResultMapper.updateById(id,paperScore,description);
    }
}
