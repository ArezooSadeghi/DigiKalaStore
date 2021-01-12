package com.example.digikalastore.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.AddressAdapter;
import com.example.digikalastore.databinding.FragmentAddressBinding;
import com.example.digikalastore.model.customer.User;
import com.example.digikalastore.viewmodel.AddressViewModel;

import java.util.List;

public class AddressFragment extends Fragment {

    private String mEmail;
    private AddressViewModel mViewModel;
    private FragmentAddressBinding mBinding;

    public static AddressFragment newInstance() {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_address,
                container,
                false);

        initToolbar();
        initRecyclerView();
        setListener();

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddressFragmentArgs args = AddressFragmentArgs.fromBundle(getArguments());
        mEmail = args.getUserEmail();
        User user = mViewModel.getUser(mEmail);
        mViewModel.getAddresses().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> addresses) {
                setupAdapter(addresses);
            }
        });
    }


    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbarAddress);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
    }


    private void initRecyclerView() {
        mBinding.recyclerViewAddress.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void setListener() {
        mBinding.fabAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressFragmentDirections.ActionAddressFragmentToGetLocationFragment action =
                        AddressFragmentDirections.actionAddressFragmentToGetLocationFragment();
                action.setEmail(mEmail);
                NavHostFragment.findNavController(AddressFragment.this).navigate(action);
            }
        });
    }


    private void setupAdapter(List<String> addresses) {
        AddressAdapter adapter = new AddressAdapter(getContext(), addresses);
        mBinding.recyclerViewAddress.setAdapter(adapter);
    }
}