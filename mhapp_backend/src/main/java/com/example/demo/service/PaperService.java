package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Paper;
import com.example.demo.entity.Passage;
import com.example.demo.entity.Question;
import com.example.demo.vo.PaperDetail;
import com.example.demo.vo.QuestionVo;
import com.github.pagehelper.PageInfo;

import java.net.UnknownHostException;
import java.util.List;

public interface PaperService extends IService<Paper> {
    List<Paper> findAll();

    List<QuestionVo> findQuestionAndAnswer(String paperId);

    void add(PaperDetail paperDetail) throws UnknownHostException;

    PageInfo<Paper> findAllPaper(Integer pageNum, Integer pageSize);

    List<Question> findQuestion(String paperId);

    List<Answer> findAnswer(String questionId);

    void update(PaperDetail paperDetail) throws UnknownHostException;

    void delete(String id);

    String findPaperName(String id);
}
