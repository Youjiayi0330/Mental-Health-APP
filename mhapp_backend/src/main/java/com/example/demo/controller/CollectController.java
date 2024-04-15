package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.CollectInfo;
import com.example.demo.entity.Passage;
import com.example.demo.service.CollectService;
import com.example.demo.vo.PassageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/collect")
public class CollectController {
    @Resource
    CollectService collectService;

    //查询某用户是否收藏过某文章
    @GetMapping("/findIsCollect/{passageId}")
    private boolean findIsCollect(@PathVariable("passageId") String passageId, @RequestParam(value = "userId")String userId){
        Boolean isCollect = true;
        List<CollectInfo> collectInfos = collectService.findIsCollect(Long.valueOf(passageId),userId);
        if(collectInfos.size() == 0){
            isCollect = false;
        }
        return isCollect;
    }

    @PostMapping
    private Result add(String passageId, String userId){
        String id = passageId + userId;
        collectService.add(id, Long.valueOf(passageId), userId);
        return Result.success();
    }

    @DeleteMapping("/{passageId}")
    private Result delete(@PathVariable("passageId") String passageId, @RequestParam(value = "userId")String userId){
        collectService.delete(Long.valueOf(passageId),userId);
        return Result.success();
    }

    @GetMapping("/findByUserId/{userId}")
    private List<PassageVo> findByUserId(@PathVariable("userId")String userId){
        List<PassageVo> passageList = collectService.findPassageByUserId(userId);
        return passageList;
    }
}
