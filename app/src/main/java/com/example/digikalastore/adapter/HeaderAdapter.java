package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.HeaderAdapterItemBinding;
import com.example.digikalastore.model.Product;

import java.util.HashMap;
import java.util.List;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.HeaderHolder> {

    private Context mContext;
    private HashMap<String, List<Product>> mItems;
    private SetItemClickedListener mListener;
    private List<String> mHeaders;

    public HeaderAdapter(Context context, HashMap<String, List<Product>> items, SetItemClickedListener listener) {
        mContext = context;
        mItems = items;
        mListener = listener;
    }

    public void setHeaders(List<String> headers) {
        mHeaders = headers;
    }

    @NonNull
    @Override
    public HeaderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HeaderHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.header_adapter_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull HeaderHolder holder, int position) {
        holder.bindHeader(mHeaders.get(position));
        setProductRecyclerView(holder.mBinding.recyclerViewHeader, mItems.get(mHeaders.get(position)));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        private HeaderAdapterItemBinding mBinding;

        public HeaderHolder(HeaderAdapterItemBinding binding) {
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

    public interface SetItemClickedListener {
        void itemClicked(int productId);
    }
}
