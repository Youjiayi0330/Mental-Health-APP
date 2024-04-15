package com.example.demo.controller;

import com.example.demo.entity.PaperResult;
import com.example.demo.service.PaperResultService;
import com.example.demo.vo.PaperResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/paperResult")
public class PaperResultController {
    @Resource
    PaperResultService paperResultService;

    @PostMapping
    public void add(String id, String userId, String paperId, String paperScore, String description){
        paperResultService.add(id,userId,paperId,Integer.valueOf(paperScore),description);
    }

    @GetMapping("/findByUserId/{userId}")
    public List<PaperResultVo> findByUserId(@PathVariable("userId") String userId){
        List<PaperResultVo> paperResultVoList = paperResultService.findByUserId(userId);
        return paperResultVoList;
    }

    @PutMapping
    public void updateById(String id, String paperScore, String description){
        paperResultService.updateById(id,Integer.valueOf(paperScore),description);
    }
}
