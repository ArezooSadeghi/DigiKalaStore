package com.example.digikalastore.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.ProductAdapter;
import com.example.digikalastore.databinding.FragmentCartBinding;
import com.example.digikalastore.event.AddEvent;
import com.example.digikalastore.event.DeleteEvent;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.viewmodel.ProductViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private FragmentCartBinding mBinding;
    private ProductViewModel mViewModel;
    private ProductAdapter mAdapter;

    public CartFragment() {
    }


    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        setObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_cart,
                container,
                false);

        initRecyclerView();

        return mBinding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void updateRecyclerView(DeleteEvent deleteEvent) {
        for (int i = 0; i < mViewModel.getProductList().size(); i++) {
            if (mViewModel.getProductList().get(i).getId() == deleteEvent.getProductId()) {
                mViewModel.getProductList().remove(i);
            }
        }
        setObserver();
    }

    private void setObserver() {
        mViewModel.getProductListLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });

        mViewModel.getProductsPriceLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> productsPrice) {
                double totalPrice = 0;
                List<Double> calculateTotalPrice = new ArrayList<>();
                for (String price:productsPrice) {
                    calculateTotalPrice.add(Double.parseDouble(price));
                }
                for (Double price:calculateTotalPrice) {
                    totalPrice += price;
                }
                mBinding.txtTotalPrice.setText(getString(R.string.total_price) + ":" + totalPrice + ":" + getString(R.string.currency));
            }
        });
    }

    private void initRecyclerView() {
        mBinding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerViewCart.addItemDecoration(new DividerItemDecoration(
                mBinding.recyclerViewCart.getContext(),
                DividerItemDecoration.VERTICAL));
    }

    private void setupAdapter(List<Product> products) {
        mAdapter = new ProductAdapter(getContext(), products, 4);
        mBinding.recyclerViewCart.setAdapter(mAdapter);
    }
}