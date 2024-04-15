package com.example.mentalhealth.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mentalhealth.MyPaperTest;
import com.example.mentalhealth.R;
import com.example.mentalhealth.entity.DoctorListArray;
import com.example.mentalhealth.entity.MyPaperTestArray;
import com.example.mentalhealth.paperDetail;

import java.util.List;

public class MyPaperTestAdapter extends ArrayAdapter<MyPaperTestArray> {
    private int resourceId;

    public MyPaperTestAdapter(@NonNull Context context, int resource, List<MyPaperTestArray> objects){
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int poisition, @Nullable View convertView, @NonNull ViewGroup parent){
        MyPaperTestArray myPaperTestArray = getItem(poisition);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView tv_paperTitle = view.findViewById(R.id.my_paperTitle);
        TextView tv_description = view.findViewById(R.id.my_paperDescription);
        Button testAgainBtn = view.findViewById(R.id.testAgainBtn);
        tv_paperTitle.setText(myPaperTestArray.getPaperTitle());
        tv_description.setText(myPaperTestArray.getDescription());
        testAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paperResultId = myPaperTestArray.getId();
                String paperId = myPaperTestArray.getPaperId();
                String paperTitle = myPaperTestArray.getPaperTitle();
                Intent intent = new Intent(view.getContext(), paperDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("paperResultId", paperResultId);
                bundle.putString("paperId", paperId);
                bundle.putString("paperTitle",paperTitle);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
}
