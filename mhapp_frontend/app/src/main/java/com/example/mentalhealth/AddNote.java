package com.example.mentalhealth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mentalhealth.Adapter.ImageSelectorAdapter;
import com.donkingliang.imageselector.utils.*;
import com.example.mentalhealth.fragment.CommunityFG;
import com.example.mentalhealth.netty.AppInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class AddNote extends AppCompatActivity {
    private Button publishBtn, addImagesBtn, addCoverBtn;
    private EditText nameEdt, contentEdt;
    private RecyclerView imagesRv, coverRv;
    private ImageSelectorAdapter imageSelectorAdapter;
    private ImageSelectorAdapter imageSelectorAdapter1;

    private ArrayList<String> imagesPath = new ArrayList<>();
    private ArrayList<String> coverPath = new ArrayList<>();

    public static final int REQUEST_CODE_IMAGES = 100;
    public static final int REQUEST_CODE_COVER = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initView();
        imagesRv.setLayoutManager(new GridLayoutManager(this,3));
        coverRv.setLayoutManager(new GridLayoutManager(this,1));
        imageSelectorAdapter = new ImageSelectorAdapter(this);
        imageSelectorAdapter1 = new ImageSelectorAdapter(this);
        imagesRv.setAdapter(imageSelectorAdapter);
        coverRv.setAdapter(imageSelectorAdapter1);
        addImagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelector.builder()
                        .useCamera(false) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(AddNote.this,REQUEST_CODE_IMAGES);

            }
        });
        addCoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelector.builder()
                        .useCamera(false) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(AddNote.this,REQUEST_CODE_COVER);
            }
        });
        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteId = AppInfo.personNow.getId()+System.currentTimeMillis();
                scImages(noteId);
                scNoteInfo(noteId);
                Toast.makeText(AddNote.this,"已发布",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddNote.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGES && data != null) {
            imagesPath = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            imageSelectorAdapter.refresh(imagesPath);
            System.out.println(imagesPath);
        } else if(requestCode == REQUEST_CODE_COVER && data != null) {
            coverPath = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            imageSelectorAdapter1.refresh(coverPath);
            System.out.println(coverPath);
        }
    }

    private void scNoteInfo(String noteId){
        File file = new File(coverPath.get(0));
        String url = "http://172.20.10.10:8081/popmh/noteInfo/addNote";
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("noteId",noteId)
                .addFormDataPart("userId",AppInfo.personNow.getId())
                .addFormDataPart("noteName",nameEdt.getText().toString())
                .addFormDataPart("content",contentEdt.getText().toString())
                .addFormDataPart("uploadCover", "coverName", RequestBody.create(MediaType.parse("*/*"), file))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("File","上传图片请求队列失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    private void scImages(String noteId){
        String url = "http://172.20.10.10:8081/popmh/noteInfo/uploadImages";
        String userId = AppInfo.personNow.getId();
        for(int i=0;i<imagesPath.size();i++){
            File file = new File(imagesPath.get(i));
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)//请求类型
                    .addFormDataPart("noteId",noteId)
                    .addFormDataPart("name", userId+i)
                    // 第一个参数传到服务器的字段名，第二个你自己的文件名，第三个MediaType.parse("*/*")数据类型，这个是所有类型的意思
                    .addFormDataPart("uploadfile", imagesPath.get(i), RequestBody.create(MediaType.parse("*/*"), file))
                    .build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(url).post(requestBody).build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("File","上传图片请求队列失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        }
    }

    private void initView(){
        publishBtn = findViewById(R.id.add_note_publish);
        addImagesBtn = findViewById(R.id.add_note_images_btn);
        addCoverBtn = findViewById(R.id.add_note_cover_btn);
        nameEdt = findViewById(R.id.add_note_name);
        contentEdt = findViewById(R.id.add_note_content);
        imagesRv = findViewById(R.id.add_note_images);
        coverRv = findViewById(R.id.add_note_cover_image);
    }
}