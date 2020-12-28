package com.example.digikalastore.remote.retrofit;

import android.text.Html;

import com.example.digikalastore.model.Review;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReviewListDeserializer implements JsonDeserializer<List<Review>> {
    @Override
    public List<Review> deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        List<Review> reviews = new ArrayList<>();

        JsonArray bodyArray = json.getAsJsonArray();
        for (int i = 0; i < bodyArray.size(); i++) {
            JsonObject reviewObject = bodyArray.get(i).getAsJsonObject();
            int productId = reviewObject.get("product_id").getAsInt();
            String reviewContent = Html.fromHtml(reviewObject.get("review").getAsString()).toString();
            String reviewerName = reviewObject.get("reviewer").getAsString();
            String reviewerEmail = reviewObject.get("reviewer_email").getAsString();
            int reviewRating = reviewObject.get("rating").getAsInt();




            Review review = new Review(productId, reviewContent, reviewerName, reviewerEmail, reviewRating);
            reviews.add(review);
        }
        return reviews;
    }
}
