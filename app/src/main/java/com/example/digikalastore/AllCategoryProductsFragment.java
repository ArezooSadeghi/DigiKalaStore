package com.example.digikalastore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.digikalastore.databinding.FragmentAllCategoryProductsBinding;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.uicontroller.fragment.ProductDetailFragmentArgs;
import com.example.digikalastore.viewmodel.ProductViewModel;

public class AllCategoryProductsFragment extends Fragment {

    private FragmentAllCategoryProductsBinding mBinding;
    private ProductViewModel mViewModel;
    private Product mProduct;

    public AllCategoryProductsFragment() {
    }

    public static AllCategoryProductsFragment newInstance() {
        AllCategoryProductsFragment fragment = new AllCategoryProductsFragment();
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
                R.layout.fragment_all_category_products,
                container,
                false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AllCategoryProductsFragmentArgs args = AllCategoryProductsFragmentArgs.fromBundle(getArguments());
        String productId = args.getProductId();
        mProduct = mViewModel.getProduct(productId);
        initViews();
    }

    private void initViews() {
        mBinding.setProduct(mProduct);
    }
}