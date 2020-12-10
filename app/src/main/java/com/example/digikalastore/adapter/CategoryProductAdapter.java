package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.CategoryRecyclerViewItemBinding;
import com.example.digikalastore.model.Product;

import java.util.List;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.CategoryProductHolder> {

    public static final int SPAN_COUNT = 3;
    private Context mContext;
    private List<String> mCategory;
    private List<Product> mProducts;
    private CategoryItemClickedCallback mCallback;

    public CategoryProductAdapter(Context context, List<String> category, List<Product> products, CategoryItemClickedCallback callback) {
        mContext = context;
        mCategory = category;
        mProducts = products;
        mCallback = callback;
    }

    public List<String> getCategory() {
        return mCategory;
    }

    public void setCategory(List<String> category) {
        mCategory = category;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    @NonNull
    @Override
    public CategoryProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryRecyclerViewItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.category_recycler_view_item,
                parent,
                false);
        return new CategoryProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductHolder holder, int position) {
        holder.bindTitle(mCategory.get(position));
        setProductTitleRecyclerView(holder.mBinding.recyclerViewProductTitle, mProducts);
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public class CategoryProductHolder extends RecyclerView.ViewHolder {

        private CategoryRecyclerViewItemBinding mBinding;

        public CategoryProductHolder(CategoryRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindTitle(String title) {
            mBinding.setTitle(title);
        }
    }

    public void setProductTitleRecyclerView(RecyclerView recyclerView, List<Product> products) {
        recyclerView.setLayoutManager(new GridLayoutManager(
                mContext,
                SPAN_COUNT,
                GridLayoutManager.HORIZONTAL,
                false));

        ProductAdapter adapter = new ProductAdapter(
                mContext, products, new ProductAdapter.ProductCategoryItemClickedCallbcak() {
            @Override
            public void productCategoryItemClicked(String productId) {
                mCallback.categoryItemClicked(productId);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public interface CategoryItemClickedCallback {
        void categoryItemClicked(String productId);
    }
}
