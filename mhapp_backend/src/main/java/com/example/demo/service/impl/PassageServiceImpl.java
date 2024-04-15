package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Passage;
import com.example.demo.mappers.CollectMapper;
import com.example.demo.mappers.PassageMapper;
import com.example.demo.service.PassageService;
import com.example.demo.vo.PassageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PassageServiceImpl extends ServiceImpl<PassageMapper, Passage> implements PassageService {

    @Resource
    private PassageMapper passageMapper;
    @Resource
    private CollectMapper collectMapper;

    public Passage add(Passage passage){
        passage.setPtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        passage.setPstatus(0);
        passageMapper.insert(passage);
        return passage;
    }

    public Passage send(Passage passage){
        passage.setPtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        passage.setPstatus(1);
        passageMapper.insert(passage);
        return passage;
    }

    public PageInfo<Passage> findDraft(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize,"");
        List<Passage> list=passageMapper.findDraft();
        return PageInfo.of(list);
    }

    public PageInfo<Passage> findSended(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize,"");
        List<Passage> list=passageMapper.findSended();
        return PageInfo.of(list);
    }

    public Passage findById(Long id){
        return passageMapper.selectById(id);
    }

    public Passage updateDraft(Passage passage){
        passage.setPtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        passage.setPstatus(0);
        passageMapper.updateById(passage);
        return passage;
    }

    public Passage updateHasSended(Passage passage){
        passage.setPtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        passage.setPstatus(1);
        passageMapper.updateById(passage);
        return passage;
    }

    public void delete(Long id){
        passageMapper.deleteById(id);
    }

    public PageInfo<Passage> findByDraftName(String ptitle,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize,"");
        List<Passage> list=passageMapper.findByDraftName(ptitle);
        return PageInfo.of(list);
    }

    public PageInfo<PassageVo> findBySendedName(String ptitle,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize,"");
        List<PassageVo> list=passageMapper.findBySendedName(ptitle);
        return PageInfo.of(list);
    }

    @Override
    public List<PassageVo> findByName(String name) {
        List<PassageVo> passageVoList = passageMapper.findBySendedName(name);
        for(int i=0;i<passageVoList.size();i++){
            Long passageId = passageVoList.get(i).getPid();
            Integer collect_count = collectMapper.findByPassageId(passageId);
            passageVoList.get(i).setCollect_count(collect_count);
        }
        return passageVoList;
    }

    public List<PassageVo> findAllPassages(){
        List<PassageVo> passageVoList = passageMapper.findAllPassages();
        for(int i=0;i<passageVoList.size();i++){
            Long passageId = passageVoList.get(i).getPid();
            Integer collect_count = collectMapper.findByPassageId(passageId);
            passageVoList.get(i).setCollect_count(collect_count);
        }
        return passageVoList;
    }

}
