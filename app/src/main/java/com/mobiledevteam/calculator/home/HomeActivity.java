package com.mobiledevteam.calculator.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mobiledevteam.calculator.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private PieChart _pieMoney;
    private String[] parties = new String[] {
            "Remaining", "Liabilities"
    };
    private int[] COLORS = {
            Color.rgb(2, 195, 154), Color.rgb(5, 102, 141)
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        _pieMoney = (PieChart)findViewById(R.id.chart_Money);
        setReady();
    }
    private void setReady(){
        _pieMoney.setDragDecelerationFrictionCoef(0.95f);
        _pieMoney.getDescription().setEnabled(false);
        _pieMoney.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        _pieMoney.setHoleRadius(58f);
        _pieMoney.setTransparentCircleRadius(61f);
        _pieMoney.setRotationAngle(0);
        _pieMoney.setRotationEnabled(true);
        _pieMoney.setHighlightPerTapEnabled(true);

        _pieMoney.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = _pieMoney.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
        setData(2, 2);
    }
    private void setData(int count, float range) {

        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry(62, parties[0]));
        entries.add(new PieEntry(38, parties[1]));

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : COLORS)
            colors.add(c);
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.rgb(2, 195, 154));
        _pieMoney.setData(data);

        // undo all highlights
        _pieMoney.highlightValues(null);
        _pieMoney.invalidate();
    }
}