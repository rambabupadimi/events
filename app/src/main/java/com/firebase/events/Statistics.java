package com.firebase.events;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Map;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        PieChart pieChart = (PieChart) findViewById(R.id.chart);
        ArrayList<Entry> entries = new ArrayList<>();

        AppPreferences appPreferences = new AppPreferences(this);
        Map map = appPreferences.loadMap();
        if(map != null)
        {

            entries.add(new Entry(Integer.parseInt(map.get("hiring").toString()), 0));
            entries.add(new Entry(Integer.parseInt(map.get("hackathon").toString()), 1));
            entries.add(new Entry(Integer.parseInt(map.get("bot").toString()), 2));
            entries.add(new Entry(Integer.parseInt(map.get("competitive").toString()), 3));

        }

        PieDataSet dataset = new PieDataSet(entries, "");

        // creating labels<br />
        ArrayList<String> labels = new ArrayList<String>();
                labels.add("Hiring");
                labels.add("Hackathon");
        labels.add("Bot");
        labels.add("Competitive");

        PieData data = new PieData(labels, dataset); // initialize Piedata<br />
        pieChart.setData(data);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateY(2000);
    }

}
