package com.example.digikalastore.model;

public class Category {

    private String mName;
    private int mId;

    public Category(String name, int id) {
        mName = name;
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
