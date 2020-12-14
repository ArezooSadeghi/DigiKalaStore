package com.example.digikalastore.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.digikalastore.R;
import com.example.digikalastore.model.Category;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.retrofit.CategoryListDeserializer;
import com.example.digikalastore.retrofit.DigiKalaService;
import com.example.digikalastore.retrofit.ProductListDeserializer;
import com.example.digikalastore.retrofit.RetrofitInstance;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    public static final String TAG = "ProductRepository";

    private MutableLiveData<List<Product>> mProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSearchingProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Category>> mCategoryLiveData = new MutableLiveData<>();
    private List<String> mTitles;
    private List<Product> mProducts;
    private static ProductRepository sInstance;
    private DigiKalaService mDigiKalaServiceProduct;
    private DigiKalaService mDigiKalaServiceCategory;
    private Context mContext;

    private ProductRepository(Context context) {
        mDigiKalaServiceProduct = RetrofitInstance.getRetrofitInstance(
                new TypeToken<List<Product>>() {
                }.getType(),
                new ProductListDeserializer()).create(DigiKalaService.class);

        mDigiKalaServiceCategory = RetrofitInstance.getRetrofitInstance(
                new TypeToken<List<Category>>() {
                }.getType(),
                new CategoryListDeserializer()).create(DigiKalaService.class);

        mContext = context.getApplicationContext();
        mTitles = new ArrayList<String>() {{
            add(mContext.getResources().getString(R.string.best_product_title));
            add(mContext.getResources().getString(R.string.latest_product_title));
            add(mContext.getResources().getString(R.string.most_visited_product_title));
        }};
    }

    public static ProductRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ProductRepository(context);
        }
        return sInstance;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    public MutableLiveData<List<Product>> getProductsLiveData() {
        return mProductsLiveData;
    }

    public MutableLiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
    }

    public MutableLiveData<List<Product>> getSearchingProductsLiveData() {
        return mSearchingProductsLiveData;
    }

    public List<String> getTitles() {
        return mTitles;
    }

    public void setTitles(List<String> titles) {
        mTitles = titles;
    }

    public void fetchCategories() {
        Call<List<Category>> call = mDigiKalaServiceCategory.getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                mCategoryLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchSearchingProductsAsync(String query) {
        Call<List<Product>> call = mDigiKalaServiceProduct.serarchProduct(query);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mSearchingProductsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchProductAsync() {
        Call<List<Product>> call = mDigiKalaServiceProduct.getProductList();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProducts = response.body();
                mProductsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public Product getProduct(String productId) {
        for (Product product : mProducts) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getProductsByCategory(String categoryId) {
        List<Product> products = new ArrayList<>();
        for (Product product : mProducts) {
            for (Category category : product.getCategory()) {
                if (category.getId().equals(categoryId)) {
                    products.add(product);
                }
            }
        }
        return products;
    }

    public List<Category> getCategories() {
        Map<String, Category> hashMap = new HashMap<>();
        for (Product product : mProducts) {
            for (Category category : product.getCategory()) {
                hashMap.put(category.getId(), category);
            }
        }

        List<Category> categories = new ArrayList<>(hashMap.values());
        return categories;
    }
}
