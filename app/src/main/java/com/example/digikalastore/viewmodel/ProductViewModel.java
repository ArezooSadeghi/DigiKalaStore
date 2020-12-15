package com.example.digikalastore.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.digikalastore.model.Category;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private LiveData<List<Product>> mProductsLiveData;
    private LiveData<List<Product>> mSearchingProductsLiveData;
    private LiveData<List<Product>> mProductsByCategoryLiveData;
    private LiveData<List<Category>> mCategoryLiveData;
    private ProductRepository mRepository;
    private List<Product> mProducts;
    private List<String> mTitles;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mProductsLiveData = mRepository.getProductsLiveData();
        mSearchingProductsLiveData = mRepository.getSearchingProductsLiveData();
        mProductsByCategoryLiveData = mRepository.getProductsByCategoryLiveData();
        mCategoryLiveData = mRepository.getCategoryLiveData();
        mTitles = mRepository.getTitles();
    }

    public List<Product> getProducts() {
        return mRepository.getProducts();
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

    public Product getProduct(String productId) {
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
}
