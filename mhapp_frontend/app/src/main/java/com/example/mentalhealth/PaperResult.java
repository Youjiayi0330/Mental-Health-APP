package com.example.mentalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mentalhealth.Adapter.QuestionPageAdapter;
import com.example.mentalhealth.entity.QuestionInfo;
import com.example.mentalhealth.netty.AllHandler;
import com.example.mentalhealth.netty.AppInfo;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PaperResult extends AppCompatActivity {

    private String paperId, paperTitle, paperResultId;
    private Integer paperScore;
    private String description;

    private TextView tv_paperTitle, tv_paperJudge;
    private Button backPaperHomeBtn, saveResultBtn;

    private Handler handler;

    private static final String BASE_PATH = "http://172.20.10.10:8081/popmh/paperResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);
                if(msg.what == 0){
                    tv_paperJudge.setText((String) msg.obj);
                }
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_result);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle.getString("paperResultId") != null){
            paperResultId = bundle.getString("paperResultId");
        }
        paperId = bundle.getString("paperId");
        paperTitle = bundle.getString("paperTitle");
        paperScore = bundle.getInt("paperScore");
        tv_paperTitle.setText(paperTitle);
        getScoreJudge();
        setListener();
    }

//    @Override//点击返回键后返回问卷列表
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent intent = new Intent(PaperResult.this,PaperList.class);
//            startActivity(intent);
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    public void setListener(){
        saveResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = BASE_PATH;
                OkHttpClient okHttpClient = new OkHttpClient();
                if(paperResultId == null){
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("id",AppInfo.personNow.getId()+paperId+System.currentTimeMillis())
                            .addFormDataPart("userId", AppInfo.personNow.getId())
                            .addFormDataPart("paperId",paperId)
                            .addFormDataPart("paperScore",paperScore.toString())
                            .addFormDataPart("description",description)
                            .build();
                    Request request = new Request.Builder().url(url).post(requestBody).build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("File","上传问卷结果失败");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Looper.prepare();
                            Toast.makeText(PaperResult.this,"保存成功",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    });
                } else {
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("id",paperResultId)
                            .addFormDataPart("paperScore",paperScore.toString())
                            .addFormDataPart("description",description)
                            .build();
                    Request request = new Request.Builder().url(url).put(requestBody).build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("File","更新问卷结果失败");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Looper.prepare();
                            Toast.makeText(PaperResult.this,"保存成功",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    });
                }
            }
        });
        backPaperHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaperResult.this,PaperList.class);
                startActivity(intent);
            }
        });
    }

    public void getScoreJudge(){
        String url = "http://172.20.10.10:8081/popmh/paperResultJudge/getDescription/"+paperId+"?paperScore="+paperScore;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","请求问卷评定失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                description = body.string();
                Message message = new Message();
                message.what = 0;
                message.obj = description;
                handler.sendMessage(message);
            }
        });
    }

    private void initView(){
        tv_paperTitle = findViewById(R.id.paper_result_paperTitle);
        tv_paperJudge = findViewById(R.id.paper_result_judge);
        backPaperHomeBtn = findViewById(R.id.back_paper_home);
        saveResultBtn = findViewById(R.id.save_result);
    }
}