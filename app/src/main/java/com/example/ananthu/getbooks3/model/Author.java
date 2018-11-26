package com.example.ananthu.getbooks3.model;

import java.io.Serializable;
import java.util.List;

public class Author implements Serializable {

    private String name;
    private int id;
    private String img;
    private String about;
    private List<Integer> bookIds;

    Author() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Integer> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Integer> bookIds) {
        this.bookIds = bookIds;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", img='" + img + '\'' +
                ", description='" + about + '\'' +
                ", bookIds=" + bookIds +
                '}';
    }
}
