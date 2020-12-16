package com.example.digikalastore.model;

public class Review {

    private int mId;
    private int mProductId;
    private int mRating;

    public Review(int id, int productId, int rating) {
        mId = id;
        mProductId = productId;
        mRating = rating;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }
}
