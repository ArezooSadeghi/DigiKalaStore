package com.example.digikalastore.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.digikalastore.database.UserDatabase;
import com.example.digikalastore.model.customer.User;
import com.example.digikalastore.repository.ProductRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public class GetLocationViewModel extends AndroidViewModel {

    private UserDatabase mDatabase;
    private ProductRepository mRepository;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private MutableLiveData<Location> mMyLocation = new MutableLiveData<>();


    public GetLocationViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance(getApplication());
        mDatabase = UserDatabase.getInstance(getApplication());
    }

    public MutableLiveData<Location> getMyLocation() {
        return mMyLocation;
    }

    public User getUser(String email) {
        return mDatabase.getUserDao().getUser(email);
    }

    public void updateUser(User user) {
        mDatabase.getUserDao().updateUser(user);
    }


    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setNumUpdates(1);
        locationRequest.setInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLocations().get(0);
                mMyLocation.setValue(location);
            }
        };

        mFusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
}
