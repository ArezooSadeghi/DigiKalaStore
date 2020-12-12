package com.example.digikalastore.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {

    private String mName;
    private String mId;
    private List<String> mImageUrl;
    private String mDescription;
    private String mPrice;
    private List<Category> mCategory;

    public Product() {
    }

    public Product(String name, String id, List<String> imageUrl, String description, String price) {
        mName = name;
        mId = id;
        mImageUrl = imageUrl;
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

    @BindingAdapter("productImage")
    public static void loadImage(ImageView view, String url) {
        Picasso.get().load(url).into(view);
    }
}
