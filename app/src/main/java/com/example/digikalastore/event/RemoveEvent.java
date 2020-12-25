package com.example.digikalastore.event;

public class RemoveEvent {

    private final String mProductPrice;

    public RemoveEvent(String productPrice) {
        mProductPrice = productPrice;
    }

    public String getProductPrice() {
        return mProductPrice;
    }
}
