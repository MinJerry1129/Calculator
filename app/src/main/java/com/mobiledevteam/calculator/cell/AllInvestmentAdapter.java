package com.mobiledevteam.calculator.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.mobiledevteam.calculator.R;
import com.mobiledevteam.calculator.Utils.Common;

import java.util.ArrayList;
import java.util.List;

public class AllInvestmentAdapter extends ArrayAdapter<Investment> {
    private Context mContext;
    private List<Investment> allInvestList = new ArrayList<>();
    private ArrayList<String> mInvestmentCategory= Common.getInstance().getmInvestmentCategory();

    public AllInvestmentAdapter(Context context, ArrayList<Investment> list) {
        super(context, 0 , list);
        mContext = context;
        allInvestList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_investment,parent,false);

        Investment currentInvest = allInvestList.get(position);
//
        TextView name = (TextView) listItem.findViewById(R.id.txt_investtitle);
        name.setText(mInvestmentCategory.get(currentInvest.getmType()));
        TextView price = (TextView) listItem.findViewById(R.id.txt_investprice);
        price.setText(currentInvest.getmPrice());
        TextView investdate = (TextView) listItem.findViewById(R.id.txt_investdate);
        investdate.setText(currentInvest.getmDate());
        return listItem;
    }
}
