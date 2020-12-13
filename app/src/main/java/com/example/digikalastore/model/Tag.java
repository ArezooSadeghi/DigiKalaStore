package com.example.digikalastore.model;

public class Tag {

    private String mId;
    private String mName;

    public Tag() {
    }

    public Tag(String id, String name) {
        mId = id;
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
