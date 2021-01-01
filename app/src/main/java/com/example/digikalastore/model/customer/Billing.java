package com.example.digikalastore.model.customer;

public class Billing {

    private String mFirstName;
    private String mLastName;
    private String mCompany;
    private String mAddressOne;
    private String mAddressTwo;
    private String mCity;
    private String mState;
    private String mPostcode;
    private String mCountry;
    private String mEmail;
    private String mPhone;

    public Billing() {

    }

    public Billing(
            String firstName,
            String lastName,
            String company,
            String addressOne,
            String addressTwo,
            String city,
            String state,
            String postcode,
            String country,
            String email,
            String phone) {
        mFirstName = firstName;
        mLastName = lastName;
        mCompany = company;
        mAddressOne = addressOne;
        mAddressTwo = addressTwo;
        mCity = city;
        mState = state;
        mPostcode = postcode;
        mCountry = country;
        mEmail = email;
        mPhone = phone;
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

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getAddressOne() {
        return mAddressOne;
    }

    public void setAddressOne(String addressOne) {
        mAddressOne = addressOne;
    }

    public String getAddressTwo() {
        return mAddressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        mAddressTwo = addressTwo;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getPostcode() {
        return mPostcode;
    }

    public void setPostcode(String postcode) {
        mPostcode = postcode;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }
}
