package com.mobiledevteam.calculator.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;

import java.util.ArrayList;
import java.util.List;

public class AllSavingListAdapter extends ArrayAdapter<Saving> {
    private Context mContext;
    private List<Saving> allSavingList = new ArrayList<>();

    public AllSavingListAdapter(Context context, ArrayList<Saving> list) {
        super(context, 0, list);
        mContext = context;
        allSavingList = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_saving, parent, false);

        Saving currentSaving = allSavingList.get(position);
//
        TextView title = (TextView) listItem.findViewById(R.id.txt_savingtitle);
        title.setText(currentSaving.getmTitle());
        TextView price = (TextView) listItem.findViewById(R.id.txt_savingprice);
        price.setText(currentSaving.getmPrice());
        TextView startdate = (TextView) listItem.findViewById(R.id.txt_savingstartdate);
        startdate.setText(currentSaving.getmStartDate());
        TextView endate = (TextView) listItem.findViewById(R.id.txt_savingenddate);
        endate.setText(currentSaving.getmEndDate());
        return listItem;
    }
}