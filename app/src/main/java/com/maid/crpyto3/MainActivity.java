package com.maid.crpyto3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import Model.model;
import adapter.adapter;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String url = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin%2Cethereum%2Cbinancecoin%2Ctether%2Csolana%2Cusdcoin%2Ccardano%2Cavalanche%2Cdogecoin%2Cpolkadot&vs_currencies=usd%2Cinr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        ArrayList<model> list =new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject obj1 = new JSONObject(response);

                    JSONObject btc  = obj1.getJSONObject("bitcoin");
                    double btcInr = btc.getDouble("inr");
                    double btcUsd = btc.getDouble("usd");
                    list.add(new model(btcInr,btcUsd,"Bitcoin"));

                    JSONObject ethe = obj1.getJSONObject("ethereum");
                    double etheInr = ethe.getDouble("inr");
                    double etheUsd = ethe.getDouble("usd");
                    list.add(new model(etheInr,etheUsd,"Ethereum"));

                    JSONObject tet = obj1.getJSONObject("tether");
                    double tetInr = tet.getDouble("inr");
                    double tetUsd = tet.getDouble("usd");
                    list.add(new model(tetInr,tetUsd,"Tether"));

                    JSONObject sol = obj1.getJSONObject("solana");
                    double solInr = sol.getDouble("inr");
                    double solUsd = sol.getDouble("usd");
                    list.add(new model(solInr,solUsd,"Solana"));

                    JSONObject pol = obj1.getJSONObject("polkadot");
                    double polInr = pol.getDouble("inr");
                    double polUsd = pol.getDouble("usd");
                    list.add(new model(polInr,polUsd, "Polkadot"));

                    JSONObject doge = obj1.getJSONObject("dogecoin");
                    double dogeInr = doge.getDouble("inr");
                    double dogeUsd = doge.getDouble("usd");
                    list.add(new model(dogeInr,dogeUsd,"Dogecoin"));

                    JSONObject bin = obj1.getJSONObject("binancecoin");
                    double binInr = bin.getDouble("inr");
                    double binUsd = bin.getDouble("usd");
                    list.add(new model(binInr,binUsd,"Binancecoin"));

                    adapter adapter = new adapter(list,MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    LinearLayoutManager linearLayoutManager =new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("san",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("lol", "the error is : "+ error.getMessage());
            }
        });
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


















    }
}