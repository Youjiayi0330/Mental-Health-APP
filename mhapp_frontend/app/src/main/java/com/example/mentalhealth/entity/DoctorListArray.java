package com.example.mentalhealth.entity;

public class DoctorListArray {
    private String doctorId;
    private String nickname;
    private String sign;
    private String intro;

    public DoctorListArray(String doctorId, String nickname, String sign, String intro){
        this.doctorId = doctorId;
        this.nickname = nickname;
        this.sign = sign;
        this.intro = intro;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSign() {
        return sign;
    }

    public String getIntro() {
        return intro;
    }
}
