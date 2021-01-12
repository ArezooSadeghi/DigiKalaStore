package com.example.digikalastore.services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAddress extends HandlerThread {

    private Handler mHandler;
    private Context mContext;
    private MutableLiveData<String> mAddressesLiveData = new MutableLiveData<>();

    private static final int WHAT_REVERSE_GEOCODING = 0;
    public static final String TAG = LocationAddress.class.getSimpleName();

    public LocationAddress(Context context) {
        super(TAG);

        mContext = context;
    }


    public MutableLiveData<String> getAddressesLiveData() {
        return mAddressesLiveData;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if (msg.what == WHAT_REVERSE_GEOCODING)
                    if (msg.obj == null || !(msg.obj instanceof LatLng))
                        return;

                LatLng latLng = (LatLng) msg.obj;

                Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    String address = addresses.get(0).getAddressLine(0);
                    String country = addresses.get(0).getCountryName();
                    String state = addresses.get(0).getAdminArea();
                    String city = addresses.get(0).getLocality();
                    String postalCode = addresses.get(0).getPostalCode();
                    mAddressesLiveData.postValue(
                            address != null ? address : ""
                                    + country != null ? country : ""
                                    + state != null ? state : ""
                                    + city != null ? city : ""
                                    + postalCode != null ? postalCode : "");
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }

                String knownName = addresses.get(0).getFeatureName();
            }
        };
    }

    public void queueMessage(LatLng latLng) {
        Message message = mHandler.obtainMessage(WHAT_REVERSE_GEOCODING, latLng);
        message.sendToTarget();
    }
}
