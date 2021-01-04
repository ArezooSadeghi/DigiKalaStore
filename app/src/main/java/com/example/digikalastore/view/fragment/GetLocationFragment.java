package com.example.digikalastore.view.fragment;

import android.os.Bundle;

import com.google.android.gms.maps.SupportMapFragment;

public class GetLocationFragment extends SupportMapFragment {

    public static GetLocationFragment newInstance() {
        GetLocationFragment fragment = new GetLocationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}