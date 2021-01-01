package com.example.digikalastore.model.customer;

public class Customer {

    private String mFirstName;
    private String mLastName;
    private String mUserName;
    private String mEmail;
    private Billing mBilling;
    private Shipping mShipping;

    public Customer() {
    }

    public Customer(
            String firstName,
            String lastName,
            String userName,
            String email,
            Billing billing,
            Shipping shipping) {
        mFirstName = firstName;
        mLastName = lastName;
        mUserName = userName;
        mEmail = email;
        mBilling = billing;
        mShipping = shipping;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Billing getBilling() {
        return mBilling;
    }

    public void setBilling(Billing billing) {
        mBilling = billing;
    }

    public Shipping getShipping() {
        return mShipping;
    }

    public void setShipping(Shipping shipping) {
        mShipping = shipping;
    }
}
