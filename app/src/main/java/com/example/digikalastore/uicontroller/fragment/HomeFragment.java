package com.example.digikalastore.uicontroller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.CategoryProductAdapter;
import com.example.digikalastore.databinding.FragmentHomeBinding;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.uicontroller.activity.MainActivity;
import com.example.digikalastore.viewmodel.ProductViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private ProductViewModel mViewModel;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);
        mViewModel.fetchProductAsync();
        setObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home,
                container,
                false);

        initViews();

        return mBinding.getRoot();
    }

    private void setObserver() {
        mViewModel.getProductsLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });
    }

    private void initViews() {
        mBinding.recyclerViewTitle.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupAdapter(List<Product> products) {
        CategoryProductAdapter adapter = new CategoryProductAdapter(
                getContext(),
                mViewModel.getTitles(),
                products, new CategoryProductAdapter.CategoryItemClickedCallback() {
            @Override
            public void categoryItemClicked(String productId) {
                HomeFragmentDirections.ActionHomeFragmentToProductDetailFragment action =
                        HomeFragmentDirections.actionHomeFragmentToProductDetailFragment();
                action.setProductId(productId);
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
                /*Navigation.findNavController(getView()).navigate(action);*/
            }
        });
        mBinding.recyclerViewTitle.setAdapter(adapter);
    }
}