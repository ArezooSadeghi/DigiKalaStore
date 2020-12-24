package com.example.digikalastore.event;

public class DeleteEvent {

    private final int mProductId;

    public DeleteEvent(int productId) {
        mProductId = productId;
    }

    public int getProductId() {
        return mProductId;
    }
}
