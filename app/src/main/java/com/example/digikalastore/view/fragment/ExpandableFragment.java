package com.example.digikalastore.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.digikalastore.R;
import com.example.digikalastore.adapter.ExpandableListViewAdapter;
import com.example.digikalastore.databinding.FragmentExpandableBinding;
import com.example.digikalastore.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandableFragment extends Fragment {

    private FragmentExpandableBinding mBinding;
    private ProductViewModel mViewModel;
    private HashMap<String, ArrayList<String>> mChildList = new HashMap<>();

    public static ExpandableFragment newInstance() {
        ExpandableFragment fragment = new ExpandableFragment();
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
                R.layout.fragment_expandable,
                container,
                false);

        mChildList.put(getString(R.string.ordering), mViewModel.getChildItemList());
        ArrayList<String> mParentList = new ArrayList<>(mChildList.keySet());

        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(getContext(), mParentList, mChildList);
        mBinding.expandableListView.setAdapter(adapter);

        return mBinding.getRoot();
    }
}