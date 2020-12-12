package com.example.digikalastore.model;

public class Category implements Comparable{

    private String mName;
    private String mId;

    public Category() {
    }

    public Category(String name, String id) {
        mName = name;
        mId = id;
    }

    public String getName() {

        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
