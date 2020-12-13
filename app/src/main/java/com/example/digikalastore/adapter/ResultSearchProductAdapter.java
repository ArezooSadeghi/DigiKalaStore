package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.ResultSearchProductItemBinding;
import com.example.digikalastore.model.Product;

import java.util.List;

public class ResultSearchProductAdapter extends BaseAdapter {

    private Context mContext;
    private List<Product> mProducts;

    public ResultSearchProductAdapter(Context context, List<Product> products) {
        mContext = context;
        mProducts = products;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return mProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            ResultSearchProductItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.result_search_product_item,
                    viewGroup,
                    false);

            viewHolder = new ViewHolder(binding);
            viewHolder.mView = binding.getRoot();
            viewHolder.mView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.bindProduct((Product) getItem(i));
        return viewHolder.mView;
    }

    private class ViewHolder {

        private ResultSearchProductItemBinding mBinding;
        private View mView;

        public ViewHolder(ResultSearchProductItemBinding binding) {
            mBinding = binding;
            mView = binding.getRoot();
        }

        public void bindProduct(Product product) {
            mBinding.setProduct(product);
        }
    }
}
