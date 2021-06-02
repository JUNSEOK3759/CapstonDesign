package com.example.pythonfire;

public class Board {
    private String iden;
    private String title;
    private String contents;
    private String name;

    public Board(){

    }
    public Board(String id, String title, String contents, String name){
        this.iden = iden;
        this.title = title;
        this.contents = contents;
        this.name = name;
    }

    public String getIden() {
        return iden;
    }

    public void setIden(String iden) {
        this.iden = iden;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Board{" +
                "iden='" + iden + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
