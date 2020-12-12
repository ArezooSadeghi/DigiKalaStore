package com.example.digikalastore.retrofit;

import android.text.Html;
import android.util.Log;

import com.example.digikalastore.model.Category;
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

public class ProductDeserializer implements JsonDeserializer<List<Product>> {

    @Override
    public List<Product> deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        List<Product> products = new ArrayList<>();

        JsonArray bodyArray = json.getAsJsonArray();
        for (int i = 0; i < bodyArray.size(); i++) {
            JsonObject productObject = bodyArray.get(i).getAsJsonObject();

            String productId = productObject.get("id").getAsString();
            String productName = productObject.get("name").getAsString();
            String productPrice = productObject.get("price").getAsString();
            String productDescription = Html.fromHtml(
                    productObject.get("description").getAsString()).toString();

            JsonArray images = productObject.get("images").getAsJsonArray();
            List<String> productImageUrls = new ArrayList<>();

            for (int j = 0; j < images.size(); j++) {
                JsonObject imageObject = images.get(j).getAsJsonObject();
                productImageUrls.add(imageObject.get("src").getAsString());
            }

            Product product = new Product(
                    productName,
                    productId,
                    productImageUrls,
                    productDescription,
                    productPrice);

            JsonArray categories = productObject.get("categories").getAsJsonArray();
            List<Category> productCategories = new ArrayList<>();

            for (int j = 0; j < categories.size(); j++) {
                List<Product> categoryProduct = new ArrayList<>();
                JsonObject categoryObject = categories.get(j).getAsJsonObject();
                String categoryId = categoryObject.get("id").getAsString();
                String categoryName = categoryObject.get("name").getAsString();
                Category category = new Category(categoryName, categoryId);
                productCategories.add(category);
                categoryProduct.add(product);
            }

            product.setCategory(productCategories);
            products.add(product);
        }
        return products;
    }
}
