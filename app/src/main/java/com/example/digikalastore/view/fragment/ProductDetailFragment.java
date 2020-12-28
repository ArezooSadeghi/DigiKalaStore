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

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.ImageSliderAdapter;
import com.example.digikalastore.databinding.FragmentProductDetailBinding;
import com.example.digikalastore.event.Event;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.viewmodel.ProductViewModel;

import org.greenrobot.eventbus.EventBus;

public class ProductDetailFragment extends Fragment {

    private FragmentProductDetailBinding mBinding;
    private ProductViewModel mViewModel;
    private Product mProduct;


    public static ProductDetailFragment newInstance() {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_product_detail,
                container,
                false);

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProductDetailFragmentArgs args = ProductDetailFragmentArgs.fromBundle(getArguments());
        int productId = args.getProductId();
        mViewModel.retrieveProduct(productId);
        setObserver();
        setListener(productId);
    }


    private void setObserver() {
        mViewModel.getRetrieveProductLiveData().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                mProduct = product;
                initViews(product);
            }
        });
    }


    private void setListener(int productId) {
        mBinding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new Event());
                mBinding.btnAddToCart.setVisibility(View.GONE);
                mBinding.showDetailOfSale.setVisibility(View.VISIBLE);
                mViewModel.getProductList().add(mProduct);
                mViewModel.getProductPrice().add(mProduct.getPrice());
            }
        });

        mBinding.btnSeeComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDetailFragmentDirections.ActionProductDetailFragmentToCommentPageFragment action =
                        ProductDetailFragmentDirections.actionProductDetailFragmentToCommentPageFragment();
                action.setProductId(productId);
                NavHostFragment.findNavController(ProductDetailFragment.this).navigate(action);
            }
        });
    }


    private void initViews(Product product) {
        mBinding.setProduct(product);
        ImageSliderAdapter adapter = new ImageSliderAdapter(getContext(), product.getImageUrl());
        mBinding.imgProductSlider.setSliderAdapter(adapter);
    }
}