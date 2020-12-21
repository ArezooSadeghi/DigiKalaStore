package com.example.digikalastore.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {

    private String mName;
    private int mId;
    private int mRatingCount;
    private List<String> mImageUrl;
    private String mDescription;
    private String mPrice;
    private List<Category> mCategory;
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
}
