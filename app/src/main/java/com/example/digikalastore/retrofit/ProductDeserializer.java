package com.example.digikalastore.retrofit;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDeserializer implements JsonDeserializer<List<Product>> {

    @Override
    public List<Product> deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        List<Product> productList = new ArrayList<>();
        JsonArray bodyArray = json.getAsJsonArray();

        for (int i = 0; i < bodyArray.size(); i++) {
            JsonObject productObject = bodyArray.get(i).getAsJsonObject();

            String id = productObject.get("id").getAsString();
            String name = productObject.get("name").getAsString();
            String price = productObject.get("price").getAsString();
            String description = Html.fromHtml(
                    productObject.get("description").getAsString()).toString();

            JsonArray categories = productObject.get("categories").getAsJsonArray();
            Map<String, String> category = new HashMap<>();

            for (int j = 0; j < categories.size(); j++) {
                JsonObject productCategory = categories.get(j).getAsJsonObject();
                category.put(
                        productCategory.get("id").getAsString(),
                        productCategory.get("name").getAsString());
            }

            JsonArray images = productObject.get("images").getAsJsonArray();
            List<String> imageUrl = new ArrayList<>();

            for (int j = 0; j < images.size(); j++) {
                JsonObject productImage = images.get(j).getAsJsonObject();
                imageUrl.add(productImage.get("src").getAsString());
            }

            Product product = new Product(name, id, imageUrl, category, description, price);
            productList.add(product);
        }
        return productList;
    }
}
