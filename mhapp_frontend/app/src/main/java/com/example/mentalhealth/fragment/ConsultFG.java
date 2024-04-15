package com.example.mentalhealth.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.mentalhealth.Adapter.DoctorListAdapter;
import com.example.mentalhealth.R;
import com.example.mentalhealth.doctorDetail;
import com.example.mentalhealth.entity.DoctorListArray;

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

public class ConsultFG extends Fragment {

    private Button orderBtn;
    private ListView doctorlistView;
    private List<DoctorListArray> doctorlist;
    private View view;

    private void setShape(int drawableId, Button button) {
        Drawable drawable_news = getResources().getDrawable(drawableId);
        drawable_news.setBounds(20, 0, 130, 110);
        button.setCompoundDrawables(drawable_news,null,null, null);
        //radioButton.setCompoundDrawablePadding(10);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    //加载Fragment中的layout
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.consult_fg,null);
        initView();
        fillData(doctorlistView);
        return view;
    }

    public void initView(){
        orderBtn = view.findViewById(R.id.order_doctor);
        setShape(R.drawable.order_icon,orderBtn);
        doctorlistView = view.findViewById(R.id.doctorlist);
        doctorlist = new ArrayList<>();
    }

    public void fillData(View view){
        String url = "http://172.20.10.10:8081/popmh/doctorInfo/all";
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(url).get().build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail","call加入队列失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String json = body.string();
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray doctorArray = JSON.parseArray(json);
                        int size = doctorArray.size();
                        for(int i=0; i<size; i++){
                            JSONObject doctorObject = doctorArray.getJSONObject(i);
                            String id = doctorObject.getString("id");
                            String nickname = doctorObject.getString("nickname");
                            String sign = doctorObject.getString("sign");
                            String intro = doctorObject.getString("intro");
                            DoctorListArray doctorListArray = new DoctorListArray(id,nickname,sign,intro);
                            doctorlist.add(doctorListArray);
                        }
                        DoctorListAdapter doctorListAdapter = new DoctorListAdapter(view.getContext(),R.layout.doctor_lv,doctorlist);
                        doctorlistView.setAdapter(doctorListAdapter);

                        doctorlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String url="http://172.20.10.10:8081/popmh/doctorInfo/";
                                StringBuilder stringBuilder = new StringBuilder(url);
                                stringBuilder.append(doctorlist.get(position).getDoctorId().toString());
                                Intent intent = new Intent(view.getContext(), doctorDetail.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("key",stringBuilder.toString());
                                bundle.putString("id",doctorlist.get(position).getDoctorId());
                                bundle.putString("nickname",doctorlist.get(position).getNickname());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }
}
