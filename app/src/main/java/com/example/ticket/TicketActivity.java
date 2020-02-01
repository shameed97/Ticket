package com.example.ticket;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketActivity extends AppCompatActivity {
    private Spinner spinnerPlace;
    private EditText editTextName, editTextNo, editTextPAmount, editTextSeat, editTextTotal;
    private TextView textViewBalance;
    private String place_url = "http://192.168.43.11/Bus/api/getplace.php";
    private String daily_url = "http://192.168.43.11/Bus/api/dailyDetails.php";
    private String place, destination, name, route, spinItemPlace, PlaceAmount;
    private String pass_name, pass_no, total, seat, placeId, spinItemget,pAmount="0";
    private List<String> data = new ArrayList<>();
    private ArrayAdapter<String> adapter1;
    private AlertDialog.Builder builder;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavigationMenuView navigationMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        spinnerPlace = findViewById(R.id.placeSpinner);
        editTextName = findViewById(R.id.passName);
        editTextNo = findViewById(R.id.passNo);
        editTextSeat = findViewById(R.id.ticNumber);
        editTextTotal = findViewById(R.id.totalAmount);
        editTextPAmount = findViewById(R.id.passAm);
        textViewBalance = findViewById(R.id.balAmount);
        builder = new AlertDialog.Builder(this);

        String[] value = getIntent().getStringArrayExtra("value");
        assert value != null;
        name = value[0];
        route = value[1];

        getPlacespin();
        setNavigation();
    }

    private void getPlacespin() {
        //Code for Spinner Place

        //Code for Volley,StringRequest.....

        if (route.equals("1")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, place_url, new Response.Listener<String>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(String response) {

                    Log.d("res", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("jsonObject", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("placeList");
                        data.add(0, "Select Place");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject Object = jsonArray.getJSONObject(i);
                            place = Object.getString("place");
                            destination = Object.getString("desti");
                            placeId = Object.getString("id");
                            Log.d("sha", place + destination);
                            data.add(place + "-" + destination);
                            adapter1 = new ArrayAdapter<>(TicketActivity.this, android.R.layout.simple_spinner_item, data);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerPlace.setAdapter(adapter1);
                            adapter1.notifyDataSetChanged();


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("sha", e.toString());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("kee", error.toString());
                    error.printStackTrace();

                }
            }) {
                //Code For Send Data's to Server
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
                //End Code For Send Data's to Server
            };
            Mysingleton.getInstance(TicketActivity.this).addToRequest(stringRequest);
        }
        //End Code for Volley,StringRequest.....

        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, place_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("res", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d("jsonObject", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("placeList");
                        data.add(0, "Select Place");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject Object = jsonArray.getJSONObject(i);
                            place = Object.getString("place");
                            destination = Object.getString("desti");
                            placeId = Object.getString("id");
                            Log.d("sha", place + destination);
                            data.add(destination + "-" + place);

                            adapter1 = new ArrayAdapter<>(TicketActivity.this, android.R.layout.simple_spinner_item, data);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerPlace.setAdapter(adapter1);
                            adapter1.notifyDataSetChanged();


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("sha", e.toString());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("kee", error.toString());
                    error.printStackTrace();

                }
            }) {
                //Code For Send Data's to Server
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
                //End Code For Send Data's to Server
            };
            Mysingleton.getInstance(TicketActivity.this).addToRequest(stringRequest);


        }
        //End Code for Volley,StringRequest.....

        //Spinner Code for getting values
        spinnerPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                if (!spinnerPlace.getSelectedItem().toString().equals("Select Route")) {

                    //spinItemget = adapterView.getItemAtPosition(position).toString();
                } else {

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Spinner Code for getting values

    }

    @SuppressLint("SetTextI18n")
    public void Booking(View view) {


        pass_name = editTextName.getText().toString();
        pass_no = editTextNo.getText().toString();
        seat = editTextSeat.getText().toString();
        pAmount = editTextPAmount.getText().toString();
        total = editTextTotal.getText().toString();
        spinItemPlace = spinnerPlace.getSelectedItem().toString();
        int amount = Integer.parseInt(pAmount) - Integer.parseInt(total);
        textViewBalance.setText("₹ " + amount);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, daily_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("res", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.d("jsonObject", jsonArray.toString());
                    JSONObject object = jsonArray.getJSONObject(0);
                    String code = object.getString("code");
                    String message = object.getString("message");
                    Log.d("value", code + message);

                    builder.setTitle("Booking Information :");
                    if (code.equals("1")) {
                        builder.setMessage(message);
                        displayMes(code);

                    } else {
                        builder.setMessage(message);
                        displayMes(code);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("sha", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("kee", error.toString());
                error.printStackTrace();

            }
        }) {
            //Code For Send Data's to Server
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (seat.equals("")) {
                    seat = "1";
                    params.put("con_name", name);
                    params.put("ticket", seat);
                    params.put("pass_name", pass_name);
                    params.put("pass_no", pass_no);
                    params.put("total", total);
                    params.put("place", spinItemPlace);
                } else {
                    params.put("con_name", name);
                    params.put("ticket", seat);
                    params.put("pass_name", pass_name);
                    params.put("pass_no", pass_no);
                    params.put("total", total);
                    params.put("place", spinItemPlace);

                }

                Log.d("value", name + pass_name + pass_no + total + spinItemPlace + seat);
                return params;
            }
            //End Code For Send Data's to Server
        };
        Mysingleton.getInstance(TicketActivity.this).addToRequest(stringRequest);
    }
    //Code for Spinner Place

    //Code for AlertDialog
    public void displayMes(final String code) {
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (code.equals("1")) {
                    sendSMS();
                    editTextName.setText("");
                    editTextNo.setText("");
                    editTextSeat.setText("");
                    editTextTotal.setText("");
                    editTextPAmount.setText("");

                } else {
                    editTextName.setText("");
                    editTextNo.setText("");
                    editTextSeat.setText("");
                    editTextTotal.setText("");
                    editTextPAmount.setText("");
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //Code for AlertDialog

    //Code for SMS
    public void sendSMS() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(pass_no, null, "BUS NO : PY-7207 \nWelcome " + pass_name + "," + "\n for " + seat + " no of person" +
                "\nRoute :" + spinItemPlace + "\nTicket Booked", null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
    }
//Code for SMS

    //Code for Navigation Button
    public void Home(View view) {

        drawerLayout.openDrawer(GravityCompat.START);
    }

    //Code for Navigation Drawer
    public void setNavigation() {
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);
        navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        navigationMenuView.addItemDecoration(new DividerItemDecoration(TicketActivity.this, DividerItemDecoration.VERTICAL));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.book:
                        Intent intent=new Intent(getApplicationContext(),DetailsActivity.class);
                        intent.putExtra("con_name",name);
                        startActivity(intent);
                        break;

                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        LinearLayout layout = navigationView.findViewById(R.id.linear);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                }

            }
        });
    }





}



