package com.example.digikalastore.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikalastore.database.UserDatabase;
import com.example.digikalastore.model.customer.User;
import com.example.digikalastore.repository.ProductRepository;

import java.util.List;

public class AddressViewModel extends AndroidViewModel {
    private UserDatabase mDatabase;
    private ProductRepository mRepository;
    private MutableLiveData<List<String>> mAddresses = new MutableLiveData<>();

    public AddressViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance(getApplication());
        mDatabase = UserDatabase.getInstance(getApplication());
    }

    public MutableLiveData<List<String>> getAddresses() {
        return mAddresses;
    }

    public User getUser(String email) {
        User user = mDatabase.getUserDao().getUser(email);
        mAddresses.setValue(user.getAddresses());
        return user;
    }
}
