package com.example.mentalhealth.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mentalhealth.R;
import com.example.mentalhealth.entity.DoctorListArray;
import com.example.mentalhealth.entity.ImageListArray;

import java.util.List;

public class DoctorListAdapter extends ArrayAdapter<DoctorListArray> {
    private int recourceId;

    public DoctorListAdapter(Context context, int resource, List<DoctorListArray> objects) {
        super(context, resource, objects);
        recourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int poisition, @Nullable View convertView, @NonNull ViewGroup parent){
        DoctorListArray doctorListArray = getItem(poisition);
        View view = LayoutInflater.from(getContext()).inflate(recourceId,parent,false);
        TextView tv_doctor_nickname = view.findViewById(R.id.lv_doctor_nickname);
        TextView tv_doctor_sign = view.findViewById(R.id.lv_doctor_sign);
        tv_doctor_nickname.setText(doctorListArray.getNickname());
        tv_doctor_sign.setText(doctorListArray.getSign());
        return view;
    }
}
