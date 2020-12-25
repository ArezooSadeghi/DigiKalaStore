package com.example.digikalastore.event;

public class ClickEvent {

    private final String mText;

    public ClickEvent(String text) {
        mText = text;
    }

    public String getText() {
        return mText;
    }
}
