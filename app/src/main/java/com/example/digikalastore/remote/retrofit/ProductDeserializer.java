package com.example.digikalastore.remote.retrofit;

import android.text.Html;

import com.example.digikalastore.model.Product;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductDeserializer implements JsonDeserializer<Product> {
    @Override
    public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject productObject = json.getAsJsonObject();

        JsonArray images = productObject.get("images").getAsJsonArray();
        List<String> productImageUrls = new ArrayList<>();
        for (int j = 0; j < images.size(); j++) {
            JsonObject imageObject = images.get(j).getAsJsonObject();
            productImageUrls.add(imageObject.get("src").getAsString());
        }

        String productName = productObject.get("name").getAsString();
        int productId = productObject.get("id").getAsInt();
        String productDescription = Html.fromHtml(
                productObject.get("description").getAsString()).toString();
        String productPrice = productObject.get("price").getAsString();
        String productAverageRating = productObject.get("average_rating").getAsString();

        return new Product(productName, productId, productImageUrls, productDescription, productPrice, productAverageRating);
    }
}
