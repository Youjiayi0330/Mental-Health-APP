package com.example.demo.controller;

import cn.hutool.core.net.NetUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.common.Result;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Paper;
import com.example.demo.entity.Passage;
import com.example.demo.entity.Question;
import com.example.demo.service.PaperService;
import com.example.demo.vo.PaperDetail;
import com.example.demo.vo.QuestionVo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/paper")
public class PaperController {
    @Resource
    PaperService paperService;

    @GetMapping("/all")
    public List<Paper> findAll(){
        List<Paper> paperList = paperService.findAll();
        return paperList;
    }

    @GetMapping("/findQuestionAndAnswerById/{paperId}")
    public List<QuestionVo> findQuestionAndAnswer(@PathVariable("paperId") String paperId){
        List<QuestionVo> questionList = paperService.findQuestionAndAnswer(paperId);
        return questionList;
    }

    @PostMapping
    public Result add(@RequestBody PaperDetail paperDetail) throws UnknownHostException {
        paperService.add(paperDetail);
        return Result.success();
    }

    @GetMapping("/findAllPaper")
    public Result<PageInfo<Paper>> findDraft(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "2") Integer pageSize){
        return Result.success(paperService.findAllPaper(pageNum,pageSize));
    }

    @GetMapping("/findQuestion/{paperId}")
    public Result<List<Question>> findQuestion(@PathVariable(value = "paperId") String paperId){
        List<Question> questionList = paperService.findQuestion(paperId);
        return Result.success(questionList);
    }

    @GetMapping("/findAnswer/{questionId}")
    public Result<List<Answer>> findAnswer(@PathVariable(value = "questionId") String questionId){
        List<Answer> answerList = paperService.findAnswer(questionId);
        return Result.success(answerList);
    }

    @PutMapping
    public Result update(@RequestBody PaperDetail paperDetail) throws UnknownHostException{
        paperService.update(paperDetail);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable(value = "id") String id){
        paperService.delete(id);
        return Result.success();
    }

    @GetMapping("/findPaperName/{id}")
    public Result<String> findPaperName(@PathVariable("id")String id){
        String paperName =  paperService.findPaperName(id);
        return Result.success(paperName);
    }
}
