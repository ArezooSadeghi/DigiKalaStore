package com.example.digikalastore.diffutils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.digikalastore.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDiffUtilCallback extends DiffUtil.Callback {

    public static final String PRODUCT_BUNDLE = "productBundle";

    private List<Product> mOldProducts = new ArrayList<>();
    private List<Product> mNewProducts = new ArrayList<>();

    public ProductDiffUtilCallback(List<Product> oldProducts, List<Product> newProducts) {
        mOldProducts = oldProducts;
        mNewProducts = newProducts;
    }

    @Override
    public int getOldListSize() {
        return mOldProducts != null ? mOldProducts.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewProducts != null ? mNewProducts.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = mNewProducts.get(newItemPosition).compareTo(mOldProducts.get(oldItemPosition));
        if (result == 0) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Product newProduct = mNewProducts.get(newItemPosition);
        Product oldProduct = mOldProducts.get(oldItemPosition);

        Bundle bundle = new Bundle();

        if (!(newProduct == oldProduct)) {
            bundle.putSerializable(PRODUCT_BUNDLE, newProduct);
        }
        if (bundle.size() == 0) {
            return null;
        }
        return bundle;
    }
}
