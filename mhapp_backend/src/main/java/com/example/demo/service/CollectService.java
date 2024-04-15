package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.CollectInfo;
import com.example.demo.entity.Passage;
import com.example.demo.vo.PassageVo;

import java.util.List;

public interface CollectService extends IService<CollectInfo> {
    List<CollectInfo> findIsCollect(Long passageId, String userId);

    void add(String id, Long passageId, String userId);

    void delete(Long passageId, String userId);

    List<PassageVo> findPassageByUserId(String userId);
}
