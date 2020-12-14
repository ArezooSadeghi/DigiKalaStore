package com.example.digikalastore.uicontroller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.CategoryAdapter;
import com.example.digikalastore.databinding.FragmentHomeBinding;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.viewmodel.ProductViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private ProductViewModel mViewModel;

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
        setHasOptionsMenu(true);

        mViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);
        mViewModel.fetchProductAsync();
        setObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home,
                container,
                false);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbarHome);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);

        initViews();

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_search:
                getActivity().onSearchRequested();
                return true;
            default:
                return false;
        }
    }

    private void setObserver() {
        mViewModel.getProductsLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });
    }

    private void initViews() {
        mBinding.recyclerViewTitle.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupAdapter(List<Product> products) {
        CategoryAdapter adapter = new CategoryAdapter(
                getContext(),
                mViewModel.getCategories(),
                mViewModel.getProducts(),
                1,
                new CategoryAdapter.CategoryItemClickedCallback() {
                    @Override
                    public void categoryItemClicked(String productId) {
                        HomeFragmentDirections.ActionHomeFragmentToProductDetailFragment action =
                                HomeFragmentDirections.actionHomeFragmentToProductDetailFragment();
                        action.setProductId(productId);
                        NavHostFragment.findNavController(HomeFragment.this).navigate(action);
                    }
                });
        mBinding.recyclerViewTitle.setAdapter(adapter);
    }
}