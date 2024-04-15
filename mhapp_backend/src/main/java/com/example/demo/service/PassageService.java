package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Passage;
import com.example.demo.vo.PassageVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PassageService extends IService<Passage> {
    List<PassageVo> findAllPassages();

    Passage add(Passage passage);

    Passage send(Passage passage);

    PageInfo<Passage> findDraft(Integer pageNum,Integer pageSize);

    PageInfo<Passage> findSended(Integer pageNum,Integer pageSize);

    Passage findById(Long id);

    Passage updateDraft(Passage passage);

    Passage updateHasSended(Passage passage);

    void delete(Long id);

    PageInfo<Passage> findByDraftName(String ptitle,Integer pageNum,Integer pageSize);

    PageInfo<PassageVo> findBySendedName(String ptitle,Integer pageNum,Integer pageSize);

    List<PassageVo> findByName(String name);
}
