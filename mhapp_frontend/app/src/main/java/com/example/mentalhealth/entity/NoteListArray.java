package com.example.mentalhealth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class NoteListArray {
    String id;
    String name;
    String userName;
    Integer userLevel;
    String time;
    String coverId;

    public NoteListArray(String id, String name, String userName, Integer userLevel, String time, String coverId) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.userLevel = userLevel;
        this.time = time;
        this.coverId = coverId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public String getTime() {
        return time;
    }

    public String getCoverId() {
        return coverId;
    }
}
