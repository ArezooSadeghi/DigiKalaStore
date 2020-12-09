package com.example.digikalastore.uicontroller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.TitleProductAdapter;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.repository.ProductRepository;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mTitleRecyclerView;
    private ProductRepository mRepository;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = ProductRepository.getInstance(getContext());
        mRepository.fetchProductAsync();
        mRepository.getProductsLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
        mTitleRecyclerView = view.findViewById(R.id.recycler_view_title);
    }

    private void initViews() {
        mTitleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupAdapter(List<Product> products) {
        TitleProductAdapter adapter = new TitleProductAdapter(
                getContext(),
                mRepository.getTitles(),
                products);
        mTitleRecyclerView.setAdapter(adapter);
    }
}