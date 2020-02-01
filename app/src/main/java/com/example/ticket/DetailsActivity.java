package com.example.ticket;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    private String name_url = "http://192.168.43.11/Bus/api/getBookingDetails.php";
    private NameAdapter nameviewAdapter;
    private ArrayList<name> naName = new ArrayList<>();
    private ListView listView;
    int count = 0;
    String status = "booked",con_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        con_name=getIntent().getStringExtra("con_name");
        listView = findViewById(R.id.listName);
        getNames();
    }

    public void getNames() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, name_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("passList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject Object = jsonArray.getJSONObject(i);
                        count++;
                        String name = Object.getString("pass_name");
                        String number = Object.getString("pass_no");
                        String seat = Object.getString("seat");
                        String place = Object.getString("place");
                        String time = Object.getString("time");
                        String date = Object.getString("date");
                        String sex = Object.getString("sex");
                        String address = Object.getString("address");
                        name na = new name(String.valueOf(count), name, number, seat, place, time, date, sex, address, con_name);
                        naName.add(na);
                    }
                    nameviewAdapter = new NameAdapter(DetailsActivity.this, naName);
                    listView.setAdapter(nameviewAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("kee", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("kee", error.toString());
                error.printStackTrace();

            }
        }) {
            //Code For Send Data's to PHP file
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("status", status);
                return params;
            }
            //End Code For Send Data's to PHP file
        };
        Mysingleton.getInstance(DetailsActivity.this).addToRequest(stringRequest);
    }
}
