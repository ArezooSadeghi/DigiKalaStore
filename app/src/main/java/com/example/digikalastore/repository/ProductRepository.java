package com.example.digikalastore.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.digikalastore.model.Category;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.remote.retrofit.CategoryDeserializer;
import com.example.digikalastore.remote.retrofit.CategoryService;
import com.example.digikalastore.remote.retrofit.ProductDeserializer;
import com.example.digikalastore.remote.retrofit.ProductListDeserializer;
import com.example.digikalastore.remote.retrofit.ProductService;
import com.example.digikalastore.remote.retrofit.RetrofitInstance;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    public static final String TAG = ProductRepository.class.getSimpleName();

    private MutableLiveData<List<Product>> mProductsByOrder = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSearchingProductsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductsByCategoryLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Category>> mCategoryLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> mRetrieveProductLiveData = new MutableLiveData<>();
    private List<Product> mProducts;
    private static ProductRepository sInstance;
    private ProductService mProductListService, mProductService;
    private CategoryService mCategoryService;
    private ProductService mDigiKalaServiceReview;
    private Context mContext;


    private HashMap<String, List<Product>> mItems = new HashMap<>();
    private MutableLiveData<HashMap<String, List<Product>>> mResponseLiveData = new MutableLiveData<>();


    private ProductRepository(Context context) {
        mProductListService = RetrofitInstance.getRetrofitInstance(
                new TypeToken<List<Product>>() {}.getType(),
                new ProductListDeserializer()).create(ProductService.class);

        mProductService = RetrofitInstance.getRetrofitInstance(
                new TypeToken<Product>() {}.getType(),
                new ProductDeserializer()).create(ProductService.class);

        mCategoryService = RetrofitInstance.getRetrofitInstance(
                new TypeToken<List<Category>>() {}.getType(),
                new CategoryDeserializer()).create(CategoryService.class);

        mContext = context.getApplicationContext();
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

    public MutableLiveData<Product> getRetrieveProductLiveData() {
        return mRetrieveProductLiveData;
    }

    public MutableLiveData<List<Product>> getProductsLiveData() {
        return mProductsLiveData;
    }

    public MutableLiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
    }

    public MutableLiveData<List<Product>> getProductsByOrder() {
        return mProductsByOrder;
    }


    public MutableLiveData<List<Product>> getProductsByCategoryLiveData() {
        return mProductsByCategoryLiveData;
    }

    public MutableLiveData<List<Product>> getSearchingProductsLiveData() {
        return mSearchingProductsLiveData;
    }


    public MutableLiveData<HashMap<String, List<Product>>> getResponseLiveData() {
        return mResponseLiveData;
    }

    public void fetchProductsByCategory(int categoryId) {
        Call<List<Product>> call = mProductListService.getProductsByCategory(categoryId);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProductsByCategoryLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public void fetchCategories() {
        Call<List<Category>> call = mCategoryService.getCategories();
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
        Call<List<Product>> call = mProductListService.serarchProducts(query);
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
        Call<List<Product>> call = mProductListService.getProducts();
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

    public Product getProduct(int productId) {
        for (Product product : mProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();
        for (Product product : mProducts) {
            for (Category category : product.getCategory()) {
                if (category.getId() == categoryId) {
                    products.add(product);
                }
            }
        }
        return products;
    }

    public List<Category> getCategories() {
        Map<Integer, Category> hashMap = new HashMap<>();
        for (Product product : mProducts) {
            for (Category category : product.getCategory()) {
                hashMap.put(category.getId(), category);
            }
        }

        List<Category> categories = new ArrayList<>(hashMap.values());
        return categories;
    }

    public void fetchProductsByPrice(String search, String orderby, String order) {
        Call<List<Product>> call = mProductListService.getProductsByOrder(search, orderby, order);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProductsByOrder.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }


    public void retrieveProduct(int productId) {
        Call<Product> call = mProductService.retrieveProduct(productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                mRetrieveProductLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });

    }

    public Observable<List<Product>> getLatestProductsObservable() {
        return mProductListService.getLatestProducts();
    }

    public Observable<List<Product>> getBestProductsObservable() {
        return mProductListService.getBestProducts();
    }

    public Observable<List<Product>> getMostVisitedProductsObservable() {
        return mProductListService.getMostVisitedProducts();
    }
}
