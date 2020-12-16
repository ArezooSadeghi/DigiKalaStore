package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.TitleAdapterItemBinding;
import com.example.digikalastore.model.Product;

import java.util.List;

public class TitleAdapter extends RecyclerView.Adapter<TitleAdapter.TitleHolder> {

    private Context mContext;
    private List<String> mTitles;
    private List<Product> mProducts;
    private SetItemClickedListener mListener;

    public TitleAdapter(
            Context context,
            List<String> titles,
            List<Product> products,
            SetItemClickedListener listener) {

        mContext = context;
        mTitles = titles;
        mProducts = products;
        mListener = listener;
    }

    @NonNull
    @Override
    public TitleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TitleHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.title_adapter_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull TitleHolder holder, int position) {
        holder.bindTitle(mTitles.get(position));
        setProductRecyclerView(holder.mBinding.recyclerViewTitle, mProducts);
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class TitleHolder extends RecyclerView.ViewHolder {

        private TitleAdapterItemBinding mBinding;

        public TitleHolder(TitleAdapterItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindTitle(String title) {
            mBinding.txtTitle.setText(title);
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
