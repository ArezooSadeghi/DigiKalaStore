package com.example.digikalastore.event;

import com.example.digikalastore.model.Product;

public class DeleteEvent {

    private final Product mProduct;

    public DeleteEvent(Product product) {
        mProduct = product;
    }

    public Product getProduct() {
        return mProduct;
    }
}
