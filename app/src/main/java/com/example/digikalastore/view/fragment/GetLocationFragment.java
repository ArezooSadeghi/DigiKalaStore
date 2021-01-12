package com.example.digikalastore.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.FragmentGetLocationBinding;
import com.example.digikalastore.model.customer.User;
import com.example.digikalastore.services.LocationAddress;
import com.example.digikalastore.viewmodel.GetLocationViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class GetLocationFragment extends Fragment {

    private User mUser;
    private LatLng mLatLng;
    private GoogleMap mGoogleMap;
    private GetLocationViewModel mViewModel;
    private LocationAddress mLocationAddress;
    private FragmentGetLocationBinding mBinding;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 0;

    public static GetLocationFragment newInstance() {
        GetLocationFragment fragment = new GetLocationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(GetLocationViewModel.class);
        mViewModel.getMyLocation().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                updateUI();
            }
        });

        mLocationAddress = new LocationAddress(getContext());
        mLocationAddress.start();
        mLocationAddress.getLooper();
        mLocationAddress.getAddressesLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String address) {
                List<String> addresses = mUser.getAddresses();
                addresses.add(address);
                mUser.setAddresses(addresses);
                mViewModel.updateUser(mUser);
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_get_location,
                container,
                false);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                updateUI();
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        mLatLng = latLng;
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + ":" + latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });

        setListener();

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetLocationFragmentArgs args = GetLocationFragmentArgs.fromBundle(getArguments());
        String email = args.getEmail();
        mUser = mViewModel.getUser(email);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationAddress.quit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_get_location, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_my_location:
                if (hasLocationAccess()) {
                    getCurrentLocation();
                } else {
                    requestLocationAccessPermission();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_LOCATION:
                if (grantResults == null && grantResults.length == 0)
                    return;
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
                return;
        }
    }


    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        if (!hasLocationAccess())
            return;

        mViewModel.getCurrentLocation();
    }


    private void setListener() {
        mBinding.btnConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLatLng != null) {
                    mLocationAddress.queueMessage(mLatLng);
                }
            }
        });
    }


    private boolean hasLocationAccess() {
        boolean isFineLocation = ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean isCoarseLocation = ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        return isFineLocation && isCoarseLocation;
    }


    private void requestLocationAccessPermission() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        requestPermissions(permissions, REQUEST_CODE_PERMISSION_LOCATION);
    }


    private void updateUI() {
        Location location = mViewModel.getMyLocation().getValue();
        if (location == null || mGoogleMap == null)
            return;

        LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions myMarkerOptions = new MarkerOptions()
                .position(myLatLng);

        mGoogleMap.addMarker(myMarkerOptions);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(myLatLng));
    }
}
