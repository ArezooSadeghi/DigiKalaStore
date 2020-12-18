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

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.CategoryAdapter;
import com.example.digikalastore.databinding.FragmentCategoryBinding;
import com.example.digikalastore.model.Category;
import com.example.digikalastore.viewmodel.ProductViewModel;

import java.util.List;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding mBinding;
    private ProductViewModel mViewModel;

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
        mViewModel.fetchCategories();

        setObserver();
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
        mViewModel.getCategoryLiveData().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                setupAdapter(categories);
            }
        });
    }

    private void initToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.toolbarCategory);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
    }

    public void initRecyclerView() {
        mBinding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setupAdapter(List<Category> categories) {

        CategoryAdapter adapter = new CategoryAdapter(getContext(), categories, new CategoryAdapter.SetItemClickListener() {
            @Override
            public void ItemClicked(int categoryId) {
                CategoryFragmentDirections.ActionCategoryFragmentToAllCategoryProductsFragment action =
                        CategoryFragmentDirections.actionCategoryFragmentToAllCategoryProductsFragment();
                action.setCategoryId(categoryId);
                NavHostFragment.findNavController(CategoryFragment.this).navigate(action);
            }
        });
        mBinding.recyclerViewCategory.setAdapter(adapter);
    }
}