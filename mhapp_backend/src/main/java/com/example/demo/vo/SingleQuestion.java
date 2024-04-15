package com.example.demo.vo;

import com.example.demo.entity.Answer;
import lombok.Data;

@Data
public class SingleQuestion {
    private String question;
    private Answer answer;
}
