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


    private LiveData<List<Category>> mCategoryLiveData;
    private LiveData<Integer> mTotalPageLiveData;



    private LiveData<List<Product>> mProductsLiveData;
    private LiveData<List<Product>> mProductsByOrder;
    private LiveData<List<Product>> mSearchingProductsLiveData;
    private LiveData<List<Product>> mProductsByCategoryLiveData;

    private LiveData<Product> mRetrieveProductLiveData;
    private ProductRepository mRepository;
    private List<Product> mProducts;

    private List<Product> mProductList;
    private LiveData<List<Product>> mProductListLiveData;

    private List<String> mProductsPrice;
    private LiveData<List<String>> mProductsPriceLiveData;




    private LiveData<HashMap<String, List<Product>>> mResponseLiveData;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());

        mCategoryLiveData = mRepository.getCategoryLiveData();
        mTotalPageLiveData = mRepository.getTotalPageLiveData();


        mProductsLiveData = mRepository.getProductsLiveData();
        mProductsByOrder = mRepository.getProductsByOrder();
        mSearchingProductsLiveData = mRepository.getSearchingProductsLiveData();
        mProductsByCategoryLiveData = mRepository.getProductsByCategoryLiveData();

        mRetrieveProductLiveData = mRepository.getRetrieveProductLiveData();

        mProductList = mRepository.getProductList();
        mProductListLiveData = mRepository.getProductListLiveData();

        mProductsPrice = mRepository.getProductsPrice();
        mProductsPriceLiveData = mRepository.getProductsPriceLiveData();


        mResponseLiveData = mRepository.getResponseLiveData();
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public LiveData<List<Product>> getProductListLiveData() {
        return mProductListLiveData;
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

    public List<String> getProductsPrice() {
        return mProductsPrice;
    }

    public LiveData<List<String>> getProductsPriceLiveData() {
        return mProductsPriceLiveData;
    }



    public LiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
    }

    public LiveData<Integer> getTotalPageLiveData() {
        return mTotalPageLiveData;
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

    public Observable<List<Product>> getFeaturedProductsObservable() {
        return mRepository.getFeaturedProductsObservable();
    }


    public void fetchCategories(int page) {
        mRepository.fetchCategories(page);
    }
}
