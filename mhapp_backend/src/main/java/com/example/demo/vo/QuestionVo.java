package com.example.demo.vo;

import com.example.demo.entity.Answer;
import com.example.demo.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionVo extends Question {
    private List<Answer> answerList;
}
