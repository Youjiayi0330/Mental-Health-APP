package com.example.mentalhealth.netty.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import lombok.Data;

@Data
public class ChatListVo implements Parcelable {
    private String personId;
    private String personName;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer hasRead;

    public ChatListVo(){

    }

    public ChatListVo(String personId, String personName, String message, Date createTime, Integer hasRead){
        this.personId = personId;
        this.personName = personName;
        this.message = message;
        this.createTime = createTime;
        this.hasRead = hasRead;
    }

    protected ChatListVo(Parcel in) {
        personId = in.readString();
        personName = in.readString();
        message = in.readString();
        if(in.readByte() == 0){
            hasRead = null;
        } else {
            hasRead = in.readInt();
        }
    }

    public static final Creator<ChatListVo> CREATOR = new Creator<ChatListVo>() {
        @Override
        public ChatListVo createFromParcel(Parcel in) {
            ChatListVo v = new ChatListVo();
            v.personId = in.readString();
            v.personName = in.readString();
            v.message = in.readString();
            v.createTime = (Date)in.readSerializable();
            v.hasRead = in.readInt();
            return v;
        }

        @Override
        public ChatListVo[] newArray(int size) {
            return new ChatListVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(personId);
        parcel.writeString(personName);
        parcel.writeString(message);
        parcel.writeSerializable(createTime);
        parcel.writeInt(hasRead);
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getHasRead() {
        return hasRead;
    }

    public void setHasRead(Integer hasRead) {
        this.hasRead = hasRead;
    }
}
