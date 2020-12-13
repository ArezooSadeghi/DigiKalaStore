package com.example.digikalastore.uicontroller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
        setHasOptionsMenu(true);

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

        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbarCategory);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);

        initViews();
        setupAdapter();

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_search:
                getActivity().onSearchRequested();
                return true;
            default:
                return false;
        }
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