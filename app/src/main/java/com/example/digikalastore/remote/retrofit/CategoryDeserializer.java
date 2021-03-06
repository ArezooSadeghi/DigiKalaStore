package com.example.digikalastore.remote.retrofit;

import com.example.digikalastore.model.Category;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CategoryDeserializer implements JsonDeserializer<List<Category>> {

    @Override
    public List<Category> deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

        List<Category> categories = new ArrayList<>();
        JsonArray bodyArray = json.getAsJsonArray();

        for (int i = 0; i < bodyArray.size(); i++) {
            JsonObject categoryObject = bodyArray.get(i).getAsJsonObject();
            int categoryId = categoryObject.get("id").getAsInt();
            String categoryName = categoryObject.get("name").getAsString();
            Category category = new Category(categoryName, categoryId);
            categories.add(category);
        }

        return categories;
    }
}
