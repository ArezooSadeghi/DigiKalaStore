package com.example.digikalastore.view.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.ExpandableListViewAdapter;
import com.example.digikalastore.adapter.ResultSearchProductAdapter;
import com.example.digikalastore.databinding.ActivitySearchableBinding;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchableActivity extends AppCompatActivity {

    private ProductViewModel mViewModel;
    private ActivitySearchableBinding mBinding;
    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_searchable);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.newIntent(SearchableActivity.this));
                finish();
            }
        });

        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        setObserver();

        handleIntent();
        mBinding.txtToolbarTitle.setText(mQuery);

        HashMap<String, ArrayList<String>> mChildList = new HashMap<>();
        ArrayList<String> related = new ArrayList<String>();
        related.add("پرفروش ترین ها");
        related.add("قیمت از زیاد به کم");
        related.add("قیمت از کم به زیاد");
        related.add("جدیدترین ها");

        mChildList.put("مرتب سازی", related);

        ArrayList<String> mParentList = new ArrayList<>(mChildList.keySet());
        mBinding.btnRelated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(SearchableActivity.this, mParentList, mChildList, new ExpandableListViewAdapter.SetItemClickListener() {
                    @Override
                    public void itemClicked(String text) {
                        /*mViewModel.fetchNewestProductsAsync(text, "2");*/
                    }
                });
                mBinding.expandable.setAdapter(adapter);
            }
        });

        mBinding.listViewResultSearch.setOnScrollListener(new AbsListView.OnScrollListener() {
            int mLastFirstVisibleItem = 0;
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (absListView.getId() == mBinding.listViewResultSearch.getId()) {
                    final int currentFirstVisibleItem = mBinding.listViewResultSearch.getFirstVisiblePosition();

                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                        getSupportActionBar().hide();
                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                        getSupportActionBar().show();
                    }
                    mLastFirstVisibleItem = currentFirstVisibleItem;
                }

            }
        });
    }

    private void setObserver() {
        mViewModel.getSearchingProductsLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
                Log.d("Arezoo", "comeOne");
            }
        });

       /* mViewModel.getNewestProductsLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
                Log.d("Arezoo", "comeTwo");
            }
        });*/
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(mQuery);
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