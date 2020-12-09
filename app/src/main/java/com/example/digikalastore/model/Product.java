package com.example.digikalastore.model;

import java.util.List;
import java.util.Map;

public class Product {

    private String mName;
    private String mId;
    private List<String> mImageUrl;
    private Map<String, String> mCategory;
    private String mDescription;
    private String mPrice;

    public Product() {
    }

    public Product(
            String name,
            String id,
            List<String> imageUrl,
            Map<String, String> category,
            String description,
            String price) {

        mName = name;
        mId = id;
        mImageUrl = imageUrl;
        mCategory = category;
        mDescription = description;
        mPrice = price;
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

    public List<String> getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        mImageUrl = imageUrl;
    }

    public Map<String, String> getCategory() {
        return mCategory;
    }

    public void setCategory(Map<String, String> category) {
        mCategory = category;
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
}