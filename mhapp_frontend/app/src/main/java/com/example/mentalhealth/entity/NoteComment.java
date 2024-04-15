package com.example.mentalhealth.entity;

public class NoteComment {
    private String id;
    private String time;
    private String content;
    private String userName;

    public NoteComment(String id, String time, String content, String userName) {
        this.id = id;
        this.time = time;
        this.content = content;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
