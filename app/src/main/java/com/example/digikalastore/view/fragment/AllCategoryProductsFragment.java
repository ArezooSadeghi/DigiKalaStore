package com.example.digikalastore.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.ProductAdapter;
import com.example.digikalastore.databinding.FragmentAllCategoryProductsBinding;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.viewmodel.ProductViewModel;

import java.util.List;

public class AllCategoryProductsFragment extends Fragment {


    private LinearLayoutManager mLayoutManager;

    private FragmentAllCategoryProductsBinding mBinding;
    private ProductViewModel mViewModel;
    private int mCategoryId;

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

        initViews();

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AllCategoryProductsFragmentArgs args = AllCategoryProductsFragmentArgs
                .fromBundle(getArguments());
        mCategoryId = args.getCategoryId();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.fetchProductsByCategory(mCategoryId);
        setObserver();
    }

    private void setObserver() {
        mViewModel.getProductsByCategoryLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });
    }

    private void initViews() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mBinding.recyclerViewAllCategoryProducts.setLayoutManager(mLayoutManager);
        /*mBinding.recyclerViewAllCategoryProducts
                .setLayoutManager(new LinearLayoutManager(getContext()));*/
    }

    private void setupAdapter(List<Product> products) {
        ProductAdapter adapter = new ProductAdapter(getContext(), products, 3, new ProductAdapter.ProductCategoryItemClickedCallbcak() {
            @Override
            public void productCategoryItemClicked(int productId) {
                AllCategoryProductsFragmentDirections.ActionAllCategoryProductsFragmentToProductDetailFragment action =
                        AllCategoryProductsFragmentDirections.actionAllCategoryProductsFragmentToProductDetailFragment();
                action.setProductId(productId);
                NavHostFragment.findNavController(AllCategoryProductsFragment.this).navigate(action);
            }
        });
        mBinding.recyclerViewAllCategoryProducts.setAdapter(adapter);
    }
}