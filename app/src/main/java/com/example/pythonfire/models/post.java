package com.example.pythonfire.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class post {

    private String documentId;
    private String nickname;
    private String title;
    private String contents;
    @ServerTimestamp
    private Date date;

    public post(){

    }
    public post(String documentId, String nickname, String title, String contents){
        this.documentId = documentId;
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "post{" +
                "documentId='" + documentId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", date=" + date +
                '}';
    }
}
