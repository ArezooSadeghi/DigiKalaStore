package com.example.digikalastore.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.digikalastore.database.UserDatabase;
import com.example.digikalastore.model.customer.User;
import com.example.digikalastore.repository.ProductRepository;

import java.util.List;

public class RegisterViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private UserDatabase mDatabase;

    public RegisterViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance(getApplication());
        mDatabase = UserDatabase.getInstance(getApplication());
    }

    public void sendCustomer(String email) {
        mRepository.sendCustomer(email);
    }

    public void insertUser(User user) {
        mDatabase.getUserDao().insert(user);
    }

    public boolean isValidUser(User newUser) {
        List<User> users = mDatabase.getUserDao().getUsers();
        if (users.size() == 0) {
            return true;
        } else {
            for (User user : users) {
                if (user.getEmail().equals(newUser.getEmail())) {
                    return false;
                }
            }
            return true;
        }
    }
}
