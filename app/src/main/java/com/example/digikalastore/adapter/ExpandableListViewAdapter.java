package com.example.digikalastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.digikalastore.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<String> mGroupList;
    private HashMap<String, ArrayList<String>> mChildList;

    public ExpandableListViewAdapter(Context context, ArrayList<String> groupList, HashMap<String, ArrayList<String>> childList) {
        mContext = context;
        mGroupList = groupList;
        mChildList = childList;
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mChildList.get(mGroupList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mGroupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mChildList.get(mGroupList.get(i)).get(i1);
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
        view = LayoutInflater.from(mContext).inflate(
                R.layout.parent_group,
                viewGroup,
                false);

        TextView textView = view.findViewById(R.id.txt_parent_group);
        textView.setText(String.valueOf(getGroup(i)));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(
                R.layout.child_group,
                viewGroup,
                false);
        TextView textView = view.findViewById(R.id.txt_child_group);
        textView.setText(String.valueOf(getChild(i, i1)));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
