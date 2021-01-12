package com.example.digikalastore.model.customer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "user_table")
public class User {

    @NotNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "first_name")
    private String mFirstName;
    @ColumnInfo(name = "last_name")
    private String mLastName;
    @ColumnInfo(name = "username")
    private String mUserName;
    @ColumnInfo(name = "email")
    private String mEmail;
    @ColumnInfo(name = "address")
    private List<String> mAddresses;

    public User(String firstName, String lastName, String userName, String email) {
        mFirstName = firstName;
        mLastName = lastName;
        mUserName = userName;
        mEmail = email;
        mAddresses = new ArrayList<>();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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

    public List<String> getAddresses() {
        return mAddresses;
    }

    public void setAddresses(List<String> addresses) {
        mAddresses = addresses;
    }
}
