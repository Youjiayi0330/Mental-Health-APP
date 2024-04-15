package com.example.demo.service.impl;

import cn.hutool.core.net.NetUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Answer;
import com.example.demo.entity.Paper;
import com.example.demo.entity.Passage;
import com.example.demo.entity.Question;
import com.example.demo.mappers.AnswerMapper;
import com.example.demo.mappers.PaperMapper;
import com.example.demo.mappers.QuestionMapper;
import com.example.demo.service.PaperService;
import com.example.demo.vo.PaperDetail;
import com.example.demo.vo.QuestionVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperService {
    @Resource
    PaperMapper paperMapper;
    @Resource
    QuestionMapper questionMapper;
    @Resource
    AnswerMapper answerMapper;

    @Override
    public List<Paper> findAll() {
        return paperMapper.findAll();
    }

    @Override
    public List<QuestionVo> findQuestionAndAnswer(String paperId) {
        List<QuestionVo> questionVoList = new ArrayList<>();
        List<Question> questions = questionMapper.findById(paperId);
        for(int i = 0; i<questions.size();i++){
            String questionId = questions.get(i).getId();
            String questionTitle = questions.get(i).getQuestionTitle();
            String paperId1 = questions.get(i).getPaperId();
            List<Answer> answerList = answerMapper.findById(questionId);
            QuestionVo questionVo = new QuestionVo();
            questionVo.setId(questionId);
            questionVo.setQuestionTitle(questionTitle);
            questionVo.setAnswerList(answerList);
            questionVo.setPaperId(paperId1);
            questionVoList.add(questionVo);
        }
        return questionVoList;
    }

    @Override
    public void add(PaperDetail paperDetail) throws UnknownHostException {
        Long time = System.currentTimeMillis();
        InetAddress inetAddress = InetAddress.getLocalHost();
        String macAddress = NetUtil.getMacAddress(inetAddress);
        String paperId = macAddress + time.toString();
        Paper paper = new Paper();
        paper.setId(paperId);
        paper.setPaperTitle(paperDetail.getName());
        //添加问卷
        paperMapper.insert(paper);
        JSONArray passageArray= JSON.parseArray(paperDetail.getValue());
        for(int i=0; i<passageArray.size();i++){
            Map map = JSON.parseObject(passageArray.get(i).toString(), Map.class);
            Question question = new Question();
            String questionId = paperId+i;
            question.setId(questionId);
            question.setQuestionTitle(map.get("question").toString());
            question.setPaperId(paperId);
            //添加问题
            questionMapper.insert(question);
            JSONArray array = (JSONArray) map.get("answerList");
            for(int j=0;j<array.size();j++){
                Map answerMap = JSON.parseObject(array.get(j).toString(), Map.class);
                Answer answer = new Answer();
                answer.setId(paperId + i + j);
                answer.setAnswer(answerMap.get("answer").toString());
                answer.setValue(Integer.valueOf(answerMap.get("score").toString()));
                answer.setQuestionId(questionId);
                //添加选项
                answerMapper.insert(answer);
            }
        }
    }

    @Override
    public PageInfo<Paper> findAllPaper(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize,"");
        List<Paper> list = paperMapper.findAll();
        return PageInfo.of(list);
    }

    @Override
    public List<Question> findQuestion(String paperId) {
        List<Question> questionList = questionMapper.findById(paperId);
        return questionList;
    }

    @Override
    public List<Answer> findAnswer(String questionId) {
        List<Answer> answerList = answerMapper.findById(questionId);
        return answerList;
    }

    @Override
    public void update(PaperDetail paperDetail) throws UnknownHostException {
        String paperId = paperDetail.getId();
        Paper paper = new Paper();
        paper.setId(paperId);
        paper.setPaperTitle(paperDetail.getName());
        //更新问卷
        paperMapper.updateById(paper);
        JSONArray passageArray= JSON.parseArray(paperDetail.getValue());
        for(int i=0; i<passageArray.size();i++){
            Map map = JSON.parseObject(passageArray.get(i).toString(), Map.class);
            Question question = new Question();
            String questionId = map.get("questionId").toString();
            question.setId(questionId);
            question.setQuestionTitle(map.get("question").toString());
            question.setPaperId(paperId);
            //更新问题
            questionMapper.updateById(question);
            JSONArray array = (JSONArray) map.get("answerList");
            for(int j=0;j<array.size();j++){
                Map answerMap = JSON.parseObject(array.get(j).toString(), Map.class);
                Answer answer = new Answer();
                String answerId = answerMap.get("answerId").toString();
                answer.setId(answerId);
                answer.setAnswer(answerMap.get("answer").toString());
                answer.setValue(Integer.valueOf(answerMap.get("score").toString()));
                answer.setQuestionId(questionId);
                //更新选项
                answerMapper.updateById(answer);
            }
        }
    }

    @Override
    public void delete(String paperId) {
        List<Question> questionList = questionMapper.findById(paperId);
        for(int i=0;i<questionList.size(); i++){
            String questionId = questionList.get(i).getId();
            List<Answer> answerList = answerMapper.findById(questionId);
           for(int j=0;j<answerList.size();j++){
               answerMapper.deleteById(answerList.get(j));
           }
           questionMapper.deleteById(questionList.get(i));
        }
        Paper paper = paperMapper.selectById(paperId);
        paperMapper.deleteById(paper);
    }

    @Override
    public String findPaperName(String id) {
        Paper paper = paperMapper.selectById(id);
        return paper.getPaperTitle();
    }
}
