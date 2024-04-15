package com.example.mentalhealth.entity;

public class ImageListArray {
    private Long pid;
    private String ptitle;
    private String ptime;
    private String FileName;
    private Integer collectCount;

    public ImageListArray(Long pid,String ptitle,String ptime,String FileName,Integer collectCount){
        this.pid=pid;
        this.ptitle=ptitle;
        this.ptime=ptime;
        this.FileName=FileName;
        this.collectCount = collectCount;
    }

    public Long getPid() {
        return pid;
    }

    public String getPtitle() {
        return ptitle;
    }

    public String getPtime() {
        return ptime;
    }

    public String getFileName() {
        return FileName;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

}
