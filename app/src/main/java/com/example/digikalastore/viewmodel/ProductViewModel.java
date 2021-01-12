package com.example.digikalastore.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.digikalastore.database.UserDatabase;
import com.example.digikalastore.model.Category;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.model.Review;
import com.example.digikalastore.model.customer.User;
import com.example.digikalastore.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    private UserDatabase mDatabase;
    private List<Product> mProductList;
    private List<String> mProductPrice;

    private LiveData<List<Product>> mSearchingProductLiveData;
    private LiveData<List<Review>> mReviewLiveData;
    private LiveData<List<String>> mProductPriceLiveData;
    private LiveData<List<Product>> mProductListLiveData;
    private LiveData<List<Category>> mCategoryLiveData;
    private LiveData<List<Product>> mProductsLiveData;
    private LiveData<Integer> mTotalPageLiveData;
    private LiveData<Review> mReviewTestLiveData;
    private MutableLiveData<List<String>> mUserAddressLiveData = new MutableLiveData<>();


    private LiveData<List<Product>> mProductByOrderLiveData;
    private LiveData<List<Product>> mProductsByCategoryLiveData;

    private LiveData<Product> mRetrieveProductLiveData;

    private List<Product> mProducts;


    private LiveData<HashMap<String, List<Product>>> mResponseLiveData;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mDatabase = UserDatabase.getInstance(getApplication());

        mSearchingProductLiveData = mRepository.getSearchingProductLiveData();
        mProductByOrderLiveData = mRepository.getProductByOrderLiveData();
        mProductPriceLiveData = mRepository.getProductPriceLiveData();
        mProductListLiveData = mRepository.getProductListLiveData();
        mTotalPageLiveData = mRepository.getTotalPageLiveData();
        mCategoryLiveData = mRepository.getCategoryLiveData();
        mProductsLiveData = mRepository.getProductsLiveData();
        mReviewLiveData = mRepository.getReviewLiveData();
        mReviewTestLiveData = mRepository.getReviewTestLiveData();

        mProductList = mRepository.getProductList();
        mProductPrice = mRepository.getProductPrice();


        mProductsByCategoryLiveData = mRepository.getProductsByCategoryLiveData();

        mRetrieveProductLiveData = mRepository.getRetrieveProductLiveData();


        mResponseLiveData = mRepository.getResponseLiveData();
    }


    public LiveData<Product> getRetrieveProductLiveData() {
        return mRetrieveProductLiveData;
    }

    public List<Product> getProducts() {
        return mRepository.getProducts();
    }

    public LiveData<List<Product>> getProductsByCategoryLiveData() {
        return mProductsByCategoryLiveData;
    }

    public LiveData<HashMap<String, List<Product>>> getResponseLiveData() {
        return mResponseLiveData;
    }


    public LiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
    }

    public LiveData<Integer> getTotalPageLiveData() {
        return mTotalPageLiveData;
    }

    public LiveData<List<Product>> getProductByOrderLiveData() {
        return mProductByOrderLiveData;
    }

    public LiveData<List<Review>> getReviewLiveData() {
        return mReviewLiveData;
    }

    public LiveData<List<Product>> getSearchingProductLiveData() {
        return mSearchingProductLiveData;
    }

    public LiveData<List<Product>> getProductListLiveData() {
        return mProductListLiveData;
    }

    public LiveData<List<String>> getProductPriceLiveData() {
        return mProductPriceLiveData;
    }

    public LiveData<List<Product>> getProductsLiveData() {
        return mProductsLiveData;
    }

    public LiveData<Review> getReviewTestLiveData() {
        return mReviewTestLiveData;
    }

    public MutableLiveData<List<String>> getUserAddressLiveData() {
        return mUserAddressLiveData;
    }

    public List<String> getProductPrice() {
        return mProductPrice;
    }

    public List<Product> getProductList() {
        return mProductList;
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


    public void fetchProductsByCategory(int categoryId) {
        mRepository.fetchProductsByCategory(categoryId);
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


    public void fetchProducts() {
        mRepository.fetchProducts();
    }

    public void fetchCategories(int page) {
        mRepository.fetchCategories(page);
    }

    public void fetchSearchingProducts(String search) {
        mRepository.fetchSearchingProducts(search);
    }

    public ArrayList<String> getChildItemList() {
        return mRepository.getChildItemList();
    }

    public void fetchProductsByOrder(String search, String orderby, String order) {
        mRepository.fetchProductsByOrder(search, orderby, order);
    }

    public void fetchReviews(int product) {
        mRepository.fetchReviews(product);
    }

    public void sendReview(int productId, String review, String reviewer, String reviewerEmail, int rating) {
        mRepository.sendReview(productId, review, reviewer, reviewerEmail, rating);
    }

    public void deleteReview(int id) {
        mRepository.deleteReview(id);
    }

    public void insertUser(User user) {
        mDatabase.getUserDao().insert(user);
    }

    public User getUser(String userEmail) {
        User user = mDatabase.getUserDao().getUser(userEmail);
        mUserAddressLiveData.setValue(user.getAddresses());
        return user;
    }

    public List<User> getUsers() {
        return mDatabase.getUserDao().getUsers();
    }

}
