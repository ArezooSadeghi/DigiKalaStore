package com.example.digikalastore.model.customer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Entity(tableName = "user_table")
public class User {

    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private UUID mUserId;
    @ColumnInfo(name = "first_name")
    private String mFirstName;
    @ColumnInfo(name = "last_name")
    private String mLastName;
    @ColumnInfo(name = "username")
    private String mUserName;
    @ColumnInfo(name = "email")
    private String mEmail;

    public User() {
        mUserId = UUID.randomUUID();
    }

    public User(String firstName, String lastName, String userName, String email) {
        mFirstName = firstName;
        mLastName = lastName;
        mUserName = userName;
        mEmail = email;
        mUserId = UUID.randomUUID();
    }

    @NotNull
    public UUID getUserId() {
        return mUserId;
    }

    public void setUserId(@NotNull UUID userId) {
        mUserId = userId;
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
}
