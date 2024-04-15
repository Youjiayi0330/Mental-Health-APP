package com.example.mentalhealth.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mentalhealth.R;
import com.example.mentalhealth.entity.AnswerInfo;
import com.example.mentalhealth.entity.QuestionInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionPageAdapter extends PagerAdapter {
    private List<QuestionInfo> questionInfoList;
    private Context context;
    // <问题id,被选择选项得分>
    Map<String, Integer> questionMap = new HashMap<>();

    public QuestionPageAdapter(){};

    public QuestionPageAdapter(Context context, List<QuestionInfo> questionInfoList) {
        this.context = context;
        this.questionInfoList = questionInfoList;
    }

    @Override
    public int getCount() {
        return questionInfoList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.paper_viewpager,null,false);
        TextView tv_questionTitle = view.findViewById(R.id.tv_questionTitle);
        RadioGroup answerGp = view.findViewById(R.id.answer_radiogroup);
        String questionTitle = questionInfoList.get(position).getQuestionTitle();
        tv_questionTitle.setText(questionTitle);
        List<AnswerInfo> answerInfoList = questionInfoList.get(position).getAnswerInfoList();

        String questionId = questionInfoList.get(position).getId();
        for (int i=0;i<answerInfoList.size();i++){
            AnswerInfo answerInfo = answerInfoList.get(i);
            //动态添加选项按钮
            RadioButton answerBtn = new RadioButton(view.getContext());
            String answer = answerInfo.getAnswer();
            answerBtn.setText(answer);
            answerBtn.setTextColor(Color.BLACK);
            answerBtn.setTextSize(15);
            answerBtn.setBackground(context.getResources().getDrawable(R.drawable.answer_radio));
            answerGp.addView(answerBtn);
            //如果该选项被选择，记录选项得分，即该问题得分
            answerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    questionMap.put(questionId,answerInfo.getValue());
                }
            });
        }
        ViewGroup parent=(ViewGroup) view.getParent();
        if(parent!=null)
            parent.removeView(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        //container.removeView(ivList.get(position));
    }

    public Map<String,Integer> getPaperScore(){
        return questionMap;
    }
}
