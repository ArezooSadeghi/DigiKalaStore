package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.ProductCategoryRecyclerViewItemBinding;
import com.example.digikalastore.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Context mContext;
    private List<Product> mProducts;
    private ProductCategoryItemClickedCallbcak mCallbcak;

    public ProductAdapter(Context context, List<Product> products, ProductCategoryItemClickedCallbcak callbcak) {
        mContext = context;
        mProducts = products;
        mCallbcak = callbcak;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductCategoryRecyclerViewItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.product_category_recycler_view_item,
                parent,
                false);
        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.bindProduct(mProducts.get(position));
        holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbcak.productCategoryItemClicked(mProducts.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        private ProductCategoryRecyclerViewItemBinding mBinding;

        public ProductHolder(ProductCategoryRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProduct(Product product) {
            mBinding.setProduct(product);
        }
    }

    public interface ProductCategoryItemClickedCallbcak {
        void productCategoryItemClicked(String productId);
    }
}
