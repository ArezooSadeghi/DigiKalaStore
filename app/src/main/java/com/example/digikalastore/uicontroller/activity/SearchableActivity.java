package com.example.digikalastore.uicontroller.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.ResultSearchProductAdapter;
import com.example.digikalastore.databinding.ActivitySearchableBinding;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.viewmodel.ProductViewModel;

import java.util.List;

public class SearchableActivity extends AppCompatActivity {

    private ProductViewModel mViewModel;
    private ActivitySearchableBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_searchable);

        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        setObserver();

        handleIntent();
    }

    private void setObserver() {
        mViewModel.getSearchingProductsLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        mViewModel.fetchSearchingProductsAsync(query);
    }

    private void setupAdapter(List<Product> products) {
        ResultSearchProductAdapter adapter = new ResultSearchProductAdapter(this, products);
        mBinding.listViewResultSearch.setAdapter(adapter);
    }
}