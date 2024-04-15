package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.PaperResultJudge;
import com.example.demo.vo.PaperDetail;

import java.net.UnknownHostException;
import java.util.List;

public interface PaperResultJudgeService extends IService<PaperResultJudge> {
    String getDescription(String paperId, Integer paperScore);

    void add(PaperDetail paperDetail) throws UnknownHostException;

    List<PaperResultJudge> findByPaperId(String paperId);

    void update(PaperDetail paperDetail) throws UnknownHostException;
}
