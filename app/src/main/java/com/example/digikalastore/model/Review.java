package com.example.digikalastore.model;

public class Review {

    private int mProductId;
    private String mReviewContent;
    private String mReviewerName;
    private String mReviewerEmail;
    private int mRating;

    public Review(int productId, String reviewContent, String reviewerName, String reviewerEmail, int rating) {
        mProductId = productId;
        mReviewContent = reviewContent;
        mReviewerName = reviewerName;
        mReviewerEmail = reviewerEmail;
        mRating = rating;
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

    public String getReviewerName() {
        return mReviewerName;
    }

    public void setReviewerName(String reviewerName) {
        mReviewerName = reviewerName;
    }

    public String getReviewContent() {
        return mReviewContent;
    }

    public void setReviewContent(String reviewContent) {
        mReviewContent = reviewContent;
    }

    public String getReviewerEmail() {
        return mReviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        mReviewerEmail = reviewerEmail;
    }
}
