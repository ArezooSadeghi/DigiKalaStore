package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.CategoryRecyclerViewItemBinding;
import com.example.digikalastore.model.Category;
import com.example.digikalastore.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.CategoryProductHolder> {

    public static final int SPAN_COUNT = 3;

    private int mViewType;
    private Context mContext;
    private List<Category> mCategory;
    private List<Product> mProducts;
    private CategoryItemClickedCallback mCallback;

    public CategoryProductAdapter(
            Context context,
            List<Category> category,
            List<Product> products,
            int viewType,
            CategoryItemClickedCallback callback) {

        mContext = context;
        mCategory = category;
        mProducts = products;
        mCallback = callback;
        mViewType = viewType;
    }

    public CategoryProductAdapter(
            Context context,
            List<Category> category,
            List<Product> products,
            int viewType) {

        mContext = context;
        mCategory = category;
        mProducts = products;
        mViewType = viewType;
    }

    public List<Category> getCategory() {
        return mCategory;
    }

    public void setCategory(List<Category> category) {
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
        return new CategoryProductHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.category_recycler_view_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryProductHolder holder, int position) {
        holder.bindCategory(mCategory.get(position));
        if (mViewType == 1) {
            setProductTitleRecyclerViewTypeOne(holder.mBinding.recyclerViewProductTitle, getProducts(mCategory.get(position).getId()));
        } else {
            setProductTitleRecyclerViewTypeTwo(holder.mBinding.recyclerViewProductTitle, getProducts(mCategory.get(position).getId()));
        }
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

        public void bindCategory(Category category) {
            mBinding.setCategory(category);
        }
    }

    public void setProductTitleRecyclerViewTypeOne(RecyclerView recyclerView, List<Product> products) {
        recyclerView.setLayoutManager(new GridLayoutManager(
                mContext,
                SPAN_COUNT,
                GridLayoutManager.HORIZONTAL,
                false));

        ProductAdapter adapter = new ProductAdapter(
                mContext, products, 1, new ProductAdapter.ProductCategoryItemClickedCallbcak() {
            @Override
            public void productCategoryItemClicked(String productId) {
                mCallback.categoryItemClicked(productId);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void setProductTitleRecyclerViewTypeTwo(RecyclerView recyclerView, List<Product> products) {
        recyclerView.setLayoutManager(new LinearLayoutManager(
                mContext,
                LinearLayoutManager.HORIZONTAL,
                false));

        ProductAdapter adapter = new ProductAdapter(mContext, products, 2);
        recyclerView.setAdapter(adapter);
    }

    public interface CategoryItemClickedCallback {
        void categoryItemClicked(String productId);
    }

    public List<Product> getProducts(String categoryId) {
        List<Product> products = new ArrayList<>();
        for (Product product:mProducts) {
            for (Category category:product.getCategory()) {
                if (category.getId().equals(categoryId)) {
                    products.add(product);
                }
            }
        }
        return products;
    }
}
