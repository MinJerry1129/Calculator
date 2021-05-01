package com.mobiledevteam.calculator.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;

import java.util.ArrayList;
import java.util.List;

public class LiabilityListAdapter extends ArrayAdapter<PayInfo> {
    private Context mContext;
    private List<PayInfo> allPayinfoList = new ArrayList<>();
    private ArrayList<Category> mCategory= Common.getInstance().getmLiabilityCategory();
    public LiabilityListAdapter(@NonNull Context context, ArrayList<PayInfo> list) {
        super(context, 0, list);
        mContext = context;
        allPayinfoList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
        View listItem = convertView;
        PayInfo onePayinfo = allPayinfoList.get(position);
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_home_liability,parent,false);

        ImageView imageView = (ImageView) listItem.findViewById(R.id.img_category);
        TextView titleText = (TextView) listItem.findViewById(R.id.txt_category);
        TextView priceText = (TextView) listItem.findViewById(R.id.txt_price);

        titleText.setText(mCategory.get(onePayinfo.getmCategoryID()).getmTitle());
        imageView.setImageResource(mCategory.get(onePayinfo.getmCategoryID()).getmImageUrl());
        priceText.setText("-$ " + onePayinfo.getmPrice());

        return listItem;
    }
}
