package com.example.mentalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.Adapter.QuestionPageAdapter;
import com.example.mentalhealth.entity.AnswerInfo;
import com.example.mentalhealth.entity.NoteComment;
import com.example.mentalhealth.entity.QuestionInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class paperDetail extends AppCompatActivity {

    private TextView tv_paperTitle;
    private ViewPager viewPager;
    private Button submitBtn;

    private String paperId, paperTitle, paperResultId;
    private List<QuestionInfo> questionInfoList = new ArrayList<>();
    private List<String> questionIdList = new ArrayList<>();
    private QuestionPageAdapter questionPageAdapter;
    private Integer paperScore = 0;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                if(msg.what == 0){
                    List<QuestionInfo> questionInfoList = (List<QuestionInfo>) msg.obj;
                    questionPageAdapter = new QuestionPageAdapter(paperDetail.this,questionInfoList);
                    viewPager.setAdapter(questionPageAdapter);
                }
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_detail);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle.getString("paperResultId") != null){
            paperResultId = bundle.getString("paperResultId");
        }
        paperId = bundle.getString("paperId");
        paperTitle = bundle.getString("paperTitle");
        tv_paperTitle.setText(paperTitle);
        loadView();
        setListener();
    }

    public void setListener(){
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Integer> questionMap = questionPageAdapter.getPaperScore();
                for(int i=0;i<questionIdList.size();i++){
                    String questionId = questionIdList.get(i);
                    int questionScore = questionMap.get(questionId);
                    paperScore += questionScore;
                }
                System.out.println(paperScore);
                Intent intent = new Intent(paperDetail.this, PaperResult.class);
                Bundle bundle = new Bundle();
                if(paperResultId != null){
                    bundle.putString("paperResultId", paperResultId);
                }
                bundle.putString("paperId", paperId);
                bundle.putString("paperTitle",paperTitle);
                bundle.putInt("paperScore", paperScore);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void loadView(){
        String url = "http://172.20.10.10:8081/popmh/paper/findQuestionAndAnswerById/"+paperId;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","获取问卷题目和选项失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String json = body.string();
                JSONArray questionArray = JSON.parseArray(json);
                for(int i=0;i<questionArray.size();i++){
                    QuestionInfo questionInfo = new QuestionInfo();
                    JSONObject questionObject = questionArray.getJSONObject(i);
                    questionInfo.setId(questionObject.getString("id"));
                    questionIdList.add(questionObject.getString("id"));
                    questionInfo.setQuestionTitle(questionObject.getString("questionTitle"));
                    JSONArray answerArray = questionObject.getJSONArray("answerList");
                    List<AnswerInfo> answerInfoList = new ArrayList<>();
                    for(int j=0;j<answerArray.size();j++){
                        AnswerInfo answerInfo = new AnswerInfo();
                        JSONObject answerObject = answerArray.getJSONObject(j);
                        answerInfo.setId(answerObject.getString("id"));
                        answerInfo.setAnswer(answerObject.getString("answer"));
                        answerInfo.setValue(answerObject.getInteger("value"));
                        answerInfoList.add(answerInfo);
                    }
                    questionInfo.setAnswerInfoList(answerInfoList);
                    questionInfoList.add(questionInfo);
                }
                Message message = new Message();
                message.what = 0;
                message.obj = questionInfoList;
                handler.sendMessage(message);
//                questionPageAdapter = new QuestionPageAdapter(paperDetail.this,questionInfoList);
//                viewPager.setAdapter(questionPageAdapter);
            }
        });
    }

    public void initView(){
        tv_paperTitle = findViewById(R.id.paper_detail_paperTitle);
        viewPager = findViewById(R.id.paper_questions);
        submitBtn = findViewById(R.id.submit_paper);
    }
}