package com.example.digikalastore.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.digikalastore.model.customer.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("select * from user_table")
    List<User> getUsers();
}
