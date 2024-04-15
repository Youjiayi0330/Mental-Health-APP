package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.Result;
import com.example.demo.entity.CollectInfo;
import com.example.demo.entity.DoctorInfo;
import com.example.demo.entity.Passage;
import com.example.demo.mappers.CollectMapper;
import com.example.demo.mappers.DoctorInfoMapper;
import com.example.demo.mappers.PassageMapper;
import com.example.demo.service.CollectService;
import com.example.demo.service.DoctorInfoService;
import com.example.demo.vo.PassageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, CollectInfo> implements CollectService {
    @Resource
    CollectMapper collectMapper;
    @Resource
    PassageMapper passageMapper;

    @Override
    public List<CollectInfo> findIsCollect(Long passageId, String userId) {
        List<CollectInfo> collectInfos = collectMapper.findIsCollect(passageId,userId);
        return collectInfos;
    }

    @Override
    public void add(String id, Long passageId, String userId) {
        CollectInfo collectInfo = new CollectInfo();
        collectInfo.setId(id);
        collectInfo.setPassageId(passageId);
        collectInfo.setUserId(userId);
        collectMapper.insert(collectInfo);
    }

    @Override
    public void delete(Long passageId, String userId) {
        collectMapper.deleteByTwoId(passageId,userId);
    }

    @Override
    public List<PassageVo> findPassageByUserId(String userId) {
        List<CollectInfo> collectInfos = collectMapper.findByUserId(userId);
        List<PassageVo> passageList = new ArrayList<>();
        for(int i=0; i<collectInfos.size(); i++){
            Long passageId = collectInfos.get(i).getPassageId();
            Passage passage = passageMapper.selectById(passageId);
            PassageVo passageVo = new PassageVo();
            passageVo.setPid(passage.getPid());
            passageVo.setPtitle(passage.getPtitle());
            passageVo.setPtime(passage.getPtime());
            passageVo.setFileName(passage.getFileName());
            Integer collect_count = collectMapper.findByPassageId(passageVo.getPid());
            passageVo.setCollect_count(collect_count);
            passageList.add(passageVo);
        }
        return passageList;
    }
}
