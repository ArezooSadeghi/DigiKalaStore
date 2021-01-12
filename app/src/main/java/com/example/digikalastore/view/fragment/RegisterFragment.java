package com.example.digikalastore.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.FragmentRegisterBinding;
import com.example.digikalastore.model.customer.User;
import com.example.digikalastore.viewmodel.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;
    private FragmentRegisterBinding mBinding;

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_register,
                container,
                false);

        setListener();

        return mBinding.getRoot();
    }


    private void setListener() {
        mBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.txtFirstName.getText().toString().equals("") ||
                        mBinding.txtLastName.getText().toString().equals("") ||
                        mBinding.txtUsername.getText().toString().equals("") ||
                        mBinding.txtEmail.getText().toString().equals("")) {
                    Toast.makeText(
                            getContext(),
                            R.string.fill_required_fields,
                            Toast.LENGTH_LONG).show();
                } else {
                    String firstName = mBinding.txtFirstName.getText().toString();
                    String lastName = mBinding.txtLastName.getText().toString();
                    String userName = mBinding.txtUsername.getText().toString();
                    String email = mBinding.txtEmail.getText().toString();
                    User user = new User(firstName, lastName, userName, email);
                    if (mViewModel.isValidUser(user)) {
                        mViewModel.insertUser(user);
                        mViewModel.sendCustomer(email);
                        Toast.makeText(
                                getContext(),
                                R.string.register_successful,
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(
                                getContext(),
                                R.string.exist_user,
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}