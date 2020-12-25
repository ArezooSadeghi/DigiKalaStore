package com.example.digikalastore.view.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
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
    private String mQuery;
    private String mOrderBy;

    private static final int REQUEST_CODE_ACTIVITY_CONTAINER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_searchable);
        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        initToolBar();

        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.newIntent(SearchableActivity.this));
                finish();
            }
        });

        handleIntent();
        mBinding.setQuery(mQuery);

        setObserver();
        setListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ACTIVITY_CONTAINER && resultCode == Activity.RESULT_OK) {
            mOrderBy = data.getStringExtra(ActivityContainer.EXTRA_TEXT);
            fetchData(mOrderBy);
        }
    }


    private void initToolBar() {
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void handleIntent() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(mQuery);
        }
    }


    private void doMySearch(String query) {
        loadingVisibility();
        mViewModel.fetchSearchingProducts(query);
    }


    private void setObserver() {
        mViewModel.getSearchingProductLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                loadingVisibility();
                setupAdapter(products);
            }
        });

        mViewModel.getProductByOrderLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });
    }


    private void setListener() {
        mBinding.btnRelated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ActivityContainer.newIntent(SearchableActivity.this);
                startActivityForResult(intent, REQUEST_CODE_ACTIVITY_CONTAINER);
            }
        });
    }

    public void fetchData(String orderBy) {
        loadingVisibility();

        if (orderBy.equals(getResources().getString(R.string.desc_order_by_price))) {

            mViewModel.fetchProductsByOrder(mQuery, "price", "desc");

        } else if (orderBy.equals(getResources().getString(R.string.asc_order_by_price))) {

            mViewModel.fetchProductsByOrder(mQuery, "price", "asc");

        } else if (orderBy.equals(getResources().getString(R.string.order_by_sale))) {

            mViewModel.fetchProductsByOrder(mQuery, "popularity", "desc");

        } else if (orderBy.equals(getResources().getString(R.string.order_by_date))) {

            mViewModel.fetchProductsByOrder(mQuery, "date", "desc");

        }

    }


    private void setupAdapter(List<Product> products) {
        ResultSearchProductAdapter adapter = new ResultSearchProductAdapter(this, products);
        mBinding.listViewResultSearch.setAdapter(adapter);
    }


    private void loadingVisibility() {
        if (mBinding.getIsLoading() != null && mBinding.getIsLoading())
            mBinding.setIsLoading(false);
        else
            mBinding.setIsLoading(true);
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, SearchableActivity.class);
    }
}