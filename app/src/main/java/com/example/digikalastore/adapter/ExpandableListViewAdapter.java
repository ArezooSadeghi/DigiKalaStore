package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.digikalastore.R;
import com.example.digikalastore.databinding.ChildGroupBinding;
import com.example.digikalastore.databinding.ParentGroupBinding;
import com.example.digikalastore.event.ClickEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<String> mParentList;
    private HashMap<String, ArrayList<String>> mChildList;

    public ExpandableListViewAdapter(Context context, ArrayList<String> parentList, HashMap<String, ArrayList<String>> childList) {
        mContext = context;
        mParentList = parentList;
        mChildList = childList;
    }

    @Override
    public int getGroupCount() {
        return mParentList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mChildList.get(mParentList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mParentList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mChildList.get(mParentList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ParentGroupBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.parent_group,
                viewGroup,
                false);

        binding.setText(String.valueOf(getGroup(i)));
        return binding.getRoot();
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildGroupBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.child_group,
                viewGroup,
                false);

        binding.setText(String.valueOf(getChild(i, i1)));

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new ClickEvent(binding.getText()));
            }
        });

        return binding.getRoot();
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

}
