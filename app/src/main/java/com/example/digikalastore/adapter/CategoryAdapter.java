package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.CategoryRecyclerViewItemBinding;
import com.example.digikalastore.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private Context mContext;
    private List<Category> mCategories;
    private SetItemClickListener mListener;
    private CategoryItemClickedCallback mCallback;

    public CategoryAdapter(Context context, List<Category> categories, SetItemClickListener listener) {
        mContext = context;
        mCategories = categories;
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

    public interface CategoryItemClickedCallback {
        void categoryItemClicked(int productId);
    }

    public interface SetItemClickListener {
        void ItemClicked(int categoryId);
    }
}
