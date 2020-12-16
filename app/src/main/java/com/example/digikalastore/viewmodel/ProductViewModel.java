package com.example.digikalastore.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.digikalastore.model.Category;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.model.Review;
import com.example.digikalastore.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private LiveData<List<Product>> mProductsLiveData;
    private LiveData<List<Product>> mProductsByOrder;
    private LiveData<List<Product>> mSearchingProductsLiveData;
    private LiveData<List<Product>> mProductsByCategoryLiveData;
    private LiveData<List<Review>> mReviews;
    private LiveData<List<Category>> mCategoryLiveData;
    private ProductRepository mRepository;
    private List<Product> mProducts;
    private List<String> mTitles;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mProductsLiveData = mRepository.getProductsLiveData();
        mProductsByOrder = mRepository.getProductsByOrder();
        mSearchingProductsLiveData = mRepository.getSearchingProductsLiveData();
        mProductsByCategoryLiveData = mRepository.getProductsByCategoryLiveData();
        mReviews = mRepository.getReviews();
        mCategoryLiveData = mRepository.getCategoryLiveData();
        mTitles = mRepository.getTitles();
    }

    public LiveData<List<Product>> getProductsByOrder() {
        return mProductsByOrder;
    }

    public List<Product> getProducts() {
        return mRepository.getProducts();
    }

    public LiveData<List<Review>> getReviews() {
        return mReviews;
    }

    public LiveData<List<Product>> getProductsLiveData() {
        return mProductsLiveData;
    }

    public LiveData<List<Product>> getProductsByCategoryLiveData() {
        return mProductsByCategoryLiveData;
    }

    public LiveData<List<Product>> getSearchingProductsLiveData() {
        return mSearchingProductsLiveData;
    }

    public LiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
    }

    public List<String> getTitles() {
        return mTitles;
    }

    public void setTitles(List<String> titles) {
        mTitles = titles;
    }

    public void fetchProductAsync() {
        mRepository.fetchProductAsync();
    }

    public Product getProduct(int productId) {
        return mRepository.getProduct(productId);
    }

    public List<Category> getCategories() {
        return mRepository.getCategories();
    }

    public List<Product> getProductsByCategory(String categoryId) {
        return mRepository.getProductsByCategory(categoryId);
    }

    public void fetchSearchingProductsAsync(String query) {
        mRepository.fetchSearchingProductsAsync(query);
    }

    public void fetchCategories() {
        mRepository.fetchCategories();
    }

    public void fetchProductsByCategory(String id) {
        mRepository.fetchProductsByCategory(id);
    }

    public void fetchReviews(int[] productId) {
        mRepository.fetchReviews(productId);
    }

    public List<Product> getBestProducts(List<Product> products) {
        List<Product> bestProducts = new ArrayList<>();
        for (Product product:products) {
            if (Float.valueOf(product.getAverageRate()) > 0.0) {
                bestProducts.add(product);
            }
        }
        return bestProducts;
    }

    public int[] getProductsId(List<Product> products) {
        int[] productId = new int[products.size()];
        for (int i = 0; i < productId.length; i++) {
            productId[i] = products.get(i).getId();
        }
        return productId;
    }

    public void fetchProductsByOrder(String search, String orderby, String order) {
        mRepository.fetchProductsByPrice(search, orderby, order);
    }


}
