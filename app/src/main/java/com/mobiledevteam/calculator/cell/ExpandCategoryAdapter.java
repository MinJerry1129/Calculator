package com.mobiledevteam.calculator.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.mobiledevteam.calculator.R;

import java.util.ArrayList;

public class ExpandCategoryAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<Category> mCategory;

    public ExpandCategoryAdapter(Context context,ArrayList<Category> category){
        this.context = context;
        this.mCategory = category;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mCategory.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return "Select Category";
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mCategory.get(childPosition).getmTitle();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView groupName = convertView.findViewById(R.id.list_parent);
        groupName.setText("Select Category");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String child = (String) getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView groupName = convertView.findViewById(R.id.list_parent);
        groupName.setText(child);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
