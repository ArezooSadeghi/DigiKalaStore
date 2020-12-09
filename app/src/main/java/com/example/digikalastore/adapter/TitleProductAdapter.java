package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.TitleRecyclerViewItemBinding;
import com.example.digikalastore.model.Product;

import java.util.List;

public class TitleProductAdapter extends RecyclerView.Adapter<TitleProductAdapter.TitleProductHolder> {

    public static final int SPAN_COUNT = 3;
    private Context mContext;
    private List<String> mTitles;
    private List<Product> mProducts;

    public TitleProductAdapter(Context context, List<String> titles) {
        mContext = context;
        mTitles = titles;
    }

    public TitleProductAdapter(Context context, List<String> titles, List<Product> products) {
        mContext = context;
        mTitles = titles;
        mProducts = products;
    }

    public List<String> getTitles() {
        return mTitles;
    }

    public void setTitles(List<String> titles) {
        mTitles = titles;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    @NonNull
    @Override
    public TitleProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TitleRecyclerViewItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.title_recycler_view_item,
                parent,
                false);
        return new TitleProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TitleProductHolder holder, int position) {
        holder.bindTitle(mTitles.get(position));
        setProductTitleRecyclerView(holder.mBinding.recyclerViewProductTitle, mProducts);
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class TitleProductHolder extends RecyclerView.ViewHolder {

        private TitleRecyclerViewItemBinding mBinding;

        public TitleProductHolder(TitleRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindTitle(String title) {
            mBinding.txtTitle.setText(title);
        }
    }

    public void setProductTitleRecyclerView(RecyclerView recyclerView, List<Product> products) {
        recyclerView.setLayoutManager(new GridLayoutManager(
                mContext,
                SPAN_COUNT,
                GridLayoutManager.HORIZONTAL,
                false));

        ProductAdapter adapter = new ProductAdapter(mContext, products);
        recyclerView.setAdapter(adapter);
    }
}
