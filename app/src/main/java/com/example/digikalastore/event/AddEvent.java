package com.example.digikalastore.event;

public class AddEvent {

    private final String mProductPrice;

    public AddEvent(String productPrice) {
        mProductPrice = productPrice;
    }

    public String getProductPrice() {
        return mProductPrice;
    }
}
