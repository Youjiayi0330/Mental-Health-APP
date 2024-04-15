package com.example.mentalhealth.entity;

public class CommentListArray {
    private String id;
    private String userName;
    private String time;
    private String content;

    public CommentListArray(String id, String userName, String time, String content) {
        this.id = id;
        this.userName = userName;
        this.time = time;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
