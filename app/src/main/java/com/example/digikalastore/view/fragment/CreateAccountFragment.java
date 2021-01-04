package com.example.digikalastore.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.FragmentCreateAccountBinding;
import com.example.digikalastore.model.customer.User;
import com.example.digikalastore.viewmodel.ProductViewModel;

import java.util.List;

public class CreateAccountFragment extends Fragment {
    private FragmentCreateAccountBinding mBinding;
    private ProductViewModel mViewModel;

    public static CreateAccountFragment newInstance() {
        CreateAccountFragment fragment = new CreateAccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
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

        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.txtUsername.getText().toString().equals("") || mBinding.txtEmail.getText().toString().equals("")) {
                    Toast.makeText(getContext(), R.string.fill_required_fields, Toast.LENGTH_LONG).show();
                } else {
                    List<User> users = mViewModel.getUsers();

                    boolean flag = true;
                    for (User user : users) {
                        if (user.getEmail().equals(mBinding.txtEmail.getText().toString()) && user.getUserName().equals(mBinding.txtUsername.getText().toString())) {
                            flag = false;
                            CreateAccountFragmentDirections.ActionCreateAccountFragmentToAddressFragment action =
                                    CreateAccountFragmentDirections.actionCreateAccountFragmentToAddressFragment();
                            action.setUserEmail(user.getEmail());
                            NavHostFragment.findNavController(CreateAccountFragment.this).navigate(action);
                        }

                    }
                    if (flag) {
                        Toast.makeText(getContext(), R.string.wrong_username_or_email, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}