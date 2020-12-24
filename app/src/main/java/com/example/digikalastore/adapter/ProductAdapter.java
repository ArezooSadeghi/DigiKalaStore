package com.example.digikalastore.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.CartRecyclerViewItemBinding;
import com.example.digikalastore.databinding.ProductCategoryRecyclerViewItemBinding;
import com.example.digikalastore.databinding.ProductRelatedCategoryRecyclerViewItemBinding;
import com.example.digikalastore.databinding.ResultSearchProductItemBinding;
import com.example.digikalastore.diffutils.ProductDiffUtilCallback;
import com.example.digikalastore.event.AddEvent;
import com.example.digikalastore.event.DeleteEvent;
import com.example.digikalastore.event.RemoveEvent;
import com.example.digikalastore.model.Product;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Product> mProducts;
    private ProductCategoryItemClickedCallbcak mCallbcak;
    private int mViewType;

    public ProductAdapter(
            Context context,
            List<Product> products,
            int viewType,
            ProductCategoryItemClickedCallbcak callbcak) {

        mContext = context;
        mProducts = products;
        mViewType = viewType;
        mCallbcak = callbcak;
    }

    public ProductAdapter(Context context, List<Product> products, int viewType) {
        mContext = context;
        mProducts = products;
        mViewType = viewType;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ViewHolderOne(DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.product_category_recycler_view_item,
                    parent,
                    false));
        }

        if (viewType == 2) {
            return new ViewHolderTwo(DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.product_related_category_recycler_view_item,
                    parent,
                    false));
        }
        if (viewType == 3) {
            return new ViewHolderThree(DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.result_search_product_item,
                    parent,
                    false));
        } else {
            return new ViewHolderFour(DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.cart_recycler_view_item,
                    parent,
                    false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderOne) {
            ((ViewHolderOne) holder).bindProductOne(mProducts.get(position));
            ((ViewHolderOne) holder).mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallbcak.productCategoryItemClicked(mProducts.get(position).getId());
                }
            });
        } else if (holder instanceof ViewHolderTwo) {
            ((ViewHolderTwo) holder).mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallbcak.productCategoryItemClicked(mProducts.get(position).getId());
                }
            });
            ((ViewHolderTwo) holder).bindProductTwo(mProducts.get(position));

        } else if (holder instanceof ViewHolderThree) {
            ((ViewHolderThree) holder).bindProduct(mProducts.get(position));
            ((ViewHolderThree) holder).mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallbcak.productCategoryItemClicked(mProducts.get(position).getId());
                }
            });


        } else if (holder instanceof ViewHolderFour) {
            ((ViewHolderFour) holder).bindProduct(mProducts.get(position));
            ((ViewHolderFour) holder).mBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new AddEvent());

                    int numberOfProduct = getButtonText((ViewHolderFour) holder);

                    if (getButtonText((ViewHolderFour) holder) == 1) {
                        ((ViewHolderFour) holder).mBinding.btnDelete.setVisibility(View.GONE);
                        ((ViewHolderFour) holder).mBinding.btnRemove.setVisibility(View.VISIBLE);
                    }

                    ((ViewHolderFour) holder).mBinding.btnNumberOfProduct.setText(++numberOfProduct + "");
                }
            });

            ((ViewHolderFour) holder).mBinding.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new RemoveEvent());

                    int numberOfProduct = getButtonText((ViewHolderFour) holder);

                    if (numberOfProduct == 2) {
                        ((ViewHolderFour) holder).mBinding.btnRemove.setVisibility(View.GONE);
                        ((ViewHolderFour) holder).mBinding.btnDelete.setVisibility(View.VISIBLE);
                    }

                    ((ViewHolderFour) holder).mBinding.btnNumberOfProduct.setText(--numberOfProduct + "");
                }
            });

            ((ViewHolderFour) holder).mBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new DeleteEvent(((ViewHolderFour) holder).mBinding.getProduct().getId()));
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads);
        else {
            Bundle bundle = (Bundle) payloads.get(0);
            for (String key : bundle.keySet()) {
                ((ViewHolderFour) holder).bindProduct((Product) bundle.getSerializable(ProductDiffUtilCallback.PRODUCT_BUNDLE));
            }
        }
    }

    private int getButtonText(@NonNull ViewHolderFour holder) {
        return Integer.parseInt(holder
                .mBinding
                .btnNumberOfProduct
                .getText()
                .toString());
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public void updateProducts(List<Product> newProducts) {
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(new ProductDiffUtilCallback(mProducts, newProducts));
        diffResult.dispatchUpdatesTo(this);
        mProducts.clear();
        mProducts.addAll(newProducts);
    }

    @Override
    public int getItemViewType(int position) {
        return mViewType;
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {

        private ProductCategoryRecyclerViewItemBinding mBinding;

        public ViewHolderOne(ProductCategoryRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProductOne(Product product) {
            mBinding.setProduct(product);
        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder {

        private ProductRelatedCategoryRecyclerViewItemBinding mBinding;

        public ViewHolderTwo(ProductRelatedCategoryRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProductTwo(Product product) {
            mBinding.setProduct(product);
        }
    }

    public class ViewHolderThree extends RecyclerView.ViewHolder {

        private ResultSearchProductItemBinding mBinding;

        public ViewHolderThree(ResultSearchProductItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProduct(Product product) {
            mBinding.setProduct(product);
        }
    }

    public class ViewHolderFour extends RecyclerView.ViewHolder {

        private CartRecyclerViewItemBinding mBinding;

        public ViewHolderFour(CartRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProduct(Product product) {
            mBinding.setProduct(product);
        }
    }

    public interface ProductCategoryItemClickedCallbcak {
        void productCategoryItemClicked(int productId);
    }
}
