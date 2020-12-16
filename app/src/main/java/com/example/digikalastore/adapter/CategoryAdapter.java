package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    public static final int SPAN_COUNT = 3;

    private int mViewType;
    private Context mContext;
    private List<Category> mCategories;
    private List<Product> mProducts;
    private List<String> mTitles;
    private CategoryItemClickedCallback mCallback;
    private SetItemClickListener mListener;

    public CategoryAdapter(
            Context context,
            List<Category> categories,
            List<Product> products,
            int viewType,
            CategoryItemClickedCallback callback) {

        mContext = context;
        mCategories = categories;
        mProducts = products;
        mCallback = callback;
        mViewType = viewType;
    }

    public CategoryAdapter(
            Context context,
            List<Category> categories,
            List<Product> products,
            int viewType) {

        mContext = context;
        mCategories = categories;
        mProducts = products;
        mViewType = viewType;
    }

    public CategoryAdapter(
            int viewType,
            Context context,
            List<Category> categories,
            List<Product> products,
            SetItemClickListener listener) {

        mViewType = viewType;
        mContext = context;
        mCategories = categories;
        mProducts = products;
        mListener = listener;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.category_recycler_view_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.bindCategory(mCategories.get(position));
        if (mViewType == 1) {
            setProductRecyclerViewTypeOne(
                    holder.mBinding.recyclerViewProductCategory,
                    getProducts(mCategories.get(position).getId()));
        } else {
            setProductRecyclerViewTypeTwo(
                    holder.mBinding.recyclerViewProductCategory,
                    getProducts(mCategories.get(position).getId()));
        }
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        private CategoryRecyclerViewItemBinding mBinding;

        public CategoryHolder(CategoryRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mBinding.btnSeeAllProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.ItemClicked(mBinding.getCategory().getId());
                }
            });
        }

        public void bindCategory(Category category) {
            mBinding.setCategory(category);
        }
    }

    public void setProductRecyclerViewTypeOne(RecyclerView recyclerView, List<Product> products) {
        recyclerView.setLayoutManager(new LinearLayoutManager(
                mContext,
                LinearLayoutManager.HORIZONTAL,
                false));

        ProductAdapter adapter = new ProductAdapter(
                mContext, products, 1, new ProductAdapter.ProductCategoryItemClickedCallbcak() {
            @Override
            public void productCategoryItemClicked(int productId) {
                mCallback.categoryItemClicked(productId);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void setProductRecyclerViewTypeTwo(RecyclerView recyclerView, List<Product> products) {
        recyclerView.setLayoutManager(new LinearLayoutManager(
                mContext,
                LinearLayoutManager.HORIZONTAL,
                false));

        ProductAdapter adapter = new ProductAdapter(mContext, products, 2);
        recyclerView.setAdapter(adapter);
    }

    public interface CategoryItemClickedCallback {
        void categoryItemClicked(int productId);
    }

    public interface SetItemClickListener {
        void ItemClicked(String categoryId);
    }

    public List<Product> getProducts(String categoryId) {
        List<Product> products = new ArrayList<>();
        for (Product product : mProducts) {
            for (Category category : product.getCategory()) {
                if (category.getId().equals(categoryId)) {
                    products.add(product);
                }
            }
        }
        return products;
    }
}
