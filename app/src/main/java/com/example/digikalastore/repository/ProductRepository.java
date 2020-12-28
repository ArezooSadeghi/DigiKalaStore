package com.example.digikalastore.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.digikalastore.R;
import com.example.digikalastore.model.Category;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.model.Review;
import com.example.digikalastore.remote.retrofit.CategoryDeserializer;
import com.example.digikalastore.remote.retrofit.CategoryService;
import com.example.digikalastore.remote.retrofit.ProductDeserializer;
import com.example.digikalastore.remote.retrofit.ProductListDeserializer;
import com.example.digikalastore.remote.retrofit.ProductService;
import com.example.digikalastore.remote.retrofit.RetrofitInstance;
import com.example.digikalastore.remote.retrofit.ReviewDeserializer;
import com.example.digikalastore.remote.retrofit.ReviewListDeserializer;
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

    private static ProductRepository sInstance;
    private Context mContext;
    private List<Product> mProductList;
    private List<String> mProductPrice;
    private ProductService mReviewListService, mReviewService;

    private MutableLiveData<List<Product>> mProductByOrderLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSearchingProductLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Category>> mCategoryLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mTotalPageLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<String>> mProductPriceLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Review>> mReviewLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductsLiveData = new MutableLiveData<>();





    private MutableLiveData<List<Product>> mProductsByCategoryLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> mRetrieveProductLiveData = new MutableLiveData<>();
    private List<Product> mProducts;
    private ProductService mProductListService, mProductService;
    private CategoryService mCategoryService;
    private ProductService mDigiKalaServiceReview;









    private HashMap<String, List<Product>> mItems = new HashMap<>();
    private MutableLiveData<HashMap<String, List<Product>>> mResponseLiveData = new MutableLiveData<>();


    private ProductRepository(Context context) {
        mProductListService = RetrofitInstance.getRetrofitInstance(
                new TypeToken<List<Product>>() {
                }.getType(),
                new ProductListDeserializer()).create(ProductService.class);

        mProductService = RetrofitInstance.getRetrofitInstance(
                new TypeToken<Product>() {
                }.getType(),
                new ProductDeserializer()).create(ProductService.class);

        mCategoryService = RetrofitInstance.getRetrofitInstance(
                new TypeToken<List<Category>>() {
                }.getType(),
                new CategoryDeserializer()).create(CategoryService.class);

        mReviewListService = RetrofitInstance.getRetrofitInstance(
                new TypeToken<List<Review>>() {}.getType(),
                new ReviewListDeserializer()).create(ProductService.class);

        mReviewService = RetrofitInstance.getRetrofitInstance(
                new TypeToken<Review>() {}.getType(),
                new ReviewDeserializer()).create(ProductService.class);

        mContext = context.getApplicationContext();

        mProductList = new ArrayList<>();
        mProductListLiveData.setValue(mProductList);


        mProductPrice = new ArrayList<>();
        mProductPriceLiveData.setValue(mProductPrice);
    }


    public static ProductRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ProductRepository(context);
        }
        return sInstance;
    }




    public MutableLiveData<Integer> getTotalPageLiveData() {
        return mTotalPageLiveData;
    }

    public MutableLiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
    }

    public MutableLiveData<List<Product>> getSearchingProductLiveData() {
        return mSearchingProductLiveData;
    }

    public MutableLiveData<List<Product>> getProductByOrderLiveData() {
        return mProductByOrderLiveData;
    }

    public MutableLiveData<List<String>> getProductPriceLiveData() {
        return mProductPriceLiveData;
    }

    public MutableLiveData<List<Product>> getProductListLiveData() {
        return mProductListLiveData;
    }

    public MutableLiveData<List<Review>> getReviewLiveData() {
        return mReviewLiveData;
    }

    public MutableLiveData<List<Product>> getProductsLiveData() {
        return mProductsLiveData;
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public List<String> getProductPrice() {
        return mProductPrice;
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

    public MutableLiveData<List<Product>> getProductsByCategoryLiveData() {
        return mProductsByCategoryLiveData;
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

    public Observable<List<Product>> getFeaturedProductsObservable() {
        return mProductListService.getAllProducts();
    }




    public void fetchCategories(int page) {
        Call<List<Category>> call = mCategoryService.getCategories(page);
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                mCategoryLiveData.setValue(response.body());
                mTotalPageLiveData.setValue(Integer.valueOf(response.headers().values("x-wp-totalpages").get(0)));
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }


    public void fetchSearchingProducts(String search) {
        Call<List<Product>> call = mProductListService.serarchProducts(search);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mSearchingProductLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }


    public void fetchProductsByOrder(String search, String orderby, String order) {
        Call<List<Product>> call = mProductListService.getProductsByOrder(search, orderby, order);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProductByOrderLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }


    public void fetchReviews(int product) {
        Call<List<Review>> call = mReviewListService.getReviews(product);
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                mReviewLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }


    public void sendReview(int productId, String review, String reviewer, String reviewerEmail, int rating) {
        Call<Review> call = mReviewService.sendReview(productId, review, reviewer, reviewerEmail, rating);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    Log.d("Arezoo", response.body() + "");
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });
    }


    public void fetchProducts() {
        Call<List<Product>> call = mProductListService.getPeoducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
               mProductsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public ArrayList<String> getChildItemList() {
        return new ArrayList<String>() {{
            add(mContext.getResources().getString(R.string.order_by_sale));
            add(mContext.getResources().getString(R.string.desc_order_by_price));
            add(mContext.getResources().getString(R.string.asc_order_by_price));
            add(mContext.getResources().getString(R.string.order_by_date));
        }};
    }
}
