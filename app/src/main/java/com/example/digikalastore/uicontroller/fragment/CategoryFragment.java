package com.example.digikalastore.uicontroller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.CategoryProductAdapter;
import com.example.digikalastore.databinding.FragmentCategoryBinding;
import com.example.digikalastore.viewmodel.ProductViewModel;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding mBinding;
    private ProductViewModel mViewModel;

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_category,
                container,
                false);

        initViews();
        setupAdapter();

        return mBinding.getRoot();
    }

    public void initViews() {
        mBinding.recyclerViewCategoryList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setupAdapter() {
        CategoryProductAdapter adapter = new CategoryProductAdapter(
                getContext(),
                mViewModel.getCategories(),
                mViewModel.getProducts(),
                2);
        mBinding.recyclerViewCategoryList.setAdapter(adapter);
    }
}