package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.ProductTitleRecyclerViewItemBinding;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.uicontroller.fragment.HomeFragmentDirections;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Context mContext;
    private List<Product> mProducts;

    public ProductAdapter(Context context, List<Product> products) {
        mContext = context;
        mProducts = products;
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
        ProductTitleRecyclerViewItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.product_title_recycler_view_item,
                parent,
                false);
        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.bindProduct(mProducts.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = HomeFragmentDirections
                        .actionHomeFragmentToProductDetailFragment(holder.mBinding.getProduct());
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        private ProductTitleRecyclerViewItemBinding mBinding;

        public ProductHolder(ProductTitleRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProduct(Product product) {
            mBinding.setProduct(product);
        }
    }
}
