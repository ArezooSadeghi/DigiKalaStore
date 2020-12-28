package com.example.digikalastore.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable, Comparable {

    private String mName;
    private int mId;
    private int mRatingCount;
    private List<String> mImageUrl;
    private String mDescription;
    private String mPrice;
    private List<Category> mCategory;
    private List<Review> mReviews;
    private ArrayList<Tag> mTags;
    private String mAverageRate;
    private String mStockStatus;
    private boolean mIsFeatured;

    public Product() {
    }

    public Product(String name, int id, List<String> imageUrl, String description, String price, String AverageRate, boolean isFeatured) {
        mName = name;
        mId = id;
        mImageUrl = imageUrl;
        mDescription = description;
        mPrice = price;
        mAverageRate = AverageRate;
        mIsFeatured = isFeatured;
    }

    public List<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }

    public String getAverageRate() {
        return mAverageRate;
    }

    public String getStockStatus() {
        return mStockStatus;
    }

    public void setStockStatus(String stockStatus) {
        mStockStatus = stockStatus;
    }

    public void setAverageRate(String averageRate) {
        mAverageRate = averageRate;
    }

    public ArrayList<Tag> getTags() {
        return mTags;
    }

    public void setTags(ArrayList<Tag> tags) {
        mTags = tags;
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

    public List<String> getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public List<Category> getCategory() {
        return mCategory;
    }

    public void setCategory(List<Category> category) {
        mCategory = category;
    }

    public int getRatingCount() {
        return mRatingCount;
    }

    public void setRatingCount(int ratingCount) {
        mRatingCount = ratingCount;
    }

    public boolean isFeatured() {
        return mIsFeatured;
    }

    public void setFeatured(boolean featured) {
        mIsFeatured = featured;
    }

    @BindingAdapter("productImage")
    public static void loadImage(ImageView view, String url) {
        Picasso.get().load(url).into(view);
    }

    @Override
    public int compareTo(Object o) {
        Product product = (Product) o;

        if (product.getName().equals(this.mName) &&
                (product.getId() == this.mId) &&
                (product.getRatingCount() == this.mRatingCount) &&
                (product.getDescription().equals(this.mDescription)) &&
                (product.getPrice().equals(this.mPrice)) &&
                (product.getAverageRate().equals(this.mAverageRate)) &&
                (product.getStockStatus().equals(this.mStockStatus)) &&
                (product.isFeatured() == this.mIsFeatured) &&
                (product.getImageUrl().equals(this.mImageUrl)) &&
                (product.getCategory().equals(this.mCategory)) &&
                (product.getTags().equals(this.mTags))) {

            return 0;
        } else {
            return 1;
        }
    }
}