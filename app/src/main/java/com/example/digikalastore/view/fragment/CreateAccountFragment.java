package com.example.digikalastore.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.FragmentCreateAccountBinding;

public class CreateAccountFragment extends Fragment {
    private FragmentCreateAccountBinding mBinding;

    public static CreateAccountFragment newInstance() {
        CreateAccountFragment fragment = new CreateAccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_create_account,
                container,
                false);

        setListener();

        return mBinding.getRoot();
    }

    private void setListener() {
        mBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment
                        .findNavController(CreateAccountFragment.this)
                        .navigate(R.id.action_createAccountFragment_to_registerFragment);
            }
        });
    }
}