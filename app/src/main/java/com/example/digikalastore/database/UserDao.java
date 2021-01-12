package com.example.digikalastore.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.digikalastore.model.customer.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("select * from user_table")
    List<User> getUsers();

    @Query("select * from user_table where email=:userEmail")
    User getUser(String userEmail);

    @Update
    void updateUser(User user);
}
