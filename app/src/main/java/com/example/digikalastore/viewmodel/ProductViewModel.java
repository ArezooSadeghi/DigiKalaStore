package com.example.digikalastore.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.digikalastore.model.Product;
import com.example.digikalastore.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private LiveData<List<Product>> mProductsLiveData;
    private List<String> mTitles;
    private ProductRepository mRepository;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mProductsLiveData = mRepository.getProductsLiveData();
        mTitles = mRepository.getTitles();
    }

    public LiveData<List<Product>> getProductsLiveData() {
        return mProductsLiveData;
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
}
