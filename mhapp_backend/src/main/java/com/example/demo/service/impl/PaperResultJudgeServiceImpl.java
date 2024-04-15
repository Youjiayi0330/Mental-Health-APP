package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Answer;
import com.example.demo.entity.PaperResultJudge;
import com.example.demo.entity.Question;
import com.example.demo.mappers.PaperResultJudgeMapper;
import com.example.demo.service.PaperResultJudgeService;
import com.example.demo.vo.PaperDetail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@Service
public class PaperResultJudgeServiceImpl extends ServiceImpl<PaperResultJudgeMapper, PaperResultJudge> implements PaperResultJudgeService {
    @Resource
    PaperResultJudgeMapper paperResultJudgeMapper;

    @Override
    public String getDescription(String paperId, Integer paperScore) {
        String description = null;
        List<PaperResultJudge> paperResultJudges = paperResultJudgeMapper.getPaperJudge(paperId);
        for(int i=0; i<paperResultJudges.size(); i++){
            if(paperScore <= paperResultJudges.get(i).getValueJudge()){
                description = paperResultJudges.get(i).getDescription();
                break;
            }
        }
        return description;
    }

    @Override
    public void add(PaperDetail paperDetail) throws UnknownHostException {
        String paperId = paperDetail.getName();
        JSONArray passageArray= JSON.parseArray(paperDetail.getValue());
        for(int i=0; i<passageArray.size();i++){
            Map map = JSON.parseObject(passageArray.get(i).toString(), Map.class);
            PaperResultJudge paperResultJudge = new PaperResultJudge();
            paperResultJudge.setId(paperId+i);
            paperResultJudge.setPaperId(paperId);
            paperResultJudge.setDescription(map.get("description").toString());
            paperResultJudge.setValueJudge(Integer.valueOf(map.get("section").toString()));
            paperResultJudgeMapper.insert(paperResultJudge);
        }
    }

    @Override
    public List<PaperResultJudge> findByPaperId(String paperId) {
        return paperResultJudgeMapper.getPaperJudge(paperId);
    }

    @Override
    public void update(PaperDetail paperDetail) throws UnknownHostException {
        String paperId = paperDetail.getId();
        JSONArray passageArray= JSON.parseArray(paperDetail.getValue());
        for(int i=0; i<passageArray.size();i++){
            Map map = JSON.parseObject(passageArray.get(i).toString(), Map.class);
            PaperResultJudge paperResultJudge = new PaperResultJudge();
            paperResultJudge.setPaperId(paperId);
            paperResultJudge.setId(map.get("paperJudgeId").toString());
            paperResultJudge.setValueJudge(Integer.valueOf(map.get("section").toString()));
            paperResultJudge.setDescription(map.get("description").toString());
            paperResultJudgeMapper.updateById(paperResultJudge);
        }
    }
}
