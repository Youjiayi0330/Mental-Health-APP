package com.example.mentalhealth.entity;

import java.util.List;

public class QuestionInfo {
    private String id;
    private String questionTitle;
    private List<AnswerInfo> answerInfoList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public List<AnswerInfo> getAnswerInfoList() {
        return answerInfoList;
    }

    public void setAnswerInfoList(List<AnswerInfo> answerInfoList) {
        this.answerInfoList = answerInfoList;
    }
}
