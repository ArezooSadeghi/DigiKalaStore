package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.HeaderAdapterFirstTypeItemBinding;
import com.example.digikalastore.databinding.HeaderAdapterSecondTypeItemBinding;
import com.example.digikalastore.model.Product;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_ONE = 1;
    public static final int VIEW_TYPE_TWO = 2;

    private Context mContext;
    private HashMap<String, List<Product>> mItems;
    private List<String> mHeaders;
    private List<Product> mProducts;
    private SetItemClickedListener mListener;

    public HeaderAdapter(Context context, HashMap<String, List<Product>> items, SetItemClickedListener listener) {
        mContext = context;
        mItems = items;
        mListener = listener;
    }

    public void setHeaders(List<String> headers) {
        mHeaders = headers;
    }

    public List<String> getHeaders() {
        return mHeaders;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 2) {
            return 2;
        }
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TWO) {
            return new HeaderHolderTypeTwo(DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.header_adapter_second_type_item, parent, false
            ));
        } else {

            return new HeaderHolderTypeOne(DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.header_adapter_first_type_item,
                    parent,
                    false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolderTypeOne) {
            ((HeaderHolderTypeOne) holder).bindHeader(mHeaders.get(position));
            setProductRecyclerView(((HeaderHolderTypeOne) holder).mBinding.recyclerViewHeader, mItems.get(mHeaders.get(position)));
        }
        if (holder instanceof HeaderHolderTypeTwo) {
            ((HeaderHolderTypeTwo) holder).bindHeader(mHeaders.get(position));
            List<String> imageUrls = new ArrayList<>();
            for (Product product : mProducts) {
                imageUrls.addAll(product.getImageUrl());
            }
            setSlider(((HeaderHolderTypeTwo) holder).mBinding.sliderViewFeaturedProduct, imageUrls);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class HeaderHolderTypeOne extends RecyclerView.ViewHolder {

        private HeaderAdapterFirstTypeItemBinding mBinding;

        public HeaderHolderTypeOne(HeaderAdapterFirstTypeItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindHeader(String header) {
            mBinding.setHeader(header);
        }
    }

    public class HeaderHolderTypeTwo extends RecyclerView.ViewHolder {

        private HeaderAdapterSecondTypeItemBinding mBinding;

        public HeaderHolderTypeTwo(HeaderAdapterSecondTypeItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindHeader(String header) {
            mBinding.setHeader(header);
        }
    }

    public void setProductRecyclerView(RecyclerView recyclerView, List<Product> products) {
        recyclerView.setLayoutManager(new LinearLayoutManager(
                mContext,
                LinearLayoutManager.HORIZONTAL,
                false));

        ProductAdapter adapter = new ProductAdapter(
                mContext,
                products,
                2,
                new ProductAdapter.ProductCategoryItemClickedCallbcak() {
                    @Override
                    public void productCategoryItemClicked(int productId) {
                        mListener.itemClicked(productId);
                    }
                });
        recyclerView.setAdapter(adapter);
    }

    public void setSlider(SliderView sliderView, List<String> imsgeUrls) {
        ImageSliderAdapter adapter = new ImageSliderAdapter(mContext, imsgeUrls);
        sliderView.setSliderAdapter(adapter);
    }

    public interface SetItemClickedListener {
        void itemClicked(int productId);
    }
}
