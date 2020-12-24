package com.example.digikalastore.view.fragment;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.CategoryAdapter;
import com.example.digikalastore.databinding.FragmentCategoryBinding;
import com.example.digikalastore.model.Category;
import com.example.digikalastore.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding mBinding;
    private ProductViewModel mViewModel;
    private CategoryAdapter mAdapter;
    private int mCurrentPage = 1;
    private List<Category> mCategories = new ArrayList<>();

    public CategoryFragment() {

    }


    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_category,
                container,
                false);


        initToolBar();
        initRecyclerView();
        setupAdapter(mCategories);
        getCategories();


        mBinding.recyclerViewCategory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!mBinding.recyclerViewCategory.canScrollVertically(1)) {
                    if (mCurrentPage <= mViewModel.getTotalPageLiveData().getValue()) {
                        mViewModel.fetchCategories(mCurrentPage);
                        mCurrentPage++;
                    }
                }
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


    private void initToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbarCategory);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
    }


    public void initRecyclerView() {
        mBinding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void setupAdapter(List<Category> categories) {
        mAdapter = new CategoryAdapter(getContext(), categories, new CategoryAdapter.SetItemClickListener() {
            @Override
            public void ItemClicked(int categoryId) {
                CategoryFragmentDirections.ActionCategoryFragmentToAllCategoryProductsFragment action =
                        CategoryFragmentDirections.actionCategoryFragmentToAllCategoryProductsFragment();
                action.setCategoryId(categoryId);
                NavHostFragment.findNavController(CategoryFragment.this).navigate(action);
            }
        });
        mBinding.recyclerViewCategory.setAdapter(mAdapter);
    }


    public void getCategories() {
        loadingVisibility();
        mViewModel.fetchCategories(mCurrentPage);
        mCurrentPage++;
        mViewModel.getCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                loadingVisibility();
                int positionStart = mCategories.size();
                mCategories.addAll(categories);
                int itemCount = mCategories.size();
                mAdapter.notifyItemRangeInserted(positionStart, itemCount);
            }
        });
    }


    private void loadingVisibility() {
        if (mCurrentPage == 1)
            if (mBinding.getIsLoading() != null && mBinding.getIsLoading())
                mBinding.setIsLoading(false);
            else
                mBinding.setIsLoading(true);
        else
            mBinding.setIsLoading(false);
    }
}
