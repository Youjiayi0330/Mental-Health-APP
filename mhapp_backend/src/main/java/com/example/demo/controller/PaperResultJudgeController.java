package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.PaperResultJudge;
import com.example.demo.service.PaperResultJudgeService;
import com.example.demo.vo.PaperDetail;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping("/paperResultJudge")
public class PaperResultJudgeController {
    @Resource
    PaperResultJudgeService paperResultJudgeService;

    @GetMapping("/getDescription/{paperId}")
    public String getDescription(@PathVariable("paperId") String paperId, @RequestParam(value = "paperScore") Integer paperScore){
        String description = paperResultJudgeService.getDescription(paperId, paperScore);
        return description;
    }

    @PostMapping
    public Result add(@RequestBody PaperDetail paperDetail) throws UnknownHostException{
        paperResultJudgeService.add(paperDetail);
        return Result.success();
    }

    @GetMapping("/isExist/{paperId}")
    public Result isExist(@PathVariable("paperId") String paperId){
        List<PaperResultJudge> paperResultJudges = paperResultJudgeService.findByPaperId(paperId);
        if (paperResultJudges.size() == 0){
            return Result.error("1","none");
        } else {
            return Result.success();
        }
    }

    @GetMapping("/findByPaperId/{paperId}")
    public Result<List<PaperResultJudge>> findByPaperId(@PathVariable("paperId") String paperId){
        List<PaperResultJudge> paperResultJudges = paperResultJudgeService.findByPaperId(paperId);
        return Result.success(paperResultJudges);
    }

    @PutMapping
    public Result update(@RequestBody PaperDetail paperDetail) throws UnknownHostException{
        paperResultJudgeService.update(paperDetail);
        return Result.success();
    }

}
