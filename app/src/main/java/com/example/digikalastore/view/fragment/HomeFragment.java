package com.example.digikalastore.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.HeaderAdapter;
import com.example.digikalastore.databinding.FragmentHomeBinding;
import com.example.digikalastore.model.Product;
import com.example.digikalastore.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private ProductViewModel mViewModel;
    private HashMap<String, List<Product>> mItems;
    private List<Product> mProducts;

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

        mItems = new HashMap<>();

        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);


        zip();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home,
                container,
                false);

        initToolbar();

        initRecyclerView();

        mBinding.recyclerViewHeader.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

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

    private void zip() {

        Observable.zip(
                mViewModel.getLatestProductsObservable(),
                mViewModel.getBestProductsObservable(),
                mViewModel.getMostVisitedProductsObservable(),
                mViewModel.getFeaturedProductsObservable(),
                new Function4<List<Product>, List<Product>, List<Product>, List<Product>, HashMap<String, List<Product>>>() {
            @io.reactivex.annotations.NonNull
            @Override
            public HashMap<String, List<Product>> apply(
                    @io.reactivex.annotations.NonNull List<Product> products,
                    @io.reactivex.annotations.NonNull List<Product> products2,
                    @io.reactivex.annotations.NonNull List<Product> products3,
                    @io.reactivex.annotations.NonNull List<Product> products4) throws Exception {

                mItems.put(getString(R.string.latest_products_header), products);
                mItems.put(getString(R.string.best_products_header), products2);
                mItems.put(getString(R.string.most_visited_products_header), products3);
                mItems.put(getString(R.string.featured_products_header), products4);

                mProducts = products4;

                return mItems;
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setupAdapter);
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbarHome);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
    }

    private void initRecyclerView() {
        mBinding.recyclerViewHeader.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerViewHeader.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayout.VERTICAL));
    }

    private void setupAdapter(HashMap<String, List<Product>> items) {
        HeaderAdapter adapter = new HeaderAdapter(getContext(), items, new HeaderAdapter.SetItemClickedListener() {
            @Override
            public void itemClicked(int productId) {
                HomeFragmentDirections.ActionHomeFragmentToProductDetailFragment action =
                        HomeFragmentDirections.actionHomeFragmentToProductDetailFragment();
                action.setProductId(productId);
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            }
        });
        adapter.setHeaders(new ArrayList<>(mItems.keySet()));
        adapter.setProducts(mProducts);
        mBinding.recyclerViewHeader.setAdapter(adapter);
    }
}