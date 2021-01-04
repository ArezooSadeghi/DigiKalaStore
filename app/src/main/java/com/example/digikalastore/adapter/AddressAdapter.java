package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.AddressHolderBinding;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {
    private Context mContext;
    private List<String> mAddresses;

    public AddressAdapter(Context context, List<String> addresses) {
        mContext = context;
        mAddresses = addresses;
    }

    public List<String> getAddresses() {
        return mAddresses;
    }

    public void setAddresses(List<String> addresses) {
        mAddresses = addresses;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.address_holder,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        holder.mBinding.setAddress(mAddresses.get(position));
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    public class AddressHolder extends RecyclerView.ViewHolder {
        private AddressHolderBinding mBinding;

        public AddressHolder(AddressHolderBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindAddress(String address) {
            mBinding.setAddress(address);
        }
    }
}
