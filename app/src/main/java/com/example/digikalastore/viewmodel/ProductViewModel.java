package com.example.digikalastore.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.digikalastore.model.Category;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

public class ProductViewModel extends AndroidViewModel {

    private LiveData<List<Product>> mProductsLiveData;
    private LiveData<List<Product>> mProductsByOrder;
    private LiveData<List<Product>> mSearchingProductsLiveData;
    private LiveData<List<Product>> mProductsByCategoryLiveData;
    private LiveData<List<Category>> mCategoryLiveData;
    private LiveData<Product> mRetrieveProductLiveData;
    private ProductRepository mRepository;
    private List<Product> mProducts;


    private LiveData<HashMap<String, List<Product>>> mResponseLiveData;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mProductsLiveData = mRepository.getProductsLiveData();
        mProductsByOrder = mRepository.getProductsByOrder();
        mSearchingProductsLiveData = mRepository.getSearchingProductsLiveData();
        mProductsByCategoryLiveData = mRepository.getProductsByCategoryLiveData();
        mCategoryLiveData = mRepository.getCategoryLiveData();
        mRetrieveProductLiveData = mRepository.getRetrieveProductLiveData();


        mResponseLiveData = mRepository.getResponseLiveData();
    }

    public LiveData<Product> getRetrieveProductLiveData() {
        return mRetrieveProductLiveData;
    }

    public LiveData<List<Product>> getProductsByOrder() {
        return mProductsByOrder;
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


    public LiveData<HashMap<String, List<Product>>> getResponseLiveData() {
        return mResponseLiveData;
    }


    public LiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
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

    public List<Product> getProductsByCategory(int categoryId) {
        return mRepository.getProductsByCategory(categoryId);
    }

    public void fetchSearchingProductsAsync(String query) {
        mRepository.fetchSearchingProductsAsync(query);
    }

    public void fetchCategories() {
        mRepository.fetchCategories();
    }

    public void fetchProductsByCategory(int categoryId) {
        mRepository.fetchProductsByCategory(categoryId);
    }


    public void fetchProductsByOrder(String search, String orderby, String order) {
        mRepository.fetchProductsByPrice(search, orderby, order);
    }

    public void retrieveProduct(int productId) {
        mRepository.retrieveProduct(productId);
    }

    public Observable<List<Product>> getLatestProductsObservable() {
        return mRepository.getLatestProductsObservable();
    }

    public Observable<List<Product>> getBestProductsObservable() {
        return mRepository.getBestProductsObservable();
    }

    public Observable<List<Product>> getMostVisitedProductsObservable() {
        return mRepository.getMostVisitedProductsObservable();
    }
}
