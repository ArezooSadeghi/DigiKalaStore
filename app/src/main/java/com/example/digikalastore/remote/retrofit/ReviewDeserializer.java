package com.example.digikalastore.remote.retrofit;

import android.text.Html;

import com.example.digikalastore.model.Review;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ReviewDeserializer implements JsonDeserializer<Review> {
    @Override
    public Review deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject reviewObject = json.getAsJsonObject();
        int productId = reviewObject.get("product_id").getAsInt();
        String reviewContent = Html.fromHtml(reviewObject.get("review").getAsString()).toString();
        String reviewerName = reviewObject.get("reviewer").getAsString();
        String reviewerEmail = reviewObject.get("reviewer_email").getAsString();
        int reviewRating = reviewObject.get("rating").getAsInt();
        return new Review(productId, reviewContent, reviewerName, reviewerEmail, reviewRating);
    }
}
