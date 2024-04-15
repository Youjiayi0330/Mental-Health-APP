package com.example.mentalhealth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.example.mentalhealth.Adapter.ImageSelectorAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class doctor_certify extends AppCompatActivity {

    private EditText realNameEdt, creditIdEdt;
    private Button creditBtn, certifyBtn, submitBtn;
    private RecyclerView creditPhotoRv, certifyPhotoRv;
    private ImageSelectorAdapter imageSelectorAdapter, imageSelectorAdapter1;
    private ArrayList<String> creditPhotoPath = new ArrayList<>();
    private ArrayList<String> certifyPhotoPath = new ArrayList<>();
    private String id;

    public static final int REQUEST_CODE_CREDIT = 100;
    public static final int REQUEST_CODE_CERTIFY = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_certify);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getString("id");
        creditPhotoRv.setLayoutManager(new GridLayoutManager(this,1));
        certifyPhotoRv.setLayoutManager(new GridLayoutManager(this,1));
        imageSelectorAdapter = new ImageSelectorAdapter(this);
        imageSelectorAdapter1 = new ImageSelectorAdapter(this);
        creditPhotoRv.setAdapter(imageSelectorAdapter);
        certifyPhotoRv.setAdapter(imageSelectorAdapter1);
        creditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelector.builder()
                        .useCamera(false) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(doctor_certify.this,REQUEST_CODE_CREDIT);
            }
        });
        certifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelector.builder()
                        .useCamera(false) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(doctor_certify.this,REQUEST_CODE_CERTIFY);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scDoctorCertify();
                scCertifyPhoto();
                AlertDialog.Builder builder = new AlertDialog.Builder(doctor_certify.this);
                builder.setMessage("三个工作日内会将认证结果发送到您的邮箱，请注意查收。").setTitle("已提交认证");
                builder.setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(doctor_certify.this,MainActivity.class);
                                startActivity(intent);
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CREDIT && data != null) {
            creditPhotoPath = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            imageSelectorAdapter.refresh(creditPhotoPath);
            System.out.println(creditPhotoPath);
        } else if(requestCode == REQUEST_CODE_CERTIFY && data != null) {
            certifyPhotoPath = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            imageSelectorAdapter1.refresh(certifyPhotoPath);
            System.out.println(certifyPhotoPath);
        }
    }

    private void scDoctorCertify(){
        File file = new File(creditPhotoPath.get(0));
        String url = "http://172.20.10.10:8081/popmh/doctorPrivacy";
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id",id)
                .addFormDataPart("realName",realNameEdt.getText().toString())
                .addFormDataPart("creditId",creditIdEdt.getText().toString())
                .addFormDataPart("creditPhoto",creditPhotoPath.get(0),RequestBody.create(MediaType.parse("*/*"), file))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","上传医生认证失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void scCertifyPhoto(){
        File file = new File(certifyPhotoPath.get(0));
        String url = "http://172.20.10.10:8081/popmh/doctorPrivacy/addCertification";
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id",id)
                .addFormDataPart("certifyPhoto",certifyPhotoPath.get(0),RequestBody.create(MediaType.parse("*/*"), file))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","上传医生认证失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void initView(){
        realNameEdt = findViewById(R.id.realName_edt);
        creditIdEdt = findViewById(R.id.creditId_edt);
        creditBtn = findViewById(R.id.add_credit_btn);
        certifyBtn = findViewById(R.id.add_certify_btn);
        submitBtn = findViewById(R.id.submit_certify);
        creditPhotoRv = findViewById(R.id.creditPhoto_rv);
        certifyPhotoRv = findViewById(R.id.certifyPhoto_rv);
    }
}