package com.example.mentalhealth.entity;

public class MyPaperTestArray {
    private String id;
    private String paperId;
    private String paperTitle;
    private String description;

    public MyPaperTestArray(){

    }

    public MyPaperTestArray(String id, String paperId, String paperTitle, String description) {
        this.id = id;
        this.paperId = paperId;
        this.paperTitle = paperTitle;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
