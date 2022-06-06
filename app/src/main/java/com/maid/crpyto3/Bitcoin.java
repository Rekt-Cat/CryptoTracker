package com.maid.crpyto3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;

import org.json.JSONArray;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Model.model;

public class Bitcoin extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitcoin);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent i = getIntent();
        String value = i.getStringExtra("Name of the coin").toLowerCase();
        Log.d("n", value);
        String url1 = "https://api.coingecko.com/api/v3/coins/"+value+"/ohlc?vs_currency=inr&days=7";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url1, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.d("san","response is : "+response.toString());

                    CandleStickChart candleStickChart = findViewById(R.id.candleStick);
                    candleStickChart.setExtraBottomOffset(25.0f);
                    candleStickChart.setHighlightPerDragEnabled(true);
                    candleStickChart.setBorderColor(getResources().getColor(R.color.colorLightGray));
                    YAxis yAxis = candleStickChart.getAxisLeft();
                    YAxis rightAxis = candleStickChart.getAxisRight();
                    yAxis.setDrawGridLines(false);
                    rightAxis.setDrawGridLines(false);
                    yAxis.setDrawLabels(false);

                    candleStickChart.requestDisallowInterceptTouchEvent(true);







                    ArrayList<CandleEntry> yValsCandleStick = new ArrayList<CandleEntry>();
                    List<String> xValsCandleStick = new ArrayList<String>();
                    long date;
                    float open=0;
                    float high=0;
                    float low=0;
                    float close=0;

                    for(int i=0; i<response.length();i++){
                        JSONArray jsonArray = response.getJSONArray(i);
                        date = jsonArray.getLong(0);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
                        String date2 = simpleDateFormat.format(date);
                        xValsCandleStick.add(date2);
                        Log.d("kk",date2);
                        open =  Float.parseFloat(jsonArray.getString(1));
                        high = Float.parseFloat(jsonArray.getString(2));
                        low = Float.parseFloat(jsonArray.getString(3));
                        close = Float.parseFloat(jsonArray.getString(4));

                        yValsCandleStick.add(new CandleEntry((float)i,high,low,open,close));

                    }

                    XAxis xAxis = candleStickChart.getXAxis();
                    xAxis.setEnabled(true);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);// disable x axis grid lines
                    xAxis.setDrawLabels(false);
                    xAxis.setCenterAxisLabels(true);
                    rightAxis.setTextColor(Color.WHITE);
                    xAxis.setGranularity(7f);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setAvoidFirstLastClipping(true);


                    CandleDataSet set1 = new CandleDataSet(yValsCandleStick, "DataSet 1");
                    set1.setColor(Color.rgb(80, 80, 80));
                    set1.setShadowWidth(0.8f);
                    set1.setShadowColor(getResources().getColor(R.color.colorLightGray));
                    set1.setDecreasingColor(getResources().getColor(R.color.red));
                    set1.setIncreasingColor(getResources().getColor(R.color.green));
                    set1.setDecreasingPaintStyle(Paint.Style.FILL);
                    set1.setIncreasingPaintStyle(Paint.Style.FILL);
                    set1.setNeutralColor(Color.LTGRAY);
                    set1.setDrawValues(false);

                    ArrayList<ICandleDataSet> datasets= new ArrayList<>();
                    datasets.add(set1);
                    class MyValueFormatter extends IndexAxisValueFormatter {


                        @Override
                        public String getFormattedValue(float value) {
                            return super.getFormattedValue(value);
                        }
                    }
                    xAxis.setValueFormatter(new MyValueFormatter());

                    CandleData data = new CandleData(datasets);
                    candleStickChart.setData(data);
                    candleStickChart.invalidate();
                    candleStickChart.getLegend().setEnabled(false);
                    candleStickChart.getDescription().setEnabled(false);
                }
                catch(Exception e){
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue1;
        requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(jsonArrayRequest);

    }
}